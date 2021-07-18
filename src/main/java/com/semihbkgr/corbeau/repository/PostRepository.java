package com.semihbkgr.corbeau.repository;

import com.semihbkgr.corbeau.model.Post;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface PostRepository extends R2dbcRepository<Post, String> {



}
