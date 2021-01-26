package com.smh.PostBlogWebApp.service;

import com.smh.PostBlogWebApp.entity.Subject;
import com.smh.PostBlogWebApp.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public Subject findByUrl(String url) {
        return subjectRepository.findByUrlEndpoint(url);
    }


}
