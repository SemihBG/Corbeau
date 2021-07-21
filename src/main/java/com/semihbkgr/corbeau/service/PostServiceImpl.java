package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.model.Post;
import com.semihbkgr.corbeau.model.projection.PostShallow;
import com.semihbkgr.corbeau.repository.PostRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public Flux<PostShallow> findAll(@NonNull Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public Mono<Post> save(@NonNull Post post) {
        return postRepository.save(post);
    }

}
