package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.model.Image;
import com.semihbkgr.corbeau.repository.ImageContentRepository;
import com.semihbkgr.corbeau.util.ParameterUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
@RequiredArgsConstructor
public class ImageContentServiceImpl implements ImageContentService {

    private final ImageContentRepository imageContentRepository;

    @Override
    public Mono<Image> save(String name, Mono<FilePart> filePartMono) {
        return filePartMono
                .flatMap(filePart -> {
                    var extension= ParameterUtils.extractExtension(filePart.filename());
                    return imageContentRepository.save(name.concat(".").concat(extension),filePart);
                })
                .then(filePartMono)
                .map(filePart -> {
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
                    return Mono.just(Image.builder()
                            .name(name)
                            .width(img.getWidth())
                            .height(img.getHeight())
                            .size(size)
                            .build());
                });
    }

    @Override
    public Mono<Image> update(int id, String name, Mono<FilePart> filePartMono) {
        return null;
    }

}
