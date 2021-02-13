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
    Post findBySubjectAndUrl(String subjectName, String urlEndpoint);

    void deleteByTitle(String title);

    @Query("SELECT COUNT(p) FROM Post p")
    int getAllCount();

    @Query("SELECT COUNT(p) FROM Post p WHERE p.subject.name=?1")
    int  getCountBySubject(String subjectName);

    @Query(value="SELECT * FROM posts WHERE MATCH (title) AGAINST (?1 IN NATURAL LANGUAGE MODE) ORDER BY modified_date DESC LIMIT ?2,?3",
            nativeQuery=true)
    List<Post> search(String searchText,int from,int to);

    @Query(value="SELECT COUNT(*) FROM posts WHERE MATCH (title) AGAINST (?1 IN NATURAL LANGUAGE MODE)"
            ,nativeQuery=true)
    int searchCount(String searchText);


}
