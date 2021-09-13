package com.semihbkgr.corbeau.component;

import com.semihbkgr.corbeau.model.Moderator;
import com.semihbkgr.corbeau.service.ModeratorService;
import com.semihbkgr.corbeau.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE)
public class PrimeModeratorRecorder implements CommandLineRunner {

    public static final boolean DEFAULT_PRIME_MODERATOR_ENABLE = false;
    public static final String DEFAULT_PRIME_MODERATOR_NAME = "name";
    public static final String DEFAULT_PRIME_MODERATOR_PASSWORD = "password";
    public static final String DEFAULT_PRIME_MODERATOR_EMAIL = "moderator@gmail.com";
    public static final String DEFAULT_PRIME_MODERATOR_ROLE = "prime";

    private final boolean primeModeratorEnable;
    private final String primeModeratorName;
    private final String primeModeratorPassword;
    private final String primeModeratorEmail;
    private final String primeModeratorRole;

    private final ModeratorService moderatorService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PrimeModeratorRecorder(@Value("${moderation.prime.enable:#{null}}") Boolean primeModeratorEnable,
                                  @Value("${moderation.prime.name:#{null}}") String primeModeratorName,
                                  @Value("${moderation.prime.password:#{null}}") String primeModeratorPassword,
                                  @Value("${moderation.prime.email:#{null}}") String primeModeratorEmail,
                                  @Value("${moderation.prime.role:#{null}}") String primeModeratorRole,
                                  ModeratorService moderatorService, RoleService roleService,
                                  PasswordEncoder passwordEncoder) {
        this.primeModeratorEnable = primeModeratorEnable != null ? primeModeratorEnable : DEFAULT_PRIME_MODERATOR_ENABLE;
        this.primeModeratorName = primeModeratorName != null ? primeModeratorName : DEFAULT_PRIME_MODERATOR_NAME;
        this.primeModeratorPassword = primeModeratorPassword != null ? primeModeratorPassword : DEFAULT_PRIME_MODERATOR_PASSWORD;
        this.primeModeratorEmail = primeModeratorEmail != null ? primeModeratorEmail : DEFAULT_PRIME_MODERATOR_EMAIL;
        this.primeModeratorRole = primeModeratorRole != null ? primeModeratorRole : DEFAULT_PRIME_MODERATOR_ROLE;
        this.moderatorService = moderatorService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public void run(String... args) {
        if (primeModeratorEnable) {
            log.info("Prime moderator is enabled");
            var primeModerator = Moderator.builder()
                    .name(primeModeratorName)
                    .password(passwordEncoder.encode(primeModeratorPassword))
                    .email(primeModeratorEmail)
                    .build();

            roleService.findByName(primeModeratorRole)
                    .flatMap(role -> {
                        primeModerator.setRoleId(role.getId());
                        return moderatorService.save(primeModerator);
                    })
                    .subscribe(moderator -> {
                        log.info("Prime moderator has been saved successfully, name: {}, password: {}, email: {}, role: {}",
                                primeModeratorName, primeModeratorPassword, primeModeratorEmail, primeModeratorRole);
                    });
        } else log.warn("Prime moderator is disabled");

    }

}
