package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.model.Image;
import com.semihbkgr.corbeau.repository.ImageContentRepository;
import com.semihbkgr.corbeau.util.ParameterUtils;
import lombok.*;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageContentServiceImpl implements ImageContentService {

    private final ImageContentRepository imageContentRepository;

    @Override
    public Mono<Image> save(@NonNull String name, @NonNull Mono<FilePart> filePartMono) {
        return imageContentRepository
                .exists(name)
                .filter(exists -> !exists)
                .switchIfEmpty(Mono.error(IllegalArgumentException::new))
                .then(filePartMono)
                .flatMap(filePart -> {
                    var ext = ParameterUtils.extractExtension(filePart.filename());
                    return imageContentRepository
                            .save(name.concat(".").concat(ext), filePart)
                            .then(Mono.just(Tuples.of(filePart, ext)));
                })
                .flatMap(tuple ->
                        imageMetadataFromFilePart(tuple.getT1())
                                .map(imageMetadata -> Tuples.of(imageMetadata, tuple.getT2()))
                )
                .map(tuple ->
                        Image.builder()
                                .name(name)
                                .extension(tuple.getT2())
                                .width(tuple.getT1().width)
                                .height(tuple.getT1().height)
                                .size(tuple.getT1().size)
                                .build()
                );
    }

    @Override
    public Mono<Image> update(@NonNull String fullName, @NonNull String newName, @NonNull Mono<FilePart> filePartMono) {
        return imageContentRepository
                .exists(newName)
                .filter(exists -> !exists)
                .switchIfEmpty(Mono.error(IllegalArgumentException::new))
                .then(imageContentRepository.exists(fullName))
                .filter(exists -> exists)
                .switchIfEmpty(Mono.error(IllegalArgumentException::new))
                .then(imageContentRepository.delete(fullName))
                .then(filePartMono)
                .flatMap(filePart -> {
                    var ext = ParameterUtils.extractExtension(filePart.filename());
                    return imageContentRepository
                            .save(newName.concat(".").concat(ext), filePart)
                            .map(ignore -> Tuples.of(filePart, ext));
                })
                .flatMap(tuple ->
                        imageMetadataFromFilePart(tuple.getT1())
                                .map(imageMetadata -> Tuples.of(imageMetadata, tuple.getT2()))
                )
                .map(tuple ->
                        Image.builder()
                                .name(newName)
                                .extension(tuple.getT2())
                                .width(tuple.getT1().width)
                                .height(tuple.getT1().height)
                                .size(tuple.getT1().size)
                                .build()
                );
    }

    @Override
    public Flux<DataBuffer> findByName(@NonNull String name) {
        return imageContentRepository
                .exists(name)
                .filter(exists -> exists)
                .switchIfEmpty(Mono.error(IllegalArgumentException::new))
                .thenMany(imageContentRepository.findByName(name));
    }

    @Override
    public Flux<DataBuffer> imageNotFound() {
        return imageContentRepository.imageNotFound();
    }

    @Override
    public Mono<Void> delete(@NonNull String fullName) {
        return imageContentRepository
                .exists(fullName)
                .filter(exists -> !exists)
                .switchIfEmpty(Mono.error(IllegalArgumentException::new))
                .then(imageContentRepository.delete(fullName));
    }

    @SuppressWarnings("BlockingMethodInNonBlockingContext")
    private Mono<ImageMetadata> imageMetadataFromFilePart(@NonNull FilePart filePart) {
        return filePart
                .content()
                .collectList()
                .flatMap(dataBufferList -> {
                    try {
                        var size = 0L;
                        var contentBAOS = new ByteArrayOutputStream();
                        for (var dataBuffer : dataBufferList) {
                            var byteCount = dataBuffer.readableByteCount();
                            size += byteCount;
                            var content = new byte[byteCount];
                            dataBuffer.read(content);
                            contentBAOS.write(content);
                        }
                        var img = ImageIO.read(new ByteArrayInputStream(contentBAOS.toByteArray()));
                        return Mono.just(ImageMetadata.builder()
                                .width(img.getWidth())
                                .height(img.getHeight())
                                .size(size)
                                .build());
                    } catch (IOException e) {
                        return Mono.error(e);
                    }
                });
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    private static class ImageMetadata {

        private int width;
        private int height;
        private long size;

    }

}
