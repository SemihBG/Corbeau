package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.model.Moderator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ModeratorService {

    Mono<Moderator> save(Moderator moderator);

    Flux<Moderator> findAll();

    Mono<Moderator> findByName(String name);

    Mono<Moderator> addRole(int id,int roleId);

}
