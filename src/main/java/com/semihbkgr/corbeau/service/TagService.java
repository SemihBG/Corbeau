package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.error.IllegalValueException;
import com.semihbkgr.corbeau.model.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TagService {

    Mono<Tag> save(Tag tag);

    Mono<Tag> update(int id, Tag tag) throws IllegalValueException;

    Flux<Tag> findAll();

    Flux<Tag> findAllByPostId(int postId) throws IllegalValueException;

    Mono<Integer> deleteById(int id) throws IllegalValueException;

}
