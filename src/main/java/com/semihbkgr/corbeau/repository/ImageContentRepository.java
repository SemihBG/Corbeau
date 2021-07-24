package com.semihbkgr.corbeau.repository;

import com.semihbkgr.corbeau.model.Image;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ImageContentRepository {

    Mono<Image> save(String name,Mono<FilePart> filePartMono);

    Mono<Image> update(String name,Mono<FilePart> filePartMono);

    Flux<DataBuffer> findByName(String name);

    Mono<Boolean> exists(String name);

}
