package com.smh.PostBlogWebApp.service;

import com.smh.PostBlogWebApp.entity.Subject;

import java.util.List;

public interface SubjectService {

    List<Subject> findAll();
    Subject findById(int id);
    Subject save(Subject subject);

}