package com.semihbkgr.corbeau.test;

import com.semihbkgr.corbeau.model.Post;
import com.semihbkgr.corbeau.model.Role;
import com.semihbkgr.corbeau.model.Subject;
import com.semihbkgr.corbeau.model.base.AllAuditable;
import reactor.core.publisher.Mono;

public class ModelUtils {

    private static final String DEFAULT_CREATE_BY_VALUE = "test";
    private static final String DEFAULT_UPDATED_BY_VALUE = "test";

    public static <A extends AllAuditable> A setAuditsOfAuditableModel(A a) {
        a.setCreatedBy(DEFAULT_CREATE_BY_VALUE);
        a.setUpdatedBy(DEFAULT_UPDATED_BY_VALUE);
        a.setCreatedAt(System.currentTimeMillis());
        a.setUpdatedAt(System.currentTimeMillis());
        return a;
    }

    public static Post defaultSavePost() {
        var post = Post.builder()
                .id(0)
                .title("title")
                .content("content")
                .subjectId(1)
                .endpoint("endpoint")
                .activated(false)
                .viewCount(500)
                .build();
        return setAuditsOfAuditableModel(post);
    }

    public static Mono<Post> defaultSavePostMono() {
        return Mono.just(defaultSavePost());
    }

    public static Post defaultSavedPost(int id) {
        var post = Post.builder()
                .id(id)
                .title("title")
                .content("content")
                .subjectId(1)
                .endpoint("endpoint")
                .activated(false)
                .viewCount(500)
                .build();
        return setAuditsOfAuditableModel(post);
    }

    public static Mono<Post> defaultSavedPostMono(int id) {
        return Mono.just(defaultSavedPost(id));
    }

    public static Post defaultUpdatePost() {
        var post = Post.builder()
                .id(0)
                .title("updated-title")
                .content("updated-content")
                .subjectId(2)
                .endpoint("updated-endpoint")
                .activated(true)
                .viewCount(500)
                .build();
        return setAuditsOfAuditableModel(post);
    }

    public static Mono<Post> defaultUpdatePostMono() {
        return Mono.just(defaultUpdatePost());
    }

    public static Post defaultUpdatedPost(int id) {
        var post = Post.builder()
                .id(id)
                .title("updated-title")
                .content("updated-content")
                .subjectId(2)
                .endpoint("updated-endpoint")
                .activated(true)
                .viewCount(500)
                .build();
        return setAuditsOfAuditableModel(post);
    }

    public static Mono<Post> defaultUpdatedPostMono(int id) {
        return Mono.just(defaultUpdatedPost(id));
    }

    public static Role defaultSaveRole(){
        return Role.builder()
                .id(0)
                .name("role")
                .build();
    }

    public static Subject defaultSaveSubject(){
        var subject = Subject.builder()
                .id(0)
                .name("name")
                .build();
        return setAuditsOfAuditableModel(subject);
    }

    public static Subject defaultSavedSubject(){
        var subject=Subject.builder()
                .id(1)
                .name("name")
                .build();
        return setAuditsOfAuditableModel(subject);
    }

    public static Subject defaultUpdateSubject(){
        var subject= Subject.builder()
                .id(0)
                .name("updated-name")
                .build();
        return setAuditsOfAuditableModel(subject);
    }

}
