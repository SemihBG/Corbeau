package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.model.Image;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ImageService {

    Flux<Image> findAll();

    Mono<Image> save(Image image);

    Mono<Void> deleteById(String id);

}
