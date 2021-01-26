package com.smh.PostBlogWebApp.service;

import com.smh.PostBlogWebApp.entity.Post;
import com.smh.PostBlogWebApp.entity.Subject;
import com.smh.PostBlogWebApp.repository.PostRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public Post save(Post post) {
        return postRepository.save(Objects.requireNonNull(post));
    }

    @Override
    public List<Post> findAll() {
        return StreamSupport.stream(postRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }

    @Override
    public List<Post> findAllBySubject(Subject subject) {
        return postRepository.findAllBySubject(Objects.requireNonNull(subject));
    }

    @Nullable
    @Override
    public Post findBySubjectAndUrl(@NonNull Subject subject,@NonNull String url) {
        return postRepository.findBySubjectAndUrl(subject.getName(),url);
    }

    @Nullable
    @Override
    public Post findByTitle(String title) {
        return postRepository.findByTitle(Objects.requireNonNull(title));
    }

    @Transactional
    @Override
    public void deleteByTitle(String title) {
        postRepository.deleteByTitle(Objects.requireNonNull(title));
    }

}
