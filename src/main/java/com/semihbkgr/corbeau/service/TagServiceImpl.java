package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.error.IllegalValueException;
import com.semihbkgr.corbeau.model.Tag;
import com.semihbkgr.corbeau.model.projection.TagDeep;
import com.semihbkgr.corbeau.repository.TagRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public Mono<Tag> save(@NonNull Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Mono<Tag> update(int id, @NonNull Tag tag) throws IllegalValueException {
        if (id <= 0)
            throw new IllegalValueException("id must be positive value", TagRepository.TABLE_NAME, "id", id);
        return tagRepository.findById(id)
                .switchIfEmpty(Mono.error(() ->
                        new IllegalValueException("Tag not available by given id", TagRepository.TABLE_NAME, "id", id))
                )
                .flatMap(savedTag -> {
                    savedTag.setName(tag.getName());
                    return tagRepository.save(savedTag);
                });
    }

    @Override
    public Flux<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public Flux<Tag> findAllByPostId(int postId) throws IllegalValueException{
        if (postId <= 0)
            throw new IllegalValueException("postId must be positive value", TagRepository.JOIN_TABLE_NAME, "post_id", postId);
        return tagRepository.findAllByPostId(postId);
    }

    @Override
    public Flux<TagDeep> findAllDeep() {
        return tagRepository.findAllDeep();
    }

    @Override
    public Mono<Integer> deleteById(int id)  throws IllegalValueException{
        if (id <= 0)
            throw new IllegalValueException("id must be positive value", TagRepository.TABLE_NAME, "id", id);
        return tagRepository.deleteById(id);
    }

}
