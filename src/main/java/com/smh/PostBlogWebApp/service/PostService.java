package com.smh.PostBlogWebApp.service;

import com.smh.PostBlogWebApp.entity.Post;

import java.util.List;

public interface PostService {

    List<Post> findAll();
    Post findById(int id);
    Post save(Post post);

}
