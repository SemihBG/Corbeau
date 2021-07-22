package com.semihbkgr.corbeau.controller;

import com.semihbkgr.corbeau.service.PostService;
import com.semihbkgr.corbeau.service.SubjectService;
import com.semihbkgr.corbeau.util.ParameterUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class ApplicationController {

    static final int POST_PAGE_SIZE = 5;

    private final SubjectService subjectService;
    private final PostService postService;

    @GetMapping
    public String menu(final Model model, ServerHttpResponse response) {
        var subjectsReactiveData = new ReactiveDataDriverContextVariable(subjectService.findAll(), 1);
        model.addAttribute("subjects", subjectsReactiveData);
        return "menu";
    }

    @GetMapping("/subject/{subject_name}")
    public Mono<String> subject(@PathVariable("subject_name") String subjectName,
                                @RequestParam(value = "p", required = false, defaultValue = "1") String pageStr,
                                final Model model) {
        var index = ParameterUtils.parsePageToIndex(pageStr);
        if (index == -1) return Mono.just("redirect:/subject/" + subjectName + "?p=" + 1);
        var subjectsReactiveData = new ReactiveDataDriverContextVariable(subjectService.findAll(), 1);
        model.addAttribute("subjects", subjectsReactiveData);
        return subjectService.findByNameDeep(subjectName)
                .map(subjectDeep -> {
                    model.addAttribute("subject", subjectDeep);
                    var postsInfoReactiveData = new ReactiveDataDriverContextVariable(
                            postService.findAllBySubjectIdInfo(subjectDeep.getId(), PageRequest.of(
                                    index, POST_PAGE_SIZE, Sort.by("updated_at").descending())),
                            1
                    );
                    model.addAttribute("posts",postsInfoReactiveData);
                    return "subject";
                });
    }

}
