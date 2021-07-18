package com.semihbkgr.corbeau.repository;

import com.semihbkgr.corbeau.model.Role;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface RoleRepository extends R2dbcRepository<Role,Integer> {

    String TABLE_NAME="roles";

    Mono<Role> findByName(String name);

}
