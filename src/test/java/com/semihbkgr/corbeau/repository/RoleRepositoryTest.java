package com.semihbkgr.corbeau.repository;

import com.semihbkgr.corbeau.model.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import reactor.test.StepVerifier;

@DataR2dbcTest
@DisplayName("Role Repository Test")
@Slf4j
class RoleRepositoryTest {

    static final String SQL_ROLE_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS roles\n" +
                    "(\n" +
                    "    id   INT PRIMARY KEY AUTO_INCREMENT,\n" +
                    "    name VARCHAR(32) NOT NULL UNIQUE\n" +
                    ")";

    static final String SQL_ROLE_TABLE_DROP =
            "DROP TABLE IF EXISTS roles";

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    R2dbcEntityTemplate template;


    @BeforeEach
    void createRoleTable() {
        template.getDatabaseClient()
                .sql(SQL_ROLE_TABLE_CREATE)
                .fetch()
                .all()
                .blockFirst();
        log.info("{} table created", RoleRepository.TABLE_NAME);
    }

    @AfterEach
    void dropRoleTable() {
        template.getDatabaseClient()
                .sql(SQL_ROLE_TABLE_DROP)
                .fetch()
                .all()
                .blockFirst();
        log.info("{} table dropped", RoleRepository.TABLE_NAME);
    }

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
    @DisplayName("save returns saved role mono")
    void saveReturnsSavedRole() {
        var role = Role.builder()
                .name("test_role")
                .build();
        var roleMono = roleRepository.save(role)
                .log();
        StepVerifier.create(roleMono)
                .expectSubscription()
                .expectNextMatches(savedRole ->
                        savedRole.getName().equals(role.getName()))
                .verifyComplete();
    }

    @Test
    @DisplayName("save and findByName returns saved role mono")
    void saveAndFindByNameRetrunsSavedRoleMono() {
        var role = Role.builder()
                .name("test_role")
                .build();
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
