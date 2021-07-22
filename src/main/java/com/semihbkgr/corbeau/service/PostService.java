package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.error.IllegalValueException;
import com.semihbkgr.corbeau.model.Post;
import com.semihbkgr.corbeau.model.projection.PostShallow;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostService {

    Mono<Post> findByTitle(String title) throws IllegalValueException;

    Flux<PostShallow> findAll(Pageable pageable);

    Mono<Post> save(Post post);

    Mono<Post> update(int id,Post post);

    Mono<Long> count();

}
