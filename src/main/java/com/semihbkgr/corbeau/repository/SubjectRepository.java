package com.semihbkgr.corbeau.repository;

import com.semihbkgr.corbeau.entity.Subject;
import org.springframework.data.repository.CrudRepository;

public interface SubjectRepository extends CrudRepository<Subject,Integer> {
    Subject findByUrlEndpoint(String url);
    Subject findByName(String name);
    void deleteByName(String name);
}
