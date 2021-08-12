package com.semihbkgr.corbeau.repository;

import com.semihbkgr.corbeau.model.Post;
import com.semihbkgr.corbeau.model.projection.PostInfo;
import com.semihbkgr.corbeau.model.projection.PostShallow;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostRepository {

    String TABLE_NAME="posts";

    Mono<Post> save(Post post);

    Mono<Post> update(Post post);

    Mono<Post> findById(int id);

    Mono<Post> findByEndpoint(String endpoint);

    Flux<PostShallow> findAllShallow(Pageable pageable);

    Flux<PostShallow> findAllByActivatedShallow(boolean activated,Pageable pageable);

    Flux<PostShallow> findAllByTagIdAndActivatedShallow(int tagId,boolean activated,Pageable pageable);

    Flux<PostInfo> findAllActivatedBySubjectIdInfo(int subjectId, Pageable pageable);

    Flux<PostShallow> searchByActivatedShallow(String s,boolean activated,Pageable pageable);

    Mono<Long> count();

    Mono<Long> countBySubjectId(int subjectId);

    Mono<Long> countByTagIdAndActivated(int tagId, boolean activated);

    Mono<Long> countBySubjectIdAndActivated(int subjectId,boolean activated);

}
