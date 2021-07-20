package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.model.Post;
import com.semihbkgr.corbeau.model.projection.PostShallow;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostService {

    Flux<PostShallow> findAllPaged(Pageable pageable);

    Mono<Post> save(Post post);

}
