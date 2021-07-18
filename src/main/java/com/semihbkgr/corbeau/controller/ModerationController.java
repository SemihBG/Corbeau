package com.semihbkgr.corbeau.controller;

import com.semihbkgr.corbeau.service.ModeratorDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/moderation")
public class ModerationController {

    @GetMapping({"", "/", "/panel"})
    public Mono<String> panel(final Model model) {
        return Mono.from(ReactiveSecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(ModeratorDetailsService.ModeratorDetails.class::cast)
                .map(moderatorDetails -> {
                    model.addAttribute("name", moderatorDetails.getName());
                    model.addAttribute("role", moderatorDetails.getRole());
                    return moderatorDetails;
                })
                .then(Mono.just("panel"));
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }


}
