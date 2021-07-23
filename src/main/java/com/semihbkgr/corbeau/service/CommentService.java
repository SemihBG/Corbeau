package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.error.IllegalValueException;
import com.semihbkgr.corbeau.model.Comment;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CommentService {

    Mono<Comment> save(Comment comment);

    Flux<Comment> findByPostId(int postId, Pageable pageable);

    Mono<Void> deleteById(int id) throws IllegalValueException;

}
