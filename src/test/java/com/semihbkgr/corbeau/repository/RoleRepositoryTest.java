package com.semihbkgr.corbeau.repository;

import com.semihbkgr.corbeau.model.Role;
import com.semihbkgr.corbeau.test.DataSourceExtension;
import com.semihbkgr.corbeau.test.ModelUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import reactor.test.StepVerifier;

import static com.semihbkgr.corbeau.test.ModelUtils.defaultSaveRole;

@DataR2dbcTest
@DisplayName("Role Repository Test")
@ExtendWith(DataSourceExtension.class)
@Slf4j
class RoleRepositoryTest {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    R2dbcEntityTemplate template;

    @Test
    @DisplayName("findByName returns empty mono")
    void findByNameReturnsEmptyFlux() {
        var roleMono = roleRepository.findByName("name")
                .log();
        StepVerifier.create(roleMono)
                .expectSubscription()
                .verifyComplete();
    }

    @Test
    @DisplayName("save role returns saved role mono")
    void saveRoleReturnsSavedRole() {
        var role= defaultSaveRole();
        var roleMono = roleRepository.save(role)
                .log();
        StepVerifier.create(roleMono)
                .expectSubscription()
                .expectNextMatches(savedRole ->
                        savedRole.getName().equals(role.getName()))
                .verifyComplete();
    }

    @Test
    @DisplayName("save role and findByName returns saved role mono")
    void saveRoleAndFindByNameRetrunsSavedRoleMono() {
        var role = defaultSaveRole();
        var roleMono = roleRepository.save(role)
                .then(roleRepository.findByName(role.getName()))
                .log();
        StepVerifier.create(roleMono)
                .expectSubscription()
                .expectNextMatches(savedRole ->
                        savedRole.getName().equals(role.getName()))
                .verifyComplete();
    }

}
