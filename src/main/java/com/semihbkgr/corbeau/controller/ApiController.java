package com.semihbkgr.corbeau.controller;


import com.semihbkgr.corbeau.component.NameSurnameOfferComponent;
import com.semihbkgr.corbeau.component.RandomImageGenerator;
import com.semihbkgr.corbeau.error.ArtifactException;
import com.semihbkgr.corbeau.error.response.FiledErrorResponse;
import com.semihbkgr.corbeau.error.response.WebExcahngeBindExceptionResponse;
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
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.client.HttpClientErrorException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    static final int COMMENT_COUNT = 5;

    private final CommentService commentService;
    private final ImageService imageService;
    private final ImageContentService imageContentService;
    private final RandomImageGenerator randomImageGenerator;
    private final NameSurnameOfferComponent nameSurnameOfferComponent;

    @GetMapping("/comment/{post_id}")
    public Flux<Comment> comment(@PathVariable("post_id") int postId,
                                 @RequestParam(value = "p",required = false,defaultValue = "1") String pageStr) {
        var index = ParameterUtils.parsePageToIndex(pageStr);
        if (index == -1) index=1;
        return commentService.findByPostId(postId, PageRequest.of(index,5, Sort.by("updated_at").descending()));
    }

    @PostMapping("/comment")
    public Mono<Comment> commentProcess(@Valid @ModelAttribute Comment comment) {
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
    public Flux<Pair<String, String>> nameSurnameOffer() {
        return Flux.fromIterable(nameSurnameOfferComponent.getNameSurnamePairList());
    }

    @GetMapping(value = "/image/random/{seed}", produces = MediaType.IMAGE_PNG_VALUE)
    public Mono<DataBuffer> imageRandom(@PathVariable("seed") String seed,
                                        @RequestParam(value = "s", required = false) String scale) {
        var scaleBy = ParameterUtils.parseToIntMinBy(scale, 1);
        if (scaleBy < 1) return randomImageGenerator.generate(seed);
        else return randomImageGenerator.generate(seed, scaleBy);
    }

    @GetMapping("/error")
    public Mono<Void> error(@RequestParam(value = "status",required = false,defaultValue = "500") int statusCode,
                            @RequestParam(value = "message",required = false,defaultValue = "No message") String message){
        return Mono.error(new ArtifactException(HttpStatus.resolve(statusCode),message));
    }

    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<WebExcahngeBindExceptionResponse> handleWebExchangeBindExcepiton(WebExchangeBindException e){
        return Mono.just(
                WebExcahngeBindExceptionResponse.builder()
                        .httpStatusCode(HttpStatus.BAD_REQUEST.value())
                        .httpStats("BAD REQUEST")
                        .fieldErrors(e.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(fieldError -> FiledErrorResponse.builder()
                                        .fieldName(fieldError.getField())
                                        .rejectedValue(fieldError.getRejectedValue())
                                        .message(fieldError.getDefaultMessage())
                                        .build())
                                .collect(Collectors.toList()))
                        .build()
        );
    }

}

