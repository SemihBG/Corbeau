package com.smh.PostBlogWebApp.controller;

import com.smh.PostBlogWebApp.service.PostService;
import com.smh.PostBlogWebApp.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private PostService postService;

    @GetMapping
    public String menu(Model model){
        model.addAttribute("subjects",subjectService.findAll());
        model.addAttribute("posts",postService.findAll());
        return "menu";
    }

}
