package com.smh.PostBlogWebApp.service;

import com.smh.PostBlogWebApp.entity.Post;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SubjectServiceImplTest {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private PostService postService;

    @Test
    public void test(){
        for (Post java : postService.findAllBySubject(subjectService.findByName("Java"), Pageable.unpaged()).toList()) {
            System.out.println(java.getTitle());
            System.out.println(java.getClass());
        }
    }

}