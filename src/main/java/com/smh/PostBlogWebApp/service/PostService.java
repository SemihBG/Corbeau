package com.smh.PostBlogWebApp.service;

import com.smh.PostBlogWebApp.entity.Post;
import com.smh.PostBlogWebApp.entity.Subject;

import java.util.List;

public interface PostService {

    Post save(Post post);
    List<Post> findAll();
    List<Post> findAllBySubject(Subject subject);
    Post findBySubjectAndUrl(Subject subject,String url);
    Post findByTitle(String title);
    void deleteByTitle(String title);

}
