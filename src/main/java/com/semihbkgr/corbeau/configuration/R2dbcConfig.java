package com.semihbkgr.corbeau.configuration;

import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

import java.util.Optional;

@Configuration
@EnableR2dbcRepositories(basePackages = "com.semihbkgr.corbeau.model")
@EnableR2dbcAuditing
@Slf4j
public class R2dbcConfig {

    private final Optional<String> populateSqlFileNameOptional;
    private final Optional<String> clearSqlFileNameOptional;

    public R2dbcConfig(@Value("${data.populate:#{null}}") String populateSqlFile,
                       @Value("${data.clear:#{null}}") String clearSqlFile) {
        this.populateSqlFileNameOptional = Optional.ofNullable(populateSqlFile);
        this.clearSqlFileNameOptional = Optional.ofNullable(clearSqlFile);
    }

    @Bean
    @Profile("dev")
    public ConnectionFactoryInitializer connectionFactoryInitialize(ConnectionFactory connectionFactory) {
        var cfInitializer = new ConnectionFactoryInitializer();
        cfInitializer.setConnectionFactory(connectionFactory);
        if (populateSqlFileNameOptional.isPresent()) {
            log.info("ConnectionFactoryInitializer populate file: {}", populateSqlFileNameOptional.get());
            var resource = new ResourceDatabasePopulator(new ClassPathResource(populateSqlFileNameOptional.get()));
            cfInitializer.setDatabasePopulator(resource);
        } else log.info("ConnectionFactoryInitializer no populate file");
        if (clearSqlFileNameOptional.isPresent()) {
            log.info("ConnectionFactoryInitializer clear file: {}", clearSqlFileNameOptional.get());
            var resource = new ResourceDatabasePopulator(new ClassPathResource(clearSqlFileNameOptional.get()));
            cfInitializer.setDatabaseCleaner(resource);
        } else log.info("ConnectionFactoryInitializer no clear file");
        return cfInitializer;
    }

}
