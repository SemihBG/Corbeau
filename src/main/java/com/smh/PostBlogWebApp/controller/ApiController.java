package com.smh.PostBlogWebApp.controller;

import com.smh.PostBlogWebApp.entity.Image;
import com.smh.PostBlogWebApp.entity.Post;
import com.smh.PostBlogWebApp.entity.Subject;
import com.smh.PostBlogWebApp.service.ImageService;
import com.smh.PostBlogWebApp.service.PostService;
import com.smh.PostBlogWebApp.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private PostService postService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/post")
    public List<Post> listAllPosts(
            @RequestParam(name="subject",required = false,defaultValue = "all")
                    String subjectName){
        if(!subjectName.equals("all")){
            Subject subject=subjectService.findByName(subjectName);
            if(subject!=null){
                return postService.findAllBySubject(subject,Pageable.unpaged())
                        .toList();
            }
        }
        return postService.findAll(Pageable.unpaged()).toList();
    }

    @GetMapping("/subject")
    public List<Subject> listAllSubjects(){
        return subjectService.findAll();
    }

    @GetMapping("/image")
    public List<Image> listAllImage(){
        return imageService.findAll();
    }

    @GetMapping("/post/{title}")
    public Post post(@PathVariable String title){
        return postService.findByTitle(title);
    }

}
