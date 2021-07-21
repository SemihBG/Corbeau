package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.error.IllegalValueException;
import com.semihbkgr.corbeau.model.Subject;
import com.semihbkgr.corbeau.model.projection.SubjectDeep;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SubjectService {

    Mono<Subject> findById(int id) throws IllegalValueException;

    Flux<SubjectDeep> findAll();

    Mono<Subject> save(Subject subject);

    Mono<Subject> update(int id,Subject subject) throws IllegalValueException;

    Mono<Void> deleteById(int id) throws IllegalValueException;

}
