package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.model.Post;
import com.semihbkgr.corbeau.model.projection.PostShallow;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostService {

    Flux<PostShallow> findAll();

    Mono<Post> save(Post post);

}
