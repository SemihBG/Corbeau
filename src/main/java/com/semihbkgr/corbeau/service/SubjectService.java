package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.model.Subject;
import reactor.core.publisher.Mono;

import java.util.List;

public interface SubjectService {

    Mono<Subject> save(Subject subject);

    /*
    Subject save(Subject subject);
    List<Subject> findAll();
    Subject findByUrlEndpoint(String url);
    Subject findByName(String name);
    void deleteByName(String name);

     */
}
