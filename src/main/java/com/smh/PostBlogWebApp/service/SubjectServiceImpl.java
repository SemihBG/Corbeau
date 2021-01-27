package com.smh.PostBlogWebApp.service;

import com.smh.PostBlogWebApp.entity.Subject;
import com.smh.PostBlogWebApp.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SubjectServiceImpl implements SubjectService {

    private static final String CACHE_NAME="subject";
    private static final String CACHE_ALL_NAME="subjectAll";

    @Autowired
    private SubjectRepository subjectRepository;

    @Caching(
            evict = {
                    @CacheEvict(cacheNames = CACHE_ALL_NAME,allEntries = true)
            },
            put = {
                    @CachePut(cacheNames = CACHE_NAME,key = "#subject.name"),
                    @CachePut(cacheNames = CACHE_NAME,key = "#subject.urlEndpoint")
            }
    )
    @Override
    public Subject save(Subject subject) {
        return subjectRepository.save(Objects.requireNonNull(subject));
    }

    @Cacheable(cacheNames = CACHE_ALL_NAME)
    @Override
    public List<Subject> findAll() {
        return StreamSupport.stream(subjectRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }

    @Cacheable(cacheNames = CACHE_NAME)
    @Nullable
    @Override
    public Subject findByUrlEndpoint(String url) {
        return subjectRepository.findByUrlEndpoint(Objects.requireNonNull(url));
    }

    @Cacheable(cacheNames = CACHE_NAME)
    @Nullable
    @Override
    public Subject findByName(String name) {
        return subjectRepository.findByName(Objects.requireNonNull(name));
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = CACHE_ALL_NAME,allEntries = true),
            @CacheEvict(cacheNames = CACHE_NAME)
        }
    )
    @Transactional
    @Override
    public void deleteByName(String name) {
        subjectRepository.deleteByName(Objects.requireNonNull(name));
    }

}
