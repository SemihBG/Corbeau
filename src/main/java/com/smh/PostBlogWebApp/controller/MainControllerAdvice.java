package com.smh.PostBlogWebApp.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class MainControllerAdvice {

    @ExceptionHandler(Exception.class)
    public String handle(Exception e, Model model){
        model.addAttribute("exception",e.getMessage());
        return "handle";
    }

}
