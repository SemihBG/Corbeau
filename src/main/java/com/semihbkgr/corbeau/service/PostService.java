package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.model.Post;
import reactor.core.publisher.Mono;

public interface PostService {

    Mono<Post> save(Post post);


}
