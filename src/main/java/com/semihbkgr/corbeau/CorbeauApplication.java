package com.semihbkgr.corbeau;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CorbeauApplication {

    //TODO check post is activated before display
    //TODO next page bug fixing
    //TODO Better comment input and validation and paged comment load
    public static void main(String[] args) {
        SpringApplication.run(CorbeauApplication.class, args);
    }

}
