package com.smh.PostBlogWebApp.service;

import com.smh.PostBlogWebApp.entity.Subject;
import com.smh.PostBlogWebApp.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public List<Subject> findAll() {
        return StreamSupport.stream(subjectRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }

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
