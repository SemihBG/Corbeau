package com.semihbkgr.corbeau;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class PostBlogWebAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostBlogWebAppApplication.class, args);
    }

}
