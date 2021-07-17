package com.semihbkgr.corbeau.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
@Profile("production")
@EnableWebSecurity
public class WebSecurityProductionConfiguration extends WebSecurityConfigurerAdapter {

    private final String username;
    private final String password;

    @Autowired
    public WebSecurityProductionConfiguration(@Value("${security.username}") String username,
                                              @Value("${security.password}") String password) {
        this.username = username;
        this.password = password;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser(username)
                .password(passwordEncoder()
                        .encode(password)).roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .httpBasic()
                .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
            .authorizeRequests()
                .antMatchers("/authorized/**","/api/**").authenticated()
                .anyRequest().permitAll()
                .and()
            .exceptionHandling().accessDeniedHandler(new SimpleAccessDeniedHandler());
    }

}
*/