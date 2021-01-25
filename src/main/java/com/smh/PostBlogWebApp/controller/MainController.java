package com.smh.PostBlogWebApp.controller;

import com.smh.PostBlogWebApp.entity.Post;
import com.smh.PostBlogWebApp.entity.Subject;
import com.smh.PostBlogWebApp.service.PostService;
import com.smh.PostBlogWebApp.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.server.PathParam;
import java.util.List;

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

    @GetMapping("{subjectName}")
    public String subject(@PathVariable("subjectName")String subjectName,Model model){
        Subject subject=subjectService.findByName(subjectName);
        if(subject==null){
            return "redirect:/";
        }
        model.addAttribute("subject",subject);
        model.addAttribute("subjects",subjectService.findAll());
        List<Post> postList=postService.findAllBySubject(subject);
        model.addAttribute("posts",postList);
        model.addAttribute("count",postList.size());
        return "subject";
    }


}
