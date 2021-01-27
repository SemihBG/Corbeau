package com.smh.PostBlogWebApp.repository;

import com.smh.PostBlogWebApp.entity.Post;
import com.smh.PostBlogWebApp.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PostRepository extends PagingAndSortingRepository<Post,Integer> {

    Page<Post> findAllBySubject(Subject subject, Pageable pageable);
    Post findByTitle(String title);

    @Query("SELECT p FROM Post p WHERE p.subject.name= ?1 AND p.urlEndpoint= ?2")
    Post findBySubjectAndUrl(String subjectName, String url);

    void deleteByTitle(String title);

}
