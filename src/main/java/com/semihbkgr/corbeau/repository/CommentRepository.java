package com.semihbkgr.corbeau.repository;

import com.semihbkgr.corbeau.model.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface CommentRepository extends R2dbcRepository<Comment,Integer> {

    String TABLE_NAME="comments";

    Flux<Comment> findAllByPostId(int postId, Pageable pageable);

}
