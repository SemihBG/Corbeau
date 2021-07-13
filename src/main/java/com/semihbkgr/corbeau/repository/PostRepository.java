package com.semihbkgr.corbeau.repository;

import com.semihbkgr.corbeau.model.Post;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface PostRepository extends R2dbcRepository<Post, String> {




    /*
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

    @Modifying
    @Query(value = "UPDATE posts SET view_count=?2 WHERE url_endpoint=?1 ",nativeQuery=true)
    void updateViewCount(String urlEndpoint,int viewCount);
    */

}
