package com.semihbkgr.corbeau.repository;

import com.semihbkgr.corbeau.model.Subject;
import com.semihbkgr.corbeau.model.projection.SubjectDeep;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.CrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SubjectRepository {

    String TABLE_NAME="subjects";

    Flux<SubjectDeep> findAll();

    Mono<Subject> findById(int id);

    Mono<Subject> save(Subject subject);

    Mono<Subject> update(Subject subject);

    Mono<Void> deleteById (int id);

}

