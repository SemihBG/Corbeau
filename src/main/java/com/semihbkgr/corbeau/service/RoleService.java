package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.error.IllegalValueException;
import com.semihbkgr.corbeau.model.Role;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RoleService {

    Flux<Role> saveAll(Iterable<Role> roleIterable);

    Mono<Role> findById(int id) throws IllegalValueException;

    Mono<Role> findByName(String name);

    Flux<Role> findAll();

}
