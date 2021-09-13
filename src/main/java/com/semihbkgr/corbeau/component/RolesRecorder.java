package com.semihbkgr.corbeau.component;

import com.semihbkgr.corbeau.model.Role;
import com.semihbkgr.corbeau.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RolesRecorder implements CommandLineRunner {

    public static final String[] DEFAULT_ROLES = new String[]{"prime"};
    public static final boolean DEFAULT_ENABLED = false;

    private final String[] roles;
    private final boolean enabled;

    private final RoleService roleService;

    @Autowired
    public RolesRecorder(@Value("${moderation.roles.enabled:#{null}}") Boolean enabled,
                         @Value("${moderation.roles:#{null}}") String[] roles,
                         RoleService roleService) {
        this.enabled = enabled != null ? enabled : DEFAULT_ENABLED;
        this.roles = roles != null ? roles : DEFAULT_ROLES;
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) throws Exception {

        log.info("Moderation roles start up record: {}", enabled);
        if (enabled) {
            var roleList = Arrays.stream(roles)
                    .map(roleName -> Role.builder().name(roleName).build())
                    .collect(Collectors.toList());
            roleService.saveAll(roleList)
                    .collectList()
                    .subscribe(savedRoleList ->
                            log.info("Roles has been saved successfully, roles: {}", savedRoleList));
        }
    }

}
