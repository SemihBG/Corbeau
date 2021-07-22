package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.error.IllegalValueException;
import com.semihbkgr.corbeau.model.Post;
import com.semihbkgr.corbeau.model.projection.PostShallow;
import com.semihbkgr.corbeau.repository.PostRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public Mono<Post> findByTitle(@PathVariable String title) {
        return postRepository.findByTitle(title)
                .switchIfEmpty(Mono.error(()->
                        new IllegalValueException("No post fond by given title",PostRepository.TABLE_NAME,"title",title)));
    }

    @Override
    public Flux<PostShallow> findAll(@NonNull Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public Mono<Post> save(@NonNull Post post) {
        return postRepository.save(post);
    }

    @Override
    public Mono<Post> update(int id, Post post) {
        return postRepository.findById(id)
                .switchIfEmpty(Mono.error(()->
                        new IllegalValueException("No post fond by given id",PostRepository.TABLE_NAME,"id",id)))
                .flatMap(savedPost->{
                    savedPost.setTitle(post.getTitle());
                    savedPost.setContent(post.getContent());
                    savedPost.setSubjectId(post.getSubjectId());
                    return postRepository.update(savedPost);
                });
    }

    @Override
    public Mono<Long> count() {
        return postRepository.count();
    }

}
