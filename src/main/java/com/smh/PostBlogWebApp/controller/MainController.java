package com.smh.PostBlogWebApp.controller;

import com.smh.PostBlogWebApp.entity.Image;
import com.smh.PostBlogWebApp.entity.Post;
import com.smh.PostBlogWebApp.entity.Subject;
import com.smh.PostBlogWebApp.service.ImageService;
import com.smh.PostBlogWebApp.service.PostService;
import com.smh.PostBlogWebApp.service.SubjectService;
import com.smh.PostBlogWebApp.util.Images;
import com.smh.PostBlogWebApp.util.Parameters;
import com.smh.PostBlogWebApp.util.ParsePageCountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MainController {

    public static final int ONE_PAGE_POST_COUNT=7;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private PostService postService;

    @Autowired
    private ImageService imageService;

    @GetMapping
    public String menu(@RequestParam(value = "page",required = false,defaultValue = "1") String page, Model model){
        try {
            int pageCountIndex=Parameters.parsePageCountIndex(page);
            PageRequest pageRequest=PageRequest.of(pageCountIndex, ONE_PAGE_POST_COUNT, Sort.by("modifiedDate").descending());
            Page<Post> postPage=postService.findAll(pageRequest);
            model.addAttribute("subjects",subjectService.findAll());
            model.addAttribute("posts",postPage.toList());
            model.addAttribute("pageCount",pageCountIndex+1);
            model.addAttribute("totalPages",postPage.getTotalPages());
            return "menu";
        } catch (ParsePageCountException e) {
            return "redirect:/";
        }
    }

    @GetMapping("{subjectUrl}")
    public String subject(@RequestParam(value = "page",required = false,defaultValue = "1") String page,
                          @PathVariable("subjectUrl")String subjectUrl,
                          Model model){
        try {
            int pageCountIndex=Parameters.parsePageCountIndex(page);
            Subject subject=subjectService.findByUrlEndpoint(subjectUrl);
            //Redirect to menu, if there is no subject with given PathVariable
            if(subject==null){
                return "redirect:/";
            }
            PageRequest pageRequest=PageRequest.of(pageCountIndex, ONE_PAGE_POST_COUNT, Sort.by("modifiedDate").descending());
            Page<Post> postPage=postService.findAllBySubject(subject,pageRequest);
            model.addAttribute("subject",subject);
            model.addAttribute("subjects",subjectService.findAll());
            List<Post> postList=postPage.toList();
            model.addAttribute("pageCount",pageCountIndex+1);
            model.addAttribute("totalPages",postPage.getTotalPages());
            return "subject";
        } catch (ParsePageCountException e) {
            return "redirect:/";
        }
    }

    @GetMapping("{subjectUrl}/{postUrl}")
    public String post(@PathVariable("subjectUrl") String subjectUrl,
                       @PathVariable("postUrl")String postUrl,
                       Model model){
        Subject subject=subjectService.findByUrlEndpoint(subjectUrl);
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

    @GetMapping(value="image/logo",produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getLogo(){
        return Images.getLogoImageContent();
    }

    @GetMapping(value="/image/icon",produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getIcon(){
        return Images.getIconImageContent();
    }




}
