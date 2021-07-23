package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.error.IllegalValueException;
import com.semihbkgr.corbeau.model.Comment;
import com.semihbkgr.corbeau.repository.CommentRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
    public Flux<Comment> findByPostId(int postId, @NonNull Pageable pageable) {
        return commentRepository.findAllByPostId(postId,pageable);
    }

    @Override
    public Mono<Void> deleteById(int id) throws IllegalValueException{
        return commentRepository.findById(id)
                .switchIfEmpty(Mono.error(()->
                        new IllegalValueException("No comment found by given id",CommentRepository.TABLE_NAME,"id",id)))
                .then(commentRepository.deleteById(id));
    }

}
