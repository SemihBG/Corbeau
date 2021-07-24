package com.semihbkgr.corbeau.repository;

import com.semihbkgr.corbeau.model.Image;
import com.semihbkgr.corbeau.util.ParameterUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Repository
@Slf4j
public class ImageContentFileRepository implements ImageContentRepository {

    private Path rootDirectoryPath;

    @Autowired
    public ImageContentFileRepository(@Value("${image.rootDirectory}") String rootDirectory) {
        this.rootDirectoryPath = Path.of(rootDirectory);
    }

    @SuppressWarnings("ConstantConditions")
    @PostConstruct
    public void createRootFolderIfNotExist() throws IOException {
        if (!Files.isDirectory(rootDirectoryPath)) {
            rootDirectoryPath = Files.createDirectory(rootDirectoryPath);
            log.info("RootFolderPath: {} created successfully", rootDirectoryPath.toString());
        } else log.info("RootFolderPath: {} already exists", rootDirectoryPath.toString());
    }

    @Override
    public Mono<Image> save(String name, Mono<FilePart> filePartMono) {
        return filePartMono
                .flatMap(filePart -> {
                    return filePart.transferTo(rootDirectoryPath.resolve(name));
                })
                .then(filePartMono)
                .flatMapMany(Part::content)
                .collectList()
                .flatMap(dataBufferList ->
                        Mono.defer(() -> {
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
                                return Mono.just(Image.builder()
                                        .name(name)
                                        .width(img.getWidth())
                                        .height(img.getHeight())
                                        .size(size)
                                        .build());
                            } catch (Throwable e) {
                                return Mono.error(e);
                            }
                        })
                );
    }

    @Override
    public Mono<Image> update(String name, Mono<FilePart> filePartMono) {
        return exists(name)
                .flatMap(exists -> {
                    if (exists) {
                        return save(name, filePartMono);
                    } else
                        return Mono.error(new IllegalArgumentException());
                });
    }

    @Override
    public Flux<DataBuffer> findByName(String name) {
        return exists(name)
                .flatMapMany(exists -> {
                    if (exists) return DataBufferUtils.read(rootDirectoryPath, new DefaultDataBufferFactory(), 4096);
                    else return Flux.error(new IllegalArgumentException());
                });
    }

    @Override
    public Mono<Boolean> exists(String name) {
        return Mono.just(Files.exists(rootDirectoryPath.resolve(name)));
    }

}
