package com.semihbkgr.corbeau.component;

import com.semihbkgr.corbeau.model.Moderator;
import com.semihbkgr.corbeau.service.ModeratorService;
import com.semihbkgr.corbeau.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
@Slf4j
public class DataPrimeModeratorStartupInterceptor implements CommandLineRunner {

    public static final String DEFAULT_PRIME_MODERATOR_NAME = "name";
    public static final String DEFAULT_PRIME_MODERATOR_PASSWORD = "password";
    public static final String DEFAULT_PRIME_MODERATOR_EMAIL = "moderator@gmail.com";
    public static final String DEFAULT_PRIME_MODERATOR_ROLE = "prime";

    private final boolean savePrimeModerator;
    private final String primeModeratorName;
    private final String primeModeratorPassword;
    private final String primeModeratorEmail;
    private final String primeModeratorRole;

    private final ModeratorService moderatorService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataPrimeModeratorStartupInterceptor(@Value("${moderation.prime.enable:#{false}}") boolean savePrimeModerator,
                                                @Value("${moderation.prime.name:#{null}}") String primeModeratorName,
                                                @Value("${moderation.prime.password:#{null}}") String primeModeratorPassword,
                                                @Value("${moderation.prime.email:#{null}}") String primeModeratorEmail,
                                                @Value("${moderation.prime.role:#{null}}") String primeModeratorRole,
                                                ModeratorService moderatorService, RoleService roleService,
                                                PasswordEncoder passwordEncoder) {
        this.savePrimeModerator = savePrimeModerator;
        this.primeModeratorName = primeModeratorName;
        this.primeModeratorPassword = primeModeratorPassword;
        this.primeModeratorEmail = primeModeratorEmail;
        this.primeModeratorRole = primeModeratorRole;
        this.moderatorService = moderatorService;
        this.roleService = roleService;
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
                    .build();
            moderatorService
                    .save(primeModerator)
                    .flatMap(moderator -> roleService
                            .findByName(primeModeratorRole != null ? primeModeratorRole : DEFAULT_PRIME_MODERATOR_ROLE)
                            .flatMap(role -> moderatorService.addRole(moderator.getId(), role.getId()))
                    )
                    .subscribe(moderator ->
                            log.info("Prime moderator has been saved successfully, name: {}, password: {}, email: {}, role: {}",
                                    primeModeratorName != null ? primeModeratorName : DEFAULT_PRIME_MODERATOR_NAME,
                                    primeModeratorPassword != null ? primeModeratorPassword : DEFAULT_PRIME_MODERATOR_PASSWORD,
                                    primeModeratorEmail != null ? primeModeratorEmail : DEFAULT_PRIME_MODERATOR_EMAIL,
                                    primeModeratorRole != null ? primeModeratorRole : DEFAULT_PRIME_MODERATOR_ROLE));
        } else log.warn("Prime moderator is disabled");

    }

}
