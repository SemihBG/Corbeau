package com.smh.PostBlogWebApp.repository;

import com.smh.PostBlogWebApp.entity.Subject;
import org.springframework.data.repository.CrudRepository;

public interface SubjectRepository extends CrudRepository<Subject,Integer> {
    Subject findByName(String name);
}
