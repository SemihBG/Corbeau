package com.smh.PostBlogWebApp.controller;

import com.smh.PostBlogWebApp.entity.Image;
import com.smh.PostBlogWebApp.entity.Post;
import com.smh.PostBlogWebApp.entity.Subject;
import com.smh.PostBlogWebApp.service.ImageService;
import com.smh.PostBlogWebApp.service.PostService;
import com.smh.PostBlogWebApp.service.SubjectService;
import com.smh.PostBlogWebApp.util.Images;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private PostService postService;

    @Autowired
    private ImageService imageService;

    @GetMapping
    public String menu(Model model){
        model.addAttribute("subjects",subjectService.findAll());
        model.addAttribute("posts",postService.findAll());
        return "menu";
    }

    @GetMapping("{subjectUrl}")
    public String subject(@PathVariable("subjectUrl")String subjectUrl,
                          Model model){
        Subject subject=subjectService.findByUrl(subjectUrl);
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

    @GetMapping("{subjectUrl}/{postUrl}")
    public String post(@PathVariable("subjectUrl")String subjectUrl,
                       @PathVariable("postUrl")String postUrl,
                       Model model){
        Subject subject=subjectService.findByUrl(subjectUrl);
        if(subject==null){
            return "redirect:/";
        }
        Post post=postService.findBySubjectAndUrl(subject,postUrl);
        if(post==null){
            return "redirect:/"+subjectUrl;
        }
        model.addAttribute("subjects",subjectService.findAll());
        model.addAttribute("post",post);
        return "post";
    }

    @GetMapping(value="/image/{urlEndpoint}",produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getImage(@PathVariable("urlEndpoint") String urlEndpoint){
        Image image=imageService.getByEndPoint(urlEndpoint);
        if(image!=null){
            return image.getContent();
        }
        return Images.getImageNotFoundImageContent();
    }

    @GetMapping(value="/image/icon",produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getIcon(){
        return Images.getIconImageContent();
    }

}
