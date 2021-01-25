package com.smh.PostBlogWebApp.repository;

import com.smh.PostBlogWebApp.entity.Post;
import com.smh.PostBlogWebApp.entity.Subject;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post,Integer> {
    Post findAllByTitle(String title);
    List<Post> findAllBySubject(Subject subject);
}
