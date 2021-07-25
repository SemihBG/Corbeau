package com.semihbkgr.corbeau;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CorbeauApplication {

    //TODO check post is activated before display
    //TODO next page bug fixing
    //TODO Better comment input and validation and paged comment load
    //TODO Html page title
    //TODO Html page extract css and js files
    //TODO Html page local bootsrap files
    //TODO remove dto
    //TODO Moderation controller redundant name model attribute
    public static void main(String[] args) {
        SpringApplication.run(CorbeauApplication.class, args);
    }

}
