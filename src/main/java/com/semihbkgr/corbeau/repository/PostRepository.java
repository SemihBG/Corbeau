package com.semihbkgr.corbeau.repository;

import com.semihbkgr.corbeau.model.Post;
import com.semihbkgr.corbeau.model.projection.PostInfo;
import com.semihbkgr.corbeau.model.projection.PostDeep;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface PostRepository {

    String TABLE_NAME="posts";

    Mono<Post> save(Post post);

    Mono<Post> update(Post post);

    Mono<Post> findById(int id);

    Mono<Post> findByEndpoint(String endpoint);

    Flux<PostDeep> findAllDeep(Pageable pageable);

    Flux<PostDeep> findAllByActivatedDeep(boolean activated, Pageable pageable);

    Flux<PostDeep> findAllByTagIdAndActivatedDeep(int tagId, boolean activated, Pageable pageable);

    Flux<PostInfo> findAllActivatedBySubjectIdInfo(int subjectId, Pageable pageable);

    Flux<PostDeep> searchByTitleAndActivatedDeep(String title, boolean activated);

    Mono<Long> count();

    Mono<Long> countBySubjectId(int subjectId);

    Mono<Long> countByTagIdAndActivated(int tagId, boolean activated);

    Mono<Long> countBySubjectIdAndActivated(int subjectId,boolean activated);

    Mono<Integer> deleteByPostId(int id);

    Mono<Void> updateTagPostJoin(int postId, List<Integer> tagsId);

}
