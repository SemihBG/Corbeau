package com.smh.PostBlogWebApp.service;

import com.smh.PostBlogWebApp.entity.Post;
import com.smh.PostBlogWebApp.entity.Subject;
import com.smh.PostBlogWebApp.repository.PostRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Post save(@NonNull Post post) {
        return postRepository.save(post);
    }

    @Override
    public Page<Post> findAll(@NonNull Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public Page<Post> findAllBySubject(@NonNull Subject subject,@NonNull Pageable pageable) {
        return postRepository.findAllBySubject(subject,pageable);
    }

    @Nullable
    @Override
    public Post findBySubjectAndUrl(@NonNull Subject subject,@NonNull String url) {
        return postRepository.findBySubjectAndUrl(subject.getName(),url);
    }

    @Nullable
    @Override
    public Post findByTitle(@NonNull String title) {
        return postRepository.findByTitle(title);
    }

    @Transactional
    @Override
    public void deleteByTitle(@NonNull String title) {
        postRepository.deleteByTitle(title);
    }

}
