package com.semihbkgr.corbeau.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    @Profile("dev")
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                    .authorizeExchange()
                        .pathMatchers("/admin/login").permitAll()
                        .pathMatchers("/admin/**").authenticated()
                        .anyExchange().permitAll()
                .and()
                    .formLogin()
                        .loginPage("/admin/login")
                .and()
                    .csrf()
                        .disable()
                .build();
    }

}
