package com.semihbkgr.corbeau.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ModeratorDetailsService implements ReactiveUserDetailsService {

    private final ModeratorService moderatorService;

    @Override
    public Mono<UserDetails> findByUsername(String s) {
        return moderatorService.findByName(s)
                .switchIfEmpty(Mono.error(()->new UsernameNotFoundException("No user found by given name"))
                .map();
    }

}
