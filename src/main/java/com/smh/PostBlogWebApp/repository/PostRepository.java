package com.smh.PostBlogWebApp.repository;

import com.smh.PostBlogWebApp.entity.Post;
import com.smh.PostBlogWebApp.entity.Subject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post,Integer> {

    List<Post> findAllBySubject(Subject subject);
    Post findByTitle(String title);

    @Query("SELECT p FROM Post p WHERE p.subject.name= ?1 AND p.urlEndpoint= ?2")
    Post findBySubjectAndUrl(String subjectName, String url);

    void deleteByTitle(String title);

}
