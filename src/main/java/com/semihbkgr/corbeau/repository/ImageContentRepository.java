package com.semihbkgr.corbeau.repository;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ImageContentRepository {

    Mono<Void> save(String name, FilePart filePart);

    Flux<DataBuffer> findByName(String name);

    Mono<Boolean> exists(String name);

    Mono<Void> delete(String name);

}
