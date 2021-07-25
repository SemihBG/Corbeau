package com.semihbkgr.corbeau.controller;


import com.semihbkgr.corbeau.model.Comment;
import com.semihbkgr.corbeau.model.Image;
import com.semihbkgr.corbeau.service.CommentService;
import com.semihbkgr.corbeau.service.ImageContentService;
import com.semihbkgr.corbeau.service.ImageService;
import com.semihbkgr.corbeau.util.ParameterUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    static final int COMMENT_COUNT = 5;

    private final CommentService commentService;
    private final ImageService imageService;
    private final ImageContentService imageContentService;

    @GetMapping("/comment/{post_id}")
    public Flux<Comment> comment(@PathVariable("post_id") int postId,
                                 @RequestParam(value = "p", required = false, defaultValue = "1") String pageStr) {
        int index = Math.max(ParameterUtils.parsePageToIndex(pageStr), 0);
        return commentService.findByPostId(postId, PageRequest.of(index, COMMENT_COUNT, Sort.by("createdAt").descending()));
    }

    @PostMapping("/comment")
    public Mono<Comment> commentProcess(@ModelAttribute Comment comment) {
        return commentService.save(comment);
    }

    @GetMapping("/image/{full-name}")
    public Mono<Image> image(@PathVariable("full-name") String fullName) {
        return imageService.findByFullName(fullName);
    }

    @GetMapping(value = "/image/content/{full-name}", produces = MediaType.IMAGE_JPEG_VALUE)
    public Flux<DataBuffer> imageContent(@PathVariable("full-name") String fullName) {
        return imageContentService.findByName(fullName);
    }

}

