package com.smh.PostBlogWebApp.service;

import com.smh.PostBlogWebApp.entity.Subject;

public interface SubjectService {

    Subject findById(int id);
    Subject save(Subject subject);

}
