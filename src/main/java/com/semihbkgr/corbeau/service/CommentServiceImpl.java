package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.error.IllegalValueException;
import com.semihbkgr.corbeau.model.Comment;
import com.semihbkgr.corbeau.model.projection.CommentDeep;
import com.semihbkgr.corbeau.repository.CommentRepository;
import com.semihbkgr.corbeau.repository.SubjectRepository;
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
    public Mono<Comment> update(int id, @NonNull Comment comment) throws IllegalValueException {
        if (id <= 0)
            throw new IllegalValueException("post_id must be positive value", CommentRepository.TABLE_NAME, "id", id);
        return commentRepository.findById(id)
                .switchIfEmpty(Mono.error(() ->
                        new IllegalValueException("Comment not available by given id", CommentRepository.TABLE_NAME, "id", id)))
                .flatMap(savedComment->{
                   savedComment.setName(comment.getName());
                   savedComment.setSurname(comment.getSurname());
                   savedComment.setEmail(comment.getEmail());
                   savedComment.setContent(comment.getContent());
                   return commentRepository.update(savedComment);
                });
    }

    @Override
    public Flux<Comment> findByPostId(int postId, @NonNull Pageable pageable) throws IllegalValueException {
        if (postId <= 0)
            throw new IllegalValueException("post_id must be positive value", CommentRepository.TABLE_NAME, "post_id", postId);
        return commentRepository.findAllByPostId(postId, pageable);
    }

    @Override
    public Flux<CommentDeep> findAllDeep(@NonNull Pageable pageable) {
        return commentRepository.findAllDeep(pageable);
    }

    @Override
    public Mono<Long> count() {
        return commentRepository.count();
    }


    @Override
    public Mono<Long> countByPostId(int postId) throws IllegalValueException {
        if (postId <= 0)
            throw new IllegalValueException("post_id must be positive value", CommentRepository.TABLE_NAME, "post_id", postId);
        return commentRepository.countByPostId(postId);
    }

    @Override
    public Mono<Void> deleteById(int id) throws IllegalValueException {
        if (id <= 0)
            throw new IllegalValueException("id must be positive value", CommentRepository.TABLE_NAME, "id", id);
        return commentRepository.deleteById(id).then();
    }

}
