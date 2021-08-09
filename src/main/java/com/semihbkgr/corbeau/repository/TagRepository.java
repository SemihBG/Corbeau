package com.semihbkgr.corbeau.repository;

import com.semihbkgr.corbeau.model.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TagRepository {

    String TABLE_NAME="tags";
    String JOIN_TABLE_NAME="tags_posts_join";

    Mono<Tag> save(Tag tag);

    Mono<Tag> update(Tag tag);

    Mono<Tag> findById(int id);

    Flux<Tag> findAll();

    Flux<Tag> findAllByPostId(int postId);

    Mono<Integer> deleteById(int id);

}
