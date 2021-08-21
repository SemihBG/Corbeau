package com.semihbkgr.corbeau.controller;

import com.semihbkgr.corbeau.model.Comment;
import com.semihbkgr.corbeau.model.Post;
import com.semihbkgr.corbeau.model.Subject;
import com.semihbkgr.corbeau.model.Tag;
import com.semihbkgr.corbeau.service.*;
import com.semihbkgr.corbeau.util.ParameterUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@SuppressWarnings("DuplicatedCode")
@Controller
@RequestMapping("/moderation")
@RequiredArgsConstructor
public class ModerationController {

    static final int POST_COUNT = 5;
    static final int IMAGE_COUNT = 5;
    static final int COMMENT_COUNT = 10;

    private final ModeratorService moderatorService;
    private final RoleService roleService;
    private final SubjectService subjectService;
    private final PostService postService;
    private final TagService tagService;
    private final ImageContentService imageContentService;
    private final ImageService imageService;
    private final CommentService commentService;

    @GetMapping()
    public String moderation() {
        return "moderation/index";
    }

    @GetMapping("/login")
    public String login() {
        return "/moderation/login";
    }

    @GetMapping("/menu")
    public Mono<String> menu(final Model model) {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(authentication -> {
                    model.addAttribute("name", authentication.getName());
                    return "/moderation/menu";
                });
    }

    @GetMapping("/profile/{name}")
    public Mono<String> profile(@PathVariable("name") String name, final Model model) {
        return Mono.from(moderatorService.findByName(name))
                .flatMap(moderator -> {
                    model.addAttribute("moderator", moderator);
                    return roleService.findById(moderator.getId());
                })
                .flatMap(role -> {
                    model.addAttribute("role", role);
                    return ReactiveSecurityContextHolder.getContext();
                })
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(ModeratorDetailsService.ModeratorDetails.class::cast)
                .map(moderatorDetails -> {
                    model.addAttribute("me", moderatorDetails.getUsername().equals(name));
                    return "/moderation/profile";
                });
    }

    @GetMapping("/subject")
    public Mono<String> subject(final Model model) {
        var subjectsDeepReactiveData = new ReactiveDataDriverContextVariable(subjectService.findAllDeep(), 1);
        model.addAttribute("subjects", subjectsDeepReactiveData);
        return Mono.from(ReactiveSecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(authentication -> {
                    model.addAttribute("name", authentication.getName());
                    return "/moderation/subject";
                });
    }

    @PostMapping("/subject")
    public Mono<String> subjectSave(@Valid @ModelAttribute Subject subject) {
        return Mono.from(subjectService.save(subject))
                .then(Mono.just("redirect:/moderation/subject"));
    }

    @PostMapping("/subject/{id}")
    public Mono<String> subjectUpdate(@PathVariable("id") int id, @ModelAttribute Subject subject) {
        return Mono
                .from(subjectService.update(id, subject))
                .thenReturn("redirect:/moderation/subject");
    }


    @GetMapping("/tag")
    public Mono<String> tag(Model model) {
        var tagsDeepReactiveData = new ReactiveDataDriverContextVariable(tagService.findAllDeep(), 1);
        model.addAttribute("tags", tagsDeepReactiveData);
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(authentication -> {
                    model.addAttribute("name", authentication.getName());
                    return "/moderation/tag";
                });
    }

    @PostMapping("/tag")
    public Mono<String> tagSave(@ModelAttribute Tag tag){
        return tagService.save(tag)
                .thenReturn("redirect:/moderation/tag");
    }

    @PostMapping("/tag/{id}")
    public Mono<String> tagUpdate(@PathVariable("id") int id,@ModelAttribute Tag tag){
        return tagService.update(id,tag)
                .thenReturn("redirect:/moderation/tag");
    }

    @GetMapping("/post")
    public Mono<String> post(final Model model, @RequestParam(value = "p", required = false, defaultValue = "1") String pageStr) {
        int index = ParameterUtils.parsePageToIndex(pageStr);
        if (index == -1) return Mono.just("redirect:/moderation/post?p=1");
        var postsReactiveData = new ReactiveDataDriverContextVariable(postService.findAllDeep(PageRequest.of(index, POST_COUNT).withSort(Sort.by("updated_at").descending())), 1);
        model.addAttribute("posts", postsReactiveData);
        return postService.count()
                .flatMap(count -> {
                    var pageCount = (int) Math.ceil((double) count / POST_COUNT);
                    model.addAttribute("count", count);
                    model.addAttribute("page", index + 1);
                    model.addAttribute("pageCount", pageCount);
                    model.addAttribute("hasPrevious", index > 0);
                    model.addAttribute("hasNext", index + 1 < pageCount);
                    return ReactiveSecurityContextHolder.getContext();
                })
                .map(SecurityContext::getAuthentication)
                .map(authentication -> {
                    model.addAttribute("name", authentication.getName());
                    return "/moderation/post";
                });
    }

    @GetMapping("/post/save")
    public Mono<String> postSave(final Model model) {
        var subjectsReactiveData = new ReactiveDataDriverContextVariable(subjectService.findAll(), 1);
        model.addAttribute("subjects", subjectsReactiveData);
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(authentication -> {
                    model.addAttribute("name", authentication.getName());
                    return "/moderation/post-save";
                });
    }

    @GetMapping("/post/{endpoint}")
    public Mono<String> postUpdate(@PathVariable("endpoint") String endpoint, final Model model) {
        var subjectsReactiveData = new ReactiveDataDriverContextVariable(subjectService.findAll(), 1);
        model.addAttribute("subjects", subjectsReactiveData);
        return postService.findByEndpoint(endpoint)
                .flatMap(post -> {
                    model.addAttribute("post", post);
                    return subjectService.findById(post.getSubjectId());
                })
                .flatMap(subject -> {
                    model.addAttribute("subject", subject);
                    return ReactiveSecurityContextHolder.getContext();
                })
                .map(SecurityContext::getAuthentication)
                .map(authentication -> {
                    model.addAttribute("name", authentication.getName());
                    return "/moderation/post-update";
                });
    }

    @PostMapping("/post")
    public Mono<String> postSaveProcess(@Valid @ModelAttribute Post post, final Model model) {
        return postService.save(post)
                .map(savedPost -> {
                    model.addAttribute("post", savedPost);
                    return "redirect:/moderation/post/" + savedPost.getEndpoint();
                });
    }

    @PostMapping("/post/{id}")
    public Mono<String> postUpdateProcess(@PathVariable("id") int id, @ModelAttribute Post post, final Model model) {
        return postService.update(id, post)
                .map(updatedPost -> {
                    model.addAttribute("post", updatedPost);
                    return "redirect:/moderation/post/" + post.getEndpoint();
                });
    }

    @GetMapping("/image")
    public Mono<String> image(@RequestParam(value = "p", required = false, defaultValue = "1") String pageStr,
                              final Model model) {
        int index = ParameterUtils.parsePageToIndex(pageStr);
        if (index == -1) return Mono.just("redirect:/moderation/image?p=1");
        var imagesReactiveData = new ReactiveDataDriverContextVariable(imageService.findAll(PageRequest.of(
                index, IMAGE_COUNT, Sort.by("updatedAt").descending())),
                1
        );
        model.addAttribute("images", imagesReactiveData);
        return imageService.count()
                .flatMap(count -> {
                    var pageCount = (int) Math.ceil((double) count / POST_COUNT);
                    model.addAttribute("count", count);
                    model.addAttribute("page", index + 1);
                    model.addAttribute("pageCount", pageCount);
                    model.addAttribute("hasPrevious", index > 0);
                    model.addAttribute("hasNext", index + 1 < pageCount);
                    return ReactiveSecurityContextHolder.getContext();
                })
                .map(SecurityContext::getAuthentication)
                .map(authentication -> {
                    model.addAttribute("name", authentication.getName());
                    return "/moderation/image";
                });
    }

    @GetMapping("/image/save")
    public Mono<String> imageSave(final Model model) {
        return ReactiveSecurityContextHolder
                .getContext()
                .map(SecurityContext::getAuthentication)
                .map(authentication -> {
                    model.addAttribute("name", authentication.getName());
                    return "/moderation/image-save";
                });
    }

    @PostMapping("/image/save")
    public Mono<String> imageSaveProcess(@RequestPart("name") String name,
                                         @RequestPart("content") Mono<FilePart> contentMono) {
        return imageContentService
                .save(name, contentMono)
                .flatMap(imageService::save)
                .then(Mono.just("redirect:/moderation/image"));
    }

    @GetMapping("/comment")
    public Mono<String> comment(@RequestParam(value = "p", required = false, defaultValue = "1") String pageStr,
                                final Model model){
        int index = ParameterUtils.parsePageToIndex(pageStr);
        if (index == -1) return Mono.just("redirect:/moderation/comment?p=1");
        var commentsReactiveData=new ReactiveDataDriverContextVariable(
                commentService.findAllDeep(PageRequest.of(index,COMMENT_COUNT,Sort.by("updated_at").descending()))
                ,1);
        model.addAttribute("comments",commentsReactiveData);
        return ReactiveSecurityContextHolder
                .getContext()
                .map(SecurityContext::getAuthentication)
                .map(authentication -> {
                    model.addAttribute("name", authentication.getName());
                    return "/moderation/comment";
                });
    }

    @PostMapping("/comment/{id}")
    public Mono<String> commentUpdate(@PathVariable("id") int id, @ModelAttribute Comment comment){
        return commentService.update(id,comment)
                .thenReturn("redirect:/moderation/comment");
    }

    @PostMapping("/comment/delete/{id}")
    public Mono<String> commentUpdate(@PathVariable("id") int id){
        return commentService.deleteById(id)
                .thenReturn("redirect:/moderation/comment");
    }

}
