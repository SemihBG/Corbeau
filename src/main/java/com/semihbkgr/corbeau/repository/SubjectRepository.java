package com.semihbkgr.corbeau.repository;

import com.semihbkgr.corbeau.model.Subject;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.CrudRepository;

public interface SubjectRepository extends R2dbcRepository<Subject,Integer> {

    String TABLE_NAME="subjects";

}

