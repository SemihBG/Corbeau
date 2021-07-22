package com.semihbkgr.corbeau.repository;

import com.semihbkgr.corbeau.model.Post;
import com.semihbkgr.corbeau.model.projection.PostShallow;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostRepository {

    String TABLE_NAME="posts";

    Mono<Post> save(Post post);

    Mono<Post> update(Post post);

    Mono<Post> findById(int id);

    Mono<Post> findByTitle(String title);

    Flux<PostShallow> findAll(Pageable pageable);

    Mono<Long> count();

}
