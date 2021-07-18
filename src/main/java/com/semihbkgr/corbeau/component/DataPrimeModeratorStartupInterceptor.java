package com.semihbkgr.corbeau.component;

import com.semihbkgr.corbeau.model.Moderator;
import com.semihbkgr.corbeau.service.ModeratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@Profile("dev")
@Slf4j
public class DataPrimeModeratorStartupInterceptor implements CommandLineRunner {

    public static final String DEFAULT_PRIME_MODERATOR_NAME = "name";
    public static final String DEFAULT_PRIME_MODERATOR_PASSWORD = "password";
    public static final String DEFAULT_PRIME_MODERATOR_EMAIL = "moderator@gmail.com";

    private final boolean savePrimeModerator;
    private final String primeModeratorName;
    private final String primeModeratorPassword;
    private final String primeModeratorEmail;

    private final ModeratorService moderatorService;
    private final PasswordEncoder passwordEncoder;

    public DataPrimeModeratorStartupInterceptor(@Value("${moderation.prime.enable:#{false}}") boolean savePrimeModerator,
                                                @Value("${moderation.prime.name:#{null}}") String primeModeratorName,
                                                @Value("${moderation.prime.password:#{null}}") String primeModeratorPassword,
                                                @Value("${moderation.prime.email:#{null}}") String primeModeratorEmail,
                                                ModeratorService moderatorService, PasswordEncoder passwordEncoder) {
        this.savePrimeModerator = savePrimeModerator;
        this.primeModeratorName = primeModeratorName;
        this.primeModeratorPassword = primeModeratorPassword;
        this.primeModeratorEmail = primeModeratorEmail;
        this.moderatorService = moderatorService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (savePrimeModerator) {
            log.info("Prime moderator is enabled");
            var primeModerator = Moderator.builder()
                    .name(primeModeratorName != null ?
                            primeModeratorName : DEFAULT_PRIME_MODERATOR_NAME)
                    .password(passwordEncoder.encode(primeModeratorPassword != null ?
                            primeModeratorPassword : DEFAULT_PRIME_MODERATOR_PASSWORD))
                    .email(primeModeratorEmail != null ?
                            primeModeratorEmail : DEFAULT_PRIME_MODERATOR_EMAIL)
                    .roles(Collections.emptyList())
                    .build();
            moderatorService
                    .save(primeModerator)
                    .subscribe(moderator ->
                            log.info("Prime moderator has been saved successfully, name: {}, password: {}",
                                    primeModeratorName, primeModeratorPassword));
        } else log.warn("Prime moderator is disabled");

    }

}
