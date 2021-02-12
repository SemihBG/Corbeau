package com.smh.PostBlogWebApp.service;

import com.smh.PostBlogWebApp.entity.Post;
import com.smh.PostBlogWebApp.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {

    Post save(Post post);
    Page<Post> findAll(Pageable pageable);
    Page<Post> findAllBySubject(Subject subject, Pageable pageable);
    Post findBySubjectAndUrl(Subject subject,String url);
    Post findByTitle(String title);
    void deleteByTitle(String title);
    int getAllCount();
    int getCountBySubject(Subject subject);
    List<Post> search(String searchText);
}
