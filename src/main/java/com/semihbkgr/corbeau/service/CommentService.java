package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.error.IllegalValueException;
import com.semihbkgr.corbeau.model.Comment;
import com.semihbkgr.corbeau.model.projection.CommentDeep;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CommentService {

    Mono<Comment> save(Comment comment);

    Mono<Comment> update(int id,Comment comment) throws IllegalValueException;

    Flux<Comment> findByPostId(int postId,Pageable pageable) throws IllegalValueException;

    Flux<CommentDeep> findAllDeep(Pageable pageable);

    Mono<Long> count();

    Mono<Long> countByPostId(int postId) throws IllegalValueException;

    Mono<Void> deleteById(int id) throws IllegalValueException;

    Mono<Integer> deleteAllByPostId(int postId) throws IllegalValueException;

}
