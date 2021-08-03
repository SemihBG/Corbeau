package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.error.IllegalValueException;
import com.semihbkgr.corbeau.model.Image;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ImageService {

    Mono<Image> save(Image image);

    Mono<Image> update(int id, Image image) throws IllegalValueException;

    Mono<Image> findByFullName(String fullName);

    Flux<Image> findAll(Pageable pageable);

    Mono<Long> count();

    Mono<Void> deleteById(int id) throws IllegalValueException;
}
