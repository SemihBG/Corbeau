package com.semihbkgr.corbeau.controller;

import com.semihbkgr.corbeau.component.NameSurnameOfferComponent;
import com.semihbkgr.corbeau.model.Post;
import com.semihbkgr.corbeau.service.CommentService;
import com.semihbkgr.corbeau.service.PostService;
import com.semihbkgr.corbeau.service.SubjectService;
import com.semihbkgr.corbeau.service.TagService;
import com.semihbkgr.corbeau.util.ParameterUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Mono;

@SuppressWarnings("DuplicatedCode")
@Controller
@RequiredArgsConstructor
public class ApplicationController {

    static final int POST_PAGE_SIZE = 5;

    private final SubjectService subjectService;
    private final PostService postService;
    private final TagService tagService;
    private final CommentService commentService;
    private final NameSurnameOfferComponent nameSurnameOfferComponent;

    @GetMapping
    public Mono<String> menu(final Model model) {
        var tagsReactiveData = new ReactiveDataDriverContextVariable(tagService.findAllDeep(), 1);
        model.addAttribute("tags", tagsReactiveData);
        return subjectService.findAll()
                .collectList()
                .map(subjectList -> {
                    model.addAttribute("subjects", subjectList);
                    return "menu";
                });
    }

    @GetMapping("/subject/{subject_name}")
    public Mono<String> subject(@PathVariable("subject_name") String subjectName,
                                @RequestParam(value = "p", required = false, defaultValue = "1") String pageStr,
                                final Model model) {
        var index = ParameterUtils.parsePageToIndex(pageStr);
        if (index == -1) return Mono.just("redirect:/subject/" + subjectName + "?p=" + 1);
        return subjectService.findByNameDeep(subjectName)
                .flatMap(subjectDeep -> {
                    model.addAttribute("subject", subjectDeep);
                    var postsInfoReactiveData = new ReactiveDataDriverContextVariable(
                            postService.findAllActivatedBySubjectIdInfo(subjectDeep.getId(),
                                    PageRequest.of(index, POST_PAGE_SIZE, Sort.by("updated_at").descending())),
                            1
                    );
                    model.addAttribute("posts", postsInfoReactiveData);
                    return postService.countBySubjectIdAndActivated(subjectDeep.getId(), true);
                })
                .flatMap(count -> {
                    var pageCount = (int) Math.ceil((double) count / POST_PAGE_SIZE);
                    model.addAttribute("count", count);
                    model.addAttribute("page", index + 1);
                    model.addAttribute("pageCount", pageCount);
                    model.addAttribute("hasPrevious", index > 0);
                    model.addAttribute("hasNext", index + 1 < pageCount);
                    return subjectService.findAll().collectList();
                })
                .map(subjectList -> {
                    model.addAttribute("subjects", subjectList);
                    return "subject";
                });
    }

    @GetMapping("/tag/{tag_name}")
    public Mono<String> tag(@PathVariable("tag_name")String tagName,
                            @RequestParam(value = "p",required = false,defaultValue = "1") String  pageStr,
                            Model model){
        var index = ParameterUtils.parsePageToIndex(pageStr);
        if (index == -1) return Mono.just("redirect:/tag/" + tagName + "?p=" + 1);
        return tagService.findByName(tagName)
                .flatMap(tag-> {
                    model.addAttribute("tag",tag);
                    var postsReactiveData=new ReactiveDataDriverContextVariable(
                            postService.findAllByTagIdAndActivatedShallow(tag.getId(), true,
                                                PageRequest.of(index, POST_PAGE_SIZE, Sort.by("updated_at").descending()))
                            ,1);
                    model.addAttribute("posts",postsReactiveData);
                    return postService.countBySubjectIdAndActivated(tag.getId(),true);
                })
                .doOnNext(count-> model.addAttribute("count",count))
                .thenMany(subjectService.findAll())
                .collectList()
                .map(subjectList -> {
                    model.addAttribute("subjects", subjectList);
                    return "tag";
                });
    }

    @GetMapping("/post/{endpoint}")
    public Mono<String> post(@PathVariable("endpoint") String endpoint, final Model model) {
        var subjectsReactiveData = new ReactiveDataDriverContextVariable(subjectService.findAll(), 1);
        model.addAttribute("subjects", subjectsReactiveData);
        return postService.findByEndpoint(endpoint)
                .filter(Post::isActivated)
                .flatMap(post -> {
                    model.addAttribute("post", post);
                    return commentService.countByPostId(post.getId());
                })
                .flatMap(commentCount -> {
                    model.addAttribute("commentCount", commentCount);
                    return nameSurnameOfferComponent.offer();
                })
                .map(pair -> {
                    model.addAttribute("offerName", pair.getFirst());
                    model.addAttribute("offerSurname", pair.getSecond());
                    return "post";
                });
    }

    @GetMapping("/search")
    public Mono<String> search(@RequestParam(value = "s", required = false) String s) {
        var subjectsReactiveData = new ReactiveDataDriverContextVariable(subjectService.findAll(), 1);
        if (s == null) return Mono.just("redirect: /");
        return Mono.just("search");
    }


}
