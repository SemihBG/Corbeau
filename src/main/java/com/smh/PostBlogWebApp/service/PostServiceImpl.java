package com.smh.PostBlogWebApp.service;

import com.smh.PostBlogWebApp.entity.Post;
import com.smh.PostBlogWebApp.entity.Subject;
import com.smh.PostBlogWebApp.repository.PostRepository;
import com.smh.PostBlogWebApp.util.search.SearchPage;
import com.smh.PostBlogWebApp.util.search.SearchPageRequest;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@Service
public class PostServiceImpl implements PostService {

    private static final String CACHE_NAME="post";
    private static final String CACHE_ALL_NAME="postAll";
    private static final String CACHE_COUNT_NAME="postCount";
    private static final String CACHE_SEARCH_NAME="search";

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Caching(put = {
            @CachePut(cacheNames = CACHE_NAME,key="#post.urlEndpoint"),
            @CachePut(cacheNames = CACHE_NAME,key="#post.title")
    },      evict = {
            @CacheEvict(cacheNames = CACHE_ALL_NAME,allEntries = true),
            @CacheEvict(cacheNames = CACHE_COUNT_NAME,allEntries = true),
            @CacheEvict(cacheNames = CACHE_NAME,allEntries = true),
            @CacheEvict(cacheNames = CACHE_SEARCH_NAME,allEntries = true)
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
    public Post findBySubjectAndUrl(@NonNull Subject subject,@NonNull String urlEndpoint) {
        return postRepository.findBySubjectAndUrl(subject.getName(),urlEndpoint);
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
            @CacheEvict(cacheNames = CACHE_COUNT_NAME,allEntries = true),
            @CacheEvict(cacheNames = CACHE_SEARCH_NAME,allEntries = true)
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

    @Cacheable(cacheNames = CACHE_SEARCH_NAME)
    @Override
    public SearchPage<Post> search(@NonNull String searchText,
                             @NonNull SearchPageRequest searchPageRequest) throws IllegalArgumentException{
        searchText=searchText.strip();
        if(searchText.isEmpty()){
            throw new IllegalArgumentException("searchText cannot be blank or empty");
        }

        return new SearchPage<>(postRepository.search
                (searchText,searchPageRequest.from(),searchPageRequest.to()),
                searchPageRequest,searchCount(searchText));

    }

    @Cacheable(cacheNames = CACHE_SEARCH_NAME)
    @Override
    public int searchCount(@NonNull String searchText) {
        return postRepository.searchCount(searchText);
    }

}
