package com.semihbkgr.corbeau.repository;

import com.semihbkgr.corbeau.model.Tag;
import com.semihbkgr.corbeau.model.projection.TagDeep;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TagRepository {

    String TABLE_NAME="tags";

    Mono<Tag> save(Tag tag);

    Mono<Tag> update(Tag tag);

    Mono<Tag> findById(int id);

    Mono<Tag> findByName(String name);

    Flux<Tag> findAll();

    Flux<Tag> findAllByPostId(int postId);

    Flux<TagDeep> findAllDeep();

    Mono<Integer> deleteById(int id);

}
