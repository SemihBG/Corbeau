package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.model.Moderator;
import com.semihbkgr.corbeau.model.Role;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ModeratorDetailsService implements ReactiveUserDetailsService {

    private final ModeratorService moderatorService;

    @Override
    public Mono<UserDetails> findByUsername(String s) {
        return moderatorService.findByName(s)
                .switchIfEmpty(Mono.error(() ->
                        new UsernameNotFoundException("User not found by given username")))
                .map(this::wrapModeratorByUserDetails);
    }

    private UserDetails wrapModeratorByUserDetails(@NonNull Moderator moderator) {
        return User.builder()
                .username(moderator.getName())
                .password(moderator.getPassword())
                .authorities(wrapRoleListToGrantedAuthorityList(moderator.getRoles()))
                .build();
    }

    private List<GrantedAuthority> wrapRoleListToGrantedAuthorityList(List<? extends Role> roleList) {
        if(roleList==null) return Collections.emptyList();
        return roleList.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

}
