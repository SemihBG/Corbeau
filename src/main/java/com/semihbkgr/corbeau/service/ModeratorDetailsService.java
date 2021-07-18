package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.model.Moderator;
import com.semihbkgr.corbeau.model.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ModeratorDetailsService implements ReactiveUserDetailsService {

    private final ModeratorService moderatorService;
    private final RoleService roleService;

    @Override
    public Mono<UserDetails> findByUsername(String s) {
        return moderatorService.findByName(s)
                .switchIfEmpty(Mono.error(() ->
                        new UsernameNotFoundException("User not found by given username")))
                .map(this::wrapModerator)
                .flatMap(moderatorDetails ->
                        roleService.findById(moderatorDetails.getRoleId())
                                .flatMap(role -> {
                                    moderatorDetails.setRole(role);
                                    moderatorDetails.grantedAuthorities = List.of(new SimpleGrantedAuthority(role.getName()));
                                    return Mono.just(moderatorDetails);
                                })
                );
    }

    private ModeratorDetails wrapModerator(@NonNull Moderator moderator) {
        return ModeratorDetails.builder()
                .id(moderator.getId())
                .name(moderator.getName())
                .password(moderator.getPassword())
                .email(moderator.getEmail())
                .roleId(moderator.getRoleId())
                .build();
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class ModeratorDetails implements UserDetails {

        private int id;
        private String name;
        private String password;
        private String email;
        private int roleId;
        private Role role;
        private List<? extends GrantedAuthority> grantedAuthorities;

        public ModeratorDetails() {
            this.grantedAuthorities = Collections.emptyList();
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return this.grantedAuthorities;
        }

        @Override
        public String getPassword() {
            return this.password;
        }

        @Override
        public String getUsername() {
            return this.getUsername();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }

    }

}
