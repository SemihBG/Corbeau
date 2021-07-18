package com.semihbkgr.corbeau.repository;

import com.semihbkgr.corbeau.model.Moderator;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Mono;

public interface ModeratorRepository extends R2dbcRepository<Moderator,Integer> {

    Mono<Moderator> findByName(String name);

    @Query("INSERT INTO db.moderator_role_join (moderator_id,role_id) VALUE (:moderator_id,:role_id)")
    Mono<Void> addRole(@Param("moderator_id") int id, @Param("role_id") int roleId);

}
