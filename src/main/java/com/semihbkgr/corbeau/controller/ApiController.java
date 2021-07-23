package com.semihbkgr.corbeau.controller;


import com.semihbkgr.corbeau.model.Comment;
import com.semihbkgr.corbeau.service.CommentService;
import com.semihbkgr.corbeau.util.ParameterUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    static final int COMMENT_COUNT = 5;

    private final CommentService commentService;

    @GetMapping("/comment/{post_id}")
    @ResponseStatus
    public Flux<Comment> comment(@PathVariable("post_id") int postId,
                                 @RequestParam(value = "p", required = false, defaultValue = "1") String pageStr) {
        int index = Math.max(ParameterUtils.parsePageToIndex(pageStr), 0);
        return commentService.findByPostId(postId, PageRequest.of(index, COMMENT_COUNT, Sort.by("createdAt").descending()));
    }

    @PostMapping("/comment")
    @ResponseBody
    public Mono<Comment> commentProcess(@ModelAttribute Comment comment) {
        return commentService.save(comment);
    }

}

