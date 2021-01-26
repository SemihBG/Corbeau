package com.smh.PostBlogWebApp.service;

import com.smh.PostBlogWebApp.entity.Subject;

import java.util.List;

public interface SubjectService {
    Subject save(Subject subject);
    List<Subject> findAll();
    Subject findByUrlEndpoint(String url);
    Subject findByName(String name);
    void deleteByName(String name);
}
