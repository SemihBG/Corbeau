package com.semihbkgr.corbeau.test;

import com.semihbkgr.corbeau.model.Post;
import com.semihbkgr.corbeau.model.base.AllAuditable;
import reactor.core.publisher.Mono;

public class ModelUtils {

    private static final String DEFAULT_CREATE_BY_VALUE="test";
    private static final String DEFAULT_UPDATED_BY_VALUE="test";

    public static <A extends AllAuditable> A setAuditsOfAuditableModel (A a){
        a.setCreatedBy(DEFAULT_CREATE_BY_VALUE);
        a.setUpdatedBy(DEFAULT_UPDATED_BY_VALUE);
        a.setCreatedAt(System.currentTimeMillis());
        a.setUpdatedAt(System.currentTimeMillis());
        return a;
    }

    public static Post defaultPost(){
        var post= Post.builder()
                .id(0)
                .title("title")
                .content("content")
                .subjectId(1)
                .endpoint("title")
                .build();
        return setAuditsOfAuditableModel(post);
    }
    public static Post defaultSavedPost(){
        var post= Post.builder()
                .id(1)
                .title("title")
                .content("content")
                .subjectId(1)
                .endpoint("title")
                .build();
        return setAuditsOfAuditableModel(post);
    }


    public static Mono<Post> defaultSavedPostMono(){
        var post= Post.builder()
                .id(1)
                .title("title")
                .content("content")
                .subjectId(1)
                .endpoint("title")
                .build();
        return Mono.just(setAuditsOfAuditableModel(post));
    }


}
