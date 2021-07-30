package com.semihbkgr.corbeau.repository;

import com.semihbkgr.corbeau.model.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.test.context.jdbc.Sql;
import reactor.test.StepVerifier;

import javax.swing.*;

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
    void createRoleTable(){
        template.getDatabaseClient()
                .sql(SQL_ROLE_TABLE_CREATE)
                .fetch()
                .all()
                .blockFirst();
        log.info("{} table created",RoleRepository.TABLE_NAME);
    }

    @AfterEach
    void dropRoleTable(){
        template.getDatabaseClient()
                .sql(SQL_ROLE_TABLE_DROP)
                .fetch()
                .all()
                .blockFirst();
        log.info("{} table dropped",RoleRepository.TABLE_NAME);
    }

    @Test
    @DisplayName("findByName returns empty mono")
    void findByNameReturnEmptyFlux() {
        var roleMono = roleRepository.findByName("name")
                .log();
        StepVerifier.create(roleMono)
                .expectSubscription()
                .verifyComplete();
    }

    @Test
    @DisplayName("save returns role mono")
    void saveRole(){
        var role=Role.builder()
                .id(0)
                .name("test_role")
                .build();
        var roleMono=roleRepository.save(role)
                .log();
        StepVerifier.create(roleMono)
                .expectSubscription()
                .expectNext(role.withId(1))
                .verifyComplete();
    }


}