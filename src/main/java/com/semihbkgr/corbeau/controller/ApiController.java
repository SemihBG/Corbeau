package com.semihbkgr.corbeau.controller;


import com.semihbkgr.corbeau.component.NameSurnameOfferComponent;
import com.semihbkgr.corbeau.model.Comment;
import com.semihbkgr.corbeau.model.Image;
import com.semihbkgr.corbeau.service.CommentService;
import com.semihbkgr.corbeau.service.ImageContentService;
import com.semihbkgr.corbeau.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
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
    private final NameSurnameOfferComponent nameSurnameOfferComponent;

    @GetMapping("/comment/{post_id}")
    public Flux<Comment> comment(@PathVariable("post_id") int postId) {
        return commentService.findByPostId(postId);
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

    @GetMapping("/offer")
    public Flux<Pair<String,String>> nameSurnameOffer(){
        return Flux.fromIterable(nameSurnameOfferComponent.getNameSurnamePairList());
    }

}

