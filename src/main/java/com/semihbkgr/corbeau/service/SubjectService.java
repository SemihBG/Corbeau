package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.error.IllegalValueException;
import com.semihbkgr.corbeau.model.Subject;
import com.semihbkgr.corbeau.model.projection.SubjectDeep;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SubjectService {

    Mono<Subject> save(Subject subject);

    Flux<Subject> findAll();

    Flux<SubjectDeep> findAllDeep();

    Mono<Subject> findById(int id) throws IllegalValueException;

    Mono<SubjectDeep> findByNameDeep(String name) throws IllegalValueException;

    Mono<Subject> update(int id, Subject subject) throws IllegalValueException;

    Mono<Void> deleteById(int id) throws IllegalValueException;

}
