package com.smh.PostBlogWebApp.service;

import com.smh.PostBlogWebApp.entity.Post;
import com.smh.PostBlogWebApp.entity.Subject;
import com.smh.PostBlogWebApp.repository.PostRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
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

    private static final String CACHE_NAME="post";
    private static final String CACHE_ALL_NAME="postAll";
    private static final String CACHE_COUNT_NAME="postCount";

    @Autowired
    private PostRepository postRepository;

    @Caching(put = {
            @CachePut(cacheNames = CACHE_NAME,key="#post.urlEndpoint"),
            @CachePut(cacheNames = CACHE_NAME,key="#post.title"),
            @CachePut(cacheNames = CACHE_ALL_NAME,key = "#post.subject + #post.urlEndpoint")
    },      evict = {
            @CacheEvict(cacheNames = CACHE_ALL_NAME,allEntries = true),
            @CacheEvict(cacheNames = CACHE_COUNT_NAME)
    })
    @Override
    public Post save(@NonNull Post post) {
        return postRepository.save(post);
    }

    @Cacheable(cacheNames = CACHE_ALL_NAME)
    @Override
    public Page<Post> findAll(@NonNull Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Cacheable(cacheNames = CACHE_ALL_NAME)
    @Override
    public Page<Post> findAllBySubject(@NonNull Subject subject,@NonNull Pageable pageable) {
        return postRepository.findAllBySubject(subject,pageable);
    }

    @Cacheable(cacheNames = CACHE_NAME)
    @Nullable
    @Override
    public Post findBySubjectAndUrl(@NonNull Subject subject,@NonNull String url) {
        return postRepository.findBySubjectAndUrl(subject.getName(),url);
    }

    @Cacheable(cacheNames = CACHE_NAME)
    @Nullable
    @Override
    public Post findByTitle(@NonNull String title) {
        return postRepository.findByTitle(title);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = CACHE_NAME,allEntries = true),
            @CacheEvict(cacheNames = CACHE_ALL_NAME,allEntries = true),
            @CacheEvict(cacheNames = CACHE_COUNT_NAME,allEntries = true)
    })
    @Transactional
    @Override
    public void deleteByTitle(@NonNull String title) {
        postRepository.deleteByTitle(title);
    }

    @Cacheable(cacheNames = CACHE_COUNT_NAME)
    @Override
    public int getAllCount() {
        return postRepository.getAllCount();
    }

    @Cacheable(cacheNames = CACHE_COUNT_NAME)
    @Override
    public int getCountBySubject(@NonNull Subject subject) {
        return postRepository.getCountBySubject(Objects.requireNonNull(subject.getName()));
    }

}
