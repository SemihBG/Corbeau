package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.model.Post;
import com.semihbkgr.corbeau.model.Subject;
import com.semihbkgr.corbeau.util.search.SearchPage;
import com.semihbkgr.corbeau.util.search.SearchPageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    Post save(Post post);
    Page<Post> findAll(Pageable pageable);
    Page<Post> findAllBySubject(Subject subject, Pageable pageable);
    Post findBySubjectAndUrl(Subject subject,String url);
    Post findByTitle(String title);
    void deleteByTitle(String title);
    int getAllCount();
    int getCountBySubject(Subject subject);
    SearchPage<Post> search(String searchText, SearchPageRequest searchPageRequest);
    int searchCount(String searchText);
    void updateViewCount(String urlEndpoint,int viewCount);
    void clearAllCaches();
}
