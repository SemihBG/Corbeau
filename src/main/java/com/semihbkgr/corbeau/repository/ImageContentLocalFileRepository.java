package com.semihbkgr.corbeau.repository;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Repository
@Slf4j
public class ImageContentLocalFileRepository implements ImageContentRepository {

    private final Path rootDirectoryPath;

    @Autowired
    public ImageContentLocalFileRepository(@Value("${image.rootDirectory}") String rootDirectory) {
        this.rootDirectoryPath = Path.of(rootDirectory);
    }

    @PostConstruct
    public void createRootFolderIfNotExist() throws IOException {
        if (!Files.isDirectory(rootDirectoryPath)) {
            Files.createDirectory(rootDirectoryPath);
            log.info("RootFolderPath: {} created successfully", rootDirectoryPath);
        } else log.info("RootFolderPath: {} already exists", rootDirectoryPath);
    }

    @Override
    public Mono<Void> save(@NonNull String name, @NonNull FilePart filePart) {
        return filePart.transferTo(rootDirectoryPath.resolve(name));
    }

    @Override
    public Flux<DataBuffer> findByName(@NonNull String name) {
        return DataBufferUtils.read(rootDirectoryPath, new DefaultDataBufferFactory(), 4096);
    }

    @Override
    public Mono<Boolean> exists(@NonNull String name) {
        return Mono.just(Files.exists(rootDirectoryPath.resolve(name)));
    }

    @Override
    public Mono<Void> delete(@NonNull String name) {
        return Mono.defer(()->{
            try {
                Files.delete(rootDirectoryPath.resolve(name));
                return Mono.empty();
            } catch (IOException e) {
                return Mono.error(e);
            }
        });
    }

}
