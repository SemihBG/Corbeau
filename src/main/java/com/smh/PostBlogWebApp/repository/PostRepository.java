package com.smh.PostBlogWebApp.repository;

import com.smh.PostBlogWebApp.entity.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post,Integer> {
}
