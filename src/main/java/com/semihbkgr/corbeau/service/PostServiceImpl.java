package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.error.IllegalValueException;
import com.semihbkgr.corbeau.model.Post;
import com.semihbkgr.corbeau.model.projection.PostInfo;
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
    public Mono<Post> save(Post post) {
        if(post==null)
            return Mono.error(new NullPointerException("Post parameter cannot be null"));
        return postRepository.save(post);
    }

    @Override
    public Mono<Post> update(int id, Post post) {
        if(post==null)
            return Mono.error(new NullPointerException("Post parameter cannot be null"));
        if(id<=0)
            return Mono.error(new IllegalArgumentException("Id parameter must be positive value"));
        return postRepository.findById(id)
                .switchIfEmpty(Mono.error(()->
                        new IllegalValueException("No post fond by given id",PostRepository.TABLE_NAME,"id",id)))
                .flatMap(savedPost->{
                    savedPost.setTitle(post.getTitle());
                    savedPost.setContent(post.getContent());
                    savedPost.setSubjectId(post.getSubjectId());
                    savedPost.setActivated(post.isActivated());
                    savedPost.setEndpoint(post.getEndpoint());
                    return postRepository.update(savedPost);
                });
    }

    @Override
    public Mono<Post> findByEndpoint(String endpoint) {
        if (endpoint==null)
            return Mono.error(new NullPointerException("Endpoint parameter cannot be null"));
        return postRepository.findByEndpoint(endpoint)
                .switchIfEmpty(Mono.error(()->
                        new IllegalValueException("No post fond by given title",PostRepository.TABLE_NAME,"enpoint",endpoint)));
    }

    @Override
    public Flux<PostShallow> findAllShallow(Pageable pageable) {
        if(pageable==null)
            return Flux.error(new NullPointerException("Pageable parameter cannot be null"));
        return postRepository.findAllShallow(pageable);
    }

    @Override
    public Flux<PostShallow> findAllByActivatedShallow(boolean activated, Pageable pageable) {
        if(pageable==null)
            return Flux.error(new NullPointerException("Pageable parameter cannot be null"));
        return postRepository.findAllByActivatedShallow(activated,pageable);
    }

    @Override
    public Flux<PostInfo> findAllActivatedBySubjectIdInfo(int subjectId, @NonNull Pageable pageable) {
        return postRepository.findAllActivatedBySubjectIdInfo(subjectId,pageable);
    }





    @Override
    public Mono<Long> count() {
        return postRepository.count();
    }

    @Override
    public Mono<Long> countBySubjectId(int subjectId) {
        return postRepository.countBySubjectId(subjectId);
    }

    @Override
    public Mono<Long> countBySubjectIdAndActivated(int subjectId,boolean activated) {
        return postRepository.countBySubjectIdAndActivated(subjectId,activated);
    }

}
