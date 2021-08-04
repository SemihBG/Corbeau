package com.semihbkgr.corbeau.repository;

import com.semihbkgr.corbeau.model.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TagRepository {

    Mono<Tag> save(Tag tag);

    Mono<Tag> update(Tag tag);

    Flux<Tag> findAll();

    Mono<Integer> deleteById(int id);

}
