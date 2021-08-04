package com.semihbkgr.corbeau.repository;

import com.semihbkgr.corbeau.model.Role;
import com.semihbkgr.corbeau.test.DataSourceExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.dao.TransientDataAccessResourceException;
import reactor.test.StepVerifier;

import static com.semihbkgr.corbeau.test.ModelUtils.defaultSaveRole;

@DataR2dbcTest
@DisplayName("Role Repository Test")
@ExtendWith(DataSourceExtension.class)
class RoleRepositoryTest {

    @Autowired
    RoleRepository roleRepository;

    @Test
    @DisplayName("save(Role) Role.id = 0, return Mono<Role> saved Role")
    void saveRole_RoleIdIs0_ReturnsSavedRoleMono() {
        var role = defaultSaveRole();
        var roleMono = roleRepository.save(role)
                .log();
        StepVerifier.create(roleMono)
                .expectSubscription()
                .expectNextMatches(savedRole ->
                        savedRole.getName().equals(role.getName())
                                &&
                                savedRole.getId() > 0
                )
                .verifyComplete();
    }

    @Test
    @DisplayName("save(Role) Role.id != 0 , return Mono<Role> error Mono")
    void saveRole_RoleIdIsNot0_ReturnsErrorMono() {
        var role = Role.builder()
                .id(1)
                .name("role-name")
                .build();
        var roleMono = roleRepository.save(role)
                .log();
        StepVerifier.create(roleMono)
                .expectSubscription()
                .expectError(TransientDataAccessResourceException.class)
                .verify();
    }

    @Test
    @DisplayName("findByName(Role.name) Role.name = any , return Mono<Role> empty Mono")
    void findByNameRoleNameIsAnyReturnsEmptyMono() {
        var roleMono = roleRepository.findByName("any")
                .log();
        StepVerifier.create(roleMono)
                .expectSubscription()
                .verifyComplete();
    }

    @Test
    @DisplayName("save(Role) and findByName(Role.name), return Mono<Role> saved Role Mono")
    void saveRoleAndFindByNameRetrunsSavedRoleMono() {
        var role = defaultSaveRole();
        var roleMono = roleRepository.save(role)
                .then(roleRepository.findByName(role.getName()))
                .log();
        StepVerifier.create(roleMono)
                .expectSubscription()
                .expectNextMatches(savedRole ->
                        savedRole.getName().equals(role.getName())
                                &&
                                savedRole.getId() > 0
                )
                .verifyComplete();
    }

}
