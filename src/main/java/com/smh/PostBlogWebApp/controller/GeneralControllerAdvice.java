package com.smh.PostBlogWebApp.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralControllerAdvice {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e){
        return e.getMessage();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDataIntegrityViolationException(Exception e){
        return "Duplication is occurred for restricted column";
    }


}
