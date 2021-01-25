package com.smh.PostBlogWebApp.service;

import com.smh.PostBlogWebApp.entity.Post;
import com.smh.PostBlogWebApp.entity.Subject;

import java.util.List;

public interface PostService {

    List<Post> findAll();
    List<Post> findAllBySubject(Subject subject);
    Post findById(int id);
    Post findByTitle(String title);
    Post save(Post post);

}
