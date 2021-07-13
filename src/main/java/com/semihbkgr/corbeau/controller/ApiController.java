package com.semihbkgr.corbeau.controller;

import com.semihbkgr.corbeau.model.Image;
import com.semihbkgr.corbeau.model.Post;
import com.semihbkgr.corbeau.model.Subject;
import com.semihbkgr.corbeau.service.ImageService;
import com.semihbkgr.corbeau.service.PostService;
import com.semihbkgr.corbeau.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final SubjectService subjectService;
    private final PostService postService;
    private final ImageService imageService;

    @Autowired
    public ApiController(SubjectService subjectService, PostService postService, ImageService imageService) {
        this.subjectService = subjectService;
        this.postService = postService;
        this.imageService = imageService;
    }

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
