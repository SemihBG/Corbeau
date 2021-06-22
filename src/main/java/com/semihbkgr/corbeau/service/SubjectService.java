package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.entity.Subject;

import java.util.List;

public interface SubjectService {
    Subject save(Subject subject);
    List<Subject> findAll();
    Subject findByUrlEndpoint(String url);
    Subject findByName(String name);
    void deleteByName(String name);
}
