package com.semihbkgr.corbeau.controller;


import com.semihbkgr.corbeau.model.Role;
import com.semihbkgr.corbeau.model.projection.PostShallow;
import com.semihbkgr.corbeau.model.projection.SubjectDeep;
import com.semihbkgr.corbeau.service.PostService;
import com.semihbkgr.corbeau.service.RoleService;
import com.semihbkgr.corbeau.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
@Profile("dev")
@RequiredArgsConstructor
public class ApiController {

    private final RoleService roleService;
    private final SubjectService subjectService;
    private final PostService postService;

    @GetMapping("/role")
    public Flux<Role> roles(){
        return roleService.findAll();
    }

    @GetMapping("/subject")
    public Flux<SubjectDeep> subjects(){
        return subjectService.findAll();
    }

    @GetMapping("/post")
    public Flux<PostShallow> posts(@RequestParam(value="p",required = false, defaultValue = "-1") int page) {
        if(page<1) page=0;
        return postService.findAll(PageRequest.of(page,10, Sort.by("updated_at")));
    }
}

