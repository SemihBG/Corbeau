package com.smh.PostBlogWebApp.service;

import com.smh.PostBlogWebApp.entity.Post;

public interface PostService {

    Post findById(int id);
    Post save(Post post);

}
