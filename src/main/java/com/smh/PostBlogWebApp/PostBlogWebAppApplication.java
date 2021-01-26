package com.smh.PostBlogWebApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PostBlogWebAppApplication{

	//TODO add db.sql to compose file's mysql container as volume

	public static void main(String[] args) {
		SpringApplication.run(PostBlogWebAppApplication.class, args);
	}


}
