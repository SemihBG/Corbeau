package com.semihbkgr.corbeau.repository;

import com.semihbkgr.corbeau.model.Post;
import com.semihbkgr.corbeau.model.projection.PostShallow;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface PostRepository extends R2dbcRepository<Post, String> {

    @Query("SELECT db.posts.id, db.posts.title, db.posts.subject_id, db.posts.created_by, db.posts.updated_by, db.posts.created_at, db.posts.updated_at,db.subjects.name as subject_name  FROM db.posts LEFT JOIN db.subjects ON db.posts.subject_id = db.subjects.id")
    Flux<PostShallow> findAllShallow();

}
