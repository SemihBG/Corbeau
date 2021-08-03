package com.semihbkgr.corbeau.repository;

import com.semihbkgr.corbeau.model.Role;
import com.semihbkgr.corbeau.test.DataSourceExtension;
import com.semihbkgr.corbeau.test.ModelUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
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
    @DisplayName("save(Role) Role.id = 0, return Mono<Role> saved Role")
    void saveRole_RoleIdIs0_ReturnsSavedRoleMono() {
        var role= defaultSaveRole();
        var roleMono = roleRepository.save(role)
                .log();
        StepVerifier.create(roleMono)
                .expectSubscription()
                .expectNextMatches(savedRole->
                        savedRole.getName().equals(role.getName())
                                &&
                        savedRole.getId()>0
                )
                .verifyComplete();
    }

    @Test
    @DisplayName("save(Role) Role.id != 0 , return Mono<Role> saved Role")
    void saveRole_RoleIdIsNot0_ReturnsSavedRoleMono() {
        var role= Role.builder()
                .id(1)
                .name("role-name")
                .build();
        var roleMono = roleRepository.save(role)
                .log();
        StepVerifier.create(roleMono)
                .expectSubscription()
                .expectNextMatches(savedRole->
                        savedRole.getName().equals(role.getName())
                                &&
                                savedRole.getId()>0
                )
                .verifyComplete();
    }

    @Test
    @DisplayName("findByName(Role.name) Role.name = any , return Mono<Role> empty mono")
    void findByNameRoleNAmeIsAnyReturnsEmptyMono() {
        var roleMono = roleRepository.findByName("any")
                .log();
        StepVerifier.create(roleMono)
                .expectSubscription()
                .verifyComplete();
    }

    @Test
    @DisplayName("findByName(Role.name) Role.name = null , throw NullPointerExcetion")
    void findByNameReturnsEmptyFlux() {
        Assertions.assertThrows(NullPointerException.class,()->{
            var roleMono = roleRepository.findByName(null);
        });
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
