package com.semihbkgr.corbeau.repository;

import com.semihbkgr.corbeau.model.Post;
import com.semihbkgr.corbeau.model.projection.PostShallow;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PostRepositoryImpl implements PostRepository {
    @Override
    public Mono<Post> save(Post post) {
        return null;
    }

    @Override
    public Flux<PostShallow> findAllPostShallow(Pageable pageable) {
        return null;
    }
}
