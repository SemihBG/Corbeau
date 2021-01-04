package com.smh.PostBlogWebApp.service;

import com.smh.PostBlogWebApp.entity.Subject;
import com.smh.PostBlogWebApp.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Nullable
    @Override
    public Subject findById(int id) {
        return subjectRepository.findById(id).orElse(null);
    }

    @Override
    public Subject save(Subject subject) {
        return subjectRepository.save(Objects.requireNonNull(subject));
    }

}
