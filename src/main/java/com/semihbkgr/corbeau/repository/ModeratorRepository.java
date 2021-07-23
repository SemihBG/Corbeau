package com.semihbkgr.corbeau.repository;

import com.semihbkgr.corbeau.model.Moderator;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface ModeratorRepository extends R2dbcRepository<Moderator, Integer> {

    Mono<Moderator> findByName(String name);

}
