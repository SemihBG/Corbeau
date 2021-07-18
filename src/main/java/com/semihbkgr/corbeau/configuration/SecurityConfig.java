package com.semihbkgr.corbeau.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.web.server.WebSession;

import java.net.URI;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange()
                .pathMatchers("/moderation/login").permitAll()
                .pathMatchers("/moderation/**").authenticated()
                .anyExchange().permitAll()
                .and()
                .formLogin().loginPage("/moderation/login")
                .authenticationSuccessHandler(serverAuthenticationSuccessHandler())
                .and()
                .logout().logoutUrl("/moderation/logout")
                .logoutSuccessHandler(serverLogoutSuccessHandler())
                .and()
                .csrf().disable()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ServerAuthenticationSuccessHandler serverAuthenticationSuccessHandler() {
        return new RedirectServerAuthenticationSuccessHandler("/moderation");
    }

    @Bean
    public ServerLogoutSuccessHandler serverLogoutSuccessHandler() {
        return (exchange, authentication) -> {
            ServerHttpResponse response = exchange.getExchange().getResponse();
            response.setStatusCode(HttpStatus.FOUND);
            response.getHeaders().setLocation(URI.create("/moderation/login"));
            response.getCookies().remove("JSESSIONID");
            return exchange.getExchange().getSession().flatMap(WebSession::invalidate);
        };
    }

}
