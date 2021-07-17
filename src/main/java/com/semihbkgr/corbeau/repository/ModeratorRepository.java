package com.semihbkgr.corbeau.repository;

import com.semihbkgr.corbeau.model.Moderator;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface ModeratorRepository extends R2dbcRepository<Moderator,Integer> {
}
