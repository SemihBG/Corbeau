package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.error.IllegalValueException;
import com.semihbkgr.corbeau.model.Image;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ImageService {

    Mono<Image> save(Image image);

    Mono<Image> update(int id, Image image) throws IllegalValueException;

    Flux<Image> findAll(Pageable pageable);

    Mono<Image> findByFullName(String fullName) throws IllegalValueException;

    Mono<Void> deleteById(int id) throws IllegalValueException;

    Mono<Long> count();

}
