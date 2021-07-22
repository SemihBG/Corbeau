package com.semihbkgr.corbeau.controller;


import com.semihbkgr.corbeau.model.Role;
import com.semihbkgr.corbeau.model.Subject;
import com.semihbkgr.corbeau.model.projection.PostInfo;
import com.semihbkgr.corbeau.model.projection.PostShallow;
import com.semihbkgr.corbeau.model.projection.SubjectDeep;
import com.semihbkgr.corbeau.service.PostService;
import com.semihbkgr.corbeau.service.RoleService;
import com.semihbkgr.corbeau.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@Profile("dev")
@RequiredArgsConstructor
public class ApiController {

    static final int POST_PAGE_SIZE = 10;

    private final RoleService roleService;
    private final SubjectService subjectService;
    private final PostService postService;

    @GetMapping("/role")
    public Flux<Role> roles() {
        return roleService.findAll();
    }


    @GetMapping("/subject")
    public Flux<Subject> subjects() {
        return subjectService.findAll();
    }

    @GetMapping("/subject-deep")
    public Flux<SubjectDeep> subjectsDeep() {
        return subjectService.findAllDeep();
    }

    @GetMapping("/subject-deep/{name}")
    public Mono<SubjectDeep> subjectDeep(@PathVariable("name") String name){
        return subjectService.findByNameDeep(name);
    }

    @GetMapping("/post")
    public Flux<PostShallow> posts(@RequestParam(value = "p", required = false, defaultValue = "-1") int page) {
        return postService.findAllShallow(PageRequest.of(Math.max(page, 0), POST_PAGE_SIZE, Sort.by("updated_at").descending()));
    }

    @GetMapping("/post/{subject_id}")
    public Flux<PostInfo> postsInfo(@PathVariable("subject_id") int id, @RequestParam(value = "p", required = false, defaultValue = "-1") int page) {
        return postService.findAllBySubjectIdInfo(id, PageRequest.of(Math.max(page, 0), POST_PAGE_SIZE, Sort.by("updated_at").descending()));
    }


}

