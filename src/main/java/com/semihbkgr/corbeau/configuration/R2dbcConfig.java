package com.semihbkgr.corbeau.configuration;

import com.semihbkgr.corbeau.service.ModeratorDetailsService;
import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

@Configuration
@EnableR2dbcRepositories(basePackages = "com.semihbkgr.corbeau.model")
@EnableR2dbcAuditing
@Slf4j
public class R2dbcConfig {

    private final Optional<String> populatorSqlFileNameOptional;
    private final Optional<String> cleanerSqlFileNameOptional;

    public R2dbcConfig(@Value("${data.populator:#{null}}") String populatorSqlFile,
                       @Value("${data.cleaner:#{null}}") String cleanerSqlFile) {
        this.populatorSqlFileNameOptional = Optional.ofNullable(populatorSqlFile);
        this.cleanerSqlFileNameOptional = Optional.ofNullable(cleanerSqlFile);
    }

    @Bean
    @Profile("dev")
    public ConnectionFactoryInitializer connectionFactoryInitializer(ConnectionFactory connectionFactory) {
        var cfInitializer = new ConnectionFactoryInitializer();
        cfInitializer.setConnectionFactory(connectionFactory);
        if (populatorSqlFileNameOptional.isPresent()) {
            log.info("ConnectionFactoryInitializer populator file: {}", populatorSqlFileNameOptional.get());
            var resource = new ResourceDatabasePopulator(new ClassPathResource(populatorSqlFileNameOptional.get()));
            cfInitializer.setDatabasePopulator(resource);
        } else log.info("ConnectionFactoryInitializer no populator file");
        if (cleanerSqlFileNameOptional.isPresent()) {
            log.info("ConnectionFactoryInitializer cleaner file: {}", cleanerSqlFileNameOptional.get());
            var resource = new ResourceDatabasePopulator(new ClassPathResource(cleanerSqlFileNameOptional.get()));
            cfInitializer.setDatabaseCleaner(resource);
        } else log.info("ConnectionFactoryInitializer no cleaner file");
        return cfInitializer;
    }

    @Bean
    public ReactiveAuditorAware<String> auditorAware(){
        return () -> ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(User.class::cast)
                .map(User::getUsername);
    }


}
