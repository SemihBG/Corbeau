package com.smh.PostBlogWebApp.service;

import com.smh.PostBlogWebApp.entity.Post;
import com.smh.PostBlogWebApp.entity.Subject;
import com.smh.PostBlogWebApp.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> findAll() {
        return StreamSupport.stream(postRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }

    @Override
    public List<Post> findAllBySubject(Subject subject) {
        return postRepository.findAllBySubject(subject);
    }

    @Nullable
    @Override
    public Post findById(int id) {
        return postRepository.findById(id).orElse(null);
    }

    @Nullable
    @Override
    public Post findByTitle(String title) {
        return postRepository.findAllByTitle(title);
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(Objects.requireNonNull(post));
    }

}
