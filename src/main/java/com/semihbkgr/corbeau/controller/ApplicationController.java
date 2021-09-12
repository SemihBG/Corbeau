package com.semihbkgr.corbeau.controller;

import com.semihbkgr.corbeau.component.AppStatus;
import com.semihbkgr.corbeau.component.NameSurnameOfferComponent;
import com.semihbkgr.corbeau.model.Post;
import com.semihbkgr.corbeau.model.projection.combination.PostDeepTagList;
import com.semihbkgr.corbeau.model.projection.combination.PostInfoTagList;
import com.semihbkgr.corbeau.service.CommentService;
import com.semihbkgr.corbeau.service.PostService;
import com.semihbkgr.corbeau.service.SubjectService;
import com.semihbkgr.corbeau.service.TagService;
import com.semihbkgr.corbeau.util.PageUtils;
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


@Controller
@RequiredArgsConstructor
public class ApplicationController {

    static final int POST_PAGE_SIZE = 5;

    private final SubjectService subjectService;
    private final PostService postService;
    private final TagService tagService;
    private final CommentService commentService;
    private final NameSurnameOfferComponent nameSurnameOfferComponent;
    private final AppStatus appStatus;

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
                    var postsInfoTagListCombinationsReactiveData = new ReactiveDataDriverContextVariable(
                            postService.findAllActivatedBySubjectIdInfo(
                                    subjectDeep.getId(),
                                    PageRequest.of(index, POST_PAGE_SIZE, Sort.by("updated_at").descending())
                            ).flatMapSequential(postInfo ->
                                    tagService.findAllByPostId(postInfo.getId())
                                            .collectList()
                                            .map(list -> new PostInfoTagList(postInfo, list)))
                            , 1
                    );
                    model.addAttribute("postInfoTagListCombinations", postsInfoTagListCombinationsReactiveData);
                    return postService.countBySubjectIdAndActivated(subjectDeep.getId(), true);
                })
                .flatMap(count -> {
                    PageUtils.addPabeAttributedToModel(model, count, index, POST_PAGE_SIZE);
                    return subjectService.findAll().collectList();
                })
                .map(subjectList -> {
                    model.addAttribute("subjects", subjectList);
                    return "subject";
                })
                .defaultIfEmpty("redirect:/")
                .onErrorReturn("redirect:/");
    }

    @GetMapping("/tag/{tag_name}")
    public Mono<String> tag(@PathVariable("tag_name") String tagName,
                            @RequestParam(value = "p", required = false, defaultValue = "1") String pageStr,
                            Model model) {
        var index = ParameterUtils.parsePageToIndex(pageStr);
        if (index == -1) return Mono.just("redirect:/tag/" + tagName + "?p=" + 1);
        return tagService.findByNameAndPostActivatedDeep(tagName, true)
                .doOnNext(tagDeep -> {
                    model.addAttribute("tag", tagDeep);
                    PageUtils.addPabeAttributedToModel(model, tagDeep.getPostCount(), index, POST_PAGE_SIZE);
                    var postDeepTagListCombinationsReactiveData = new ReactiveDataDriverContextVariable(
                            postService.findAllByTagIdAndActivatedDeep(tagDeep.getId(), true,
                                    PageRequest.of(index, POST_PAGE_SIZE, Sort.by("updated_at").descending()))
                                    .flatMapSequential(postDeep ->
                                            tagService.findAllByPostId(postDeep.getId())
                                                    .collectList()
                                                    .map(list -> new PostDeepTagList(postDeep, list)))
                            , 1);
                    model.addAttribute("postDeepTagListCombinations", postDeepTagListCombinationsReactiveData);
                })
                .thenMany(subjectService.findAll())
                .collectList()
                .map(subjectList -> {
                    model.addAttribute("subjects", subjectList);
                    return "tag";
                })
                .defaultIfEmpty("redirect:/")
                .onErrorReturn("redirect:/");
    }

    @GetMapping("/post/{endpoint}")
    public Mono<String> post(@PathVariable("endpoint") String endpoint, final Model model) {
        var subjectsReactiveData = new ReactiveDataDriverContextVariable(subjectService.findAll(), 1);
        model.addAttribute("subjects", subjectsReactiveData);
        return postService.findByEndpoint(endpoint)
                .filter(Post::isActivated)
                .flatMap(post -> {
                    model.addAttribute("post", post);
                    return tagService.findAllByPostId(post.getId())
                            .collectList()
                            .flatMap(tagList -> {
                                model.addAttribute("tags", tagList);
                                return subjectService.findById(post.getSubjectId());
                            })
                            .flatMap(subject -> {
                                model.addAttribute("subject", subject);
                                return commentService.countByPostId(post.getId());
                            });
                })
                .flatMap(commentCount -> {
                    model.addAttribute("commentCount", commentCount);
                    return nameSurnameOfferComponent.offer();
                })
                .map(pair -> {
                    model.addAttribute("offerName", pair.getFirst());
                    model.addAttribute("offerSurname", pair.getSecond());
                    model.addAttribute("appStatus", appStatus);
                    return "post";
                })
                .defaultIfEmpty("redirect:/")
                .onErrorReturn("redirect:/");
    }

    @GetMapping("/search")
    public Mono<String> search(@RequestParam(value = "s", required = false) String s,
                               Model model) {
        if (s == null) return Mono.just("redirect: /");
        var postDeepTagListCombinationReactiveData = new ReactiveDataDriverContextVariable(
                postService.searchByTitleAndActivatedDeep(s, true)
                        .flatMapSequential(postDeep ->
                                tagService.findAllByPostId(postDeep.getId())
                                        .collectList()
                                        .map(list -> new PostDeepTagList(postDeep, list))
                        )
                , 1);
        model.addAttribute("postDeepTagListCombinations", postDeepTagListCombinationReactiveData);
        return subjectService.findAll()
                .collectList()
                .map(subjectList -> {
                    model.addAttribute("subjects", subjectList);
                    return "search";
                });
    }

}
