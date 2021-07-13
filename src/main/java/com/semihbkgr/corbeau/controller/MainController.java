package com.semihbkgr.corbeau.controller;

import com.semihbkgr.corbeau.model.Image;
import com.semihbkgr.corbeau.model.Post;
import com.semihbkgr.corbeau.model.Subject;
import com.semihbkgr.corbeau.service.CounterService;
import com.semihbkgr.corbeau.service.ImageService;
import com.semihbkgr.corbeau.service.PostService;
import com.semihbkgr.corbeau.service.SubjectService;
import com.semihbkgr.corbeau.util.*;
import com.semihbkgr.corbeau.util.search.PageAdapter;
import com.semihbkgr.corbeau.util.search.SearchPage;
import com.semihbkgr.corbeau.util.search.SearchPageRequest;
import com.semihbkgr.corbeau.util.search.SearchText;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    public static final int ONE_PAGE_POST_COUNT=5;

    private final SubjectService subjectService;
    private final PostService postService;
    private final ImageService imageService;

    @Autowired
    private CounterService counterService;

    @Autowired
    public MainController(SubjectService subjectService, PostService postService, ImageService imageService) {
        this.subjectService = subjectService;
        this.postService = postService;
        this.imageService = imageService;
    }

    @GetMapping
    public String menu(@RequestParam(value = "page",required = false,defaultValue = "1") String page, Model model){
        try {
            int pageCountIndex=Parameters.parsePageCountIndex(page);
            PageRequest pageRequest=PageRequest.of(pageCountIndex, ONE_PAGE_POST_COUNT, Sort.by("modifiedDate").descending());
            Page<Post> postPage=postService.findAll(pageRequest);
            //Control if exceed total page count.Redirect to last page count if exceeded
            if(pageCountIndex!=0 && postPage.getNumberOfElements()==0){
                return "redirect:/?page="+postPage.getTotalPages();
            }
            addPageDataToModel(model,postPage,pageRequest);
            addRequiredDataToModel(model);
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
            //Control if exceed total page count.Redirect to last page count if exceeded
            if(pageCountIndex!=0 && postPage.getNumberOfElements()==0){
                return "redirect:/"+subjectUrl+"?page="+postPage.getTotalPages();
            }
            addPageDataToModel(model,postPage,pageRequest);
            model.addAttribute("subject",subject);
            addRequiredDataToModel(model);
            return "subject";
        } catch (ParsePageCountException e) {
            return "redirect:/"+subjectUrl;
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
        model.addAttribute("post",post);
        addRequiredDataToModel(model);
        return "post";
    }

    @GetMapping(value="/image/{urlEndpoint}",produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getImage(@PathVariable("urlEndpoint") String urlEndpoint){
        byte[] predefinedImageContent=null;
        if((predefinedImageContent=Images.getPredefinedImage(urlEndpoint))!=null){
            return predefinedImageContent;
        }
        Image image=imageService.getByEndPoint(urlEndpoint);
        if(image!=null){
            return image.getContent();
        }
        return Images.getImageNotFoundImageContent();
    }

    @GetMapping("/s")
    public String search(@RequestParam(value = "text",required = false,defaultValue = "") String text,
                         @RequestParam(value = "page",required = false,defaultValue = "1") String page,
                         Model model){

        try{
            int pageCountIndex=Parameters.parsePageCountIndex(page);
            text=text.strip();
            if(text.isEmpty()){
                return "redirect:/";
            }
            SearchPageRequest searchPageRequest=SearchPageRequest.of(pageCountIndex,ONE_PAGE_POST_COUNT);
            SearchPage<Post> postSearchPage=postService.search(text, searchPageRequest);
            PageAdapter<Post> pageAdapter=PageAdapter.of(postSearchPage);
            //Control if exceed total page count.Redirect to last page count if exceeded
            if(pageCountIndex!=0 && pageAdapter.getNumberOfElements()==0){
                return "redirect:/s/?text="+text+"&page="+pageAdapter.getTotalPages();
            }
            model.addAttribute("current_text",text);
            addPageDataToModel(model, pageAdapter,searchPageRequest);
            addRequiredDataToModel(model);
            return "search";
        }catch (ParsePageCountException ignore){
            return "redirect:/s/?text="+text;
        }

    }

    private void addRequiredDataToModel(@NonNull Model model){
        model.addAttribute("subjects",subjectService.findAll());
        model.addAttribute("text", SearchText.empty());
    }

    private void addPageDataToModel(@NonNull Model model, @NonNull Page page,@NonNull PageRequest pageRequest){
        model.addAttribute("pageCount",pageRequest.getPageNumber()+1);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("posts",page.toList());
        model.addAttribute("firstPost",pageRequest.getPageNumber()*ONE_PAGE_POST_COUNT+1);
        model.addAttribute("lastPost",pageRequest.getPageNumber()*ONE_PAGE_POST_COUNT+
                page.getNumberOfElements());
        model.addAttribute("allPost",page.getTotalElements());

    }



}
