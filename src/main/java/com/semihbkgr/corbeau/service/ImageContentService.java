package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.model.Image;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ImageContentService {

    Mono<Image> save(String name, Mono<FilePart> filePartMono);

    Mono<Image> update(String fullName, String newName, Mono<FilePart> filePartMono);

    Flux<DataBuffer> findByName(String name);

    Flux<DataBuffer> imageNotFound();

    Mono<Void> delete(String fullName);

}
