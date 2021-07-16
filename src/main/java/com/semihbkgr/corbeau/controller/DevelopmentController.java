package com.semihbkgr.corbeau.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Profile("development")
@Controller
@RequestMapping("/dev")
public class DevelopmentController{

    /*
    @GetMapping("/throw/{exception}")
    public void throwException(@PathVariable String exception) throws Throwable {
        throw (Throwable)Class.forName(exception).getConstructor().newInstance();
    }
    */

}
