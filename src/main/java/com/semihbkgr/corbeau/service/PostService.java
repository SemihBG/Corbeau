package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.error.IllegalValueException;
import com.semihbkgr.corbeau.model.Post;
import com.semihbkgr.corbeau.model.projection.PostDeep;
import com.semihbkgr.corbeau.model.projection.PostInfo;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface PostService {

    Mono<Post> save(Post post);

    Mono<Post> update(int id, Post post) throws IllegalValueException;

    Mono<Post> findByEndpoint(String endpoint);

    Flux<PostDeep> findAllDeep(Pageable pageable);

    Flux<PostDeep> findAllByActivatedDeep(boolean activated, Pageable pageable);

    Flux<PostDeep> findAllByTagIdAndActivatedDeep(int tagId, boolean activated, Pageable pageable) throws IllegalValueException;

    Flux<PostInfo> findAllActivatedBySubjectIdInfo(int subjectId, Pageable pageable) throws IllegalValueException;

    Flux<PostDeep> searchByTitleAndActivatedDeep(String title, boolean acitavated);

    Mono<Long> count();

    Mono<Long> countBySubjectId(int subjectId) throws IllegalValueException;

    Mono<Long> countBySubjectIdAndActivated(int subjectId, boolean activated) throws IllegalValueException;

    Mono<Integer> deletePost(int id) throws IllegalValueException;

    Mono<Void> updateTagPostJoin(int postId, List<Integer> tagsId);

}
