package com.semihbkgr.corbeau.component;

import com.semihbkgr.corbeau.service.ModeratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
@Slf4j
public class DataPrimeModeratorStartupInterceptor implements CommandLineRunner {

    public static final String DEFAULT_PRIME_MODERATOR_NAME = "name";
    public static final String DEFAULT_PRIME_MODERATOR_PASSWORD = "password";
    public static final String DEFAULT_PRIME_MODERATOR_EMAIL = "moderator@gmail.com";

    private final boolean insertPrimeModerator;
    private final String primeModeratorName;
    private final String primeModeratorPassword;
    private final String privateModeratorEmail;

    private final ModeratorService moderatorService;

    public DataPrimeModeratorStartupInterceptor(@Value("moderation.prime.enable:#{false}") boolean insertPrimeModerator,
                                                @Value("moderation.prime.name:#{null}") String primeModeratorName,
                                                @Value("moderation.prime.password:#{null}") String primeModeratorPassword,
                                                @Value("moderation.prime.password:#{null}") String primeModeratorEmail,
                                                ModeratorService moderatorService) {
        this.insertPrimeModerator = insertPrimeModerator;
        this.primeModeratorName = primeModeratorName;
        this.primeModeratorPassword = primeModeratorPassword;
        this.privateModeratorEmail = primeModeratorEmail;
        this.moderatorService=moderatorService;
    }

    @Override
    public void run(String... args) throws Exception {

    }

}
