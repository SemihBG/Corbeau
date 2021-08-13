package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.error.IllegalValueException;
import com.semihbkgr.corbeau.model.Tag;
import com.semihbkgr.corbeau.model.projection.TagDeep;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TagService {

    Mono<Tag> save(Tag tag);

    Mono<Tag> update(int id, Tag tag) throws IllegalValueException;

    Mono<Tag> findByName(String name);

    Flux<Tag> findAllByPostId(int postId) throws IllegalValueException;

    Flux<TagDeep> findAllDeep();

    Flux<TagDeep> findAllByActivatedDeep(boolean activated);

    Mono<Integer> deleteById(int id) throws IllegalValueException;

}
