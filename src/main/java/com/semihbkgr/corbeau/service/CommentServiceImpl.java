package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.error.IllegalValueException;
import com.semihbkgr.corbeau.model.Comment;
import com.semihbkgr.corbeau.repository.CommentRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public Mono<Comment> save(@NonNull Comment comment) {
        return commentRepository.save(comment.withId(0));
    }

    @Override
    public Flux<Comment> findByPostId(int postId) throws IllegalValueException{
        if (postId <= 0)
            throw new IllegalArgumentException("PostId parameter must be positive value");
        return commentRepository.findAllByPostIdOrderByCreatedAtDesc(postId);
    }


    @Override
    public Mono<Long> countByPostId(int postId)  throws IllegalValueException{
        if (postId <= 0)
            throw new IllegalArgumentException("PostId parameter must be positive value");
        return commentRepository.countByPostId(postId);
    }

    @Override
    public Mono<Void> deleteById(int id) throws IllegalValueException {
        if (id <= 0)
            throw new IllegalArgumentException("Id parameter must be positive value");
        return commentRepository.deleteById(id);
    }

}
