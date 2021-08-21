package com.semihbkgr.corbeau.repository;

import com.semihbkgr.corbeau.model.Comment;
import com.semihbkgr.corbeau.model.projection.CommentDeep;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CommentRepository {

    String TABLE_NAME="comments";

    Mono<Comment> save(Comment comment);

    Mono<Comment> update(Comment comment);

    Flux<Comment> findAllByPostId(int postId,Pageable pageable);

    Flux<CommentDeep> findAllCommentDeep(Pageable pageable);

    Mono<Long> countByPostId(int postId);

}
