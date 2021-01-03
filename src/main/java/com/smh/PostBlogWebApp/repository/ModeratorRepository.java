package com.smh.PostBlogWebApp.repository;

import com.smh.PostBlogWebApp.entity.Moderator;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ModeratorRepository extends CrudRepository<Moderator,Integer> {

    Optional<Moderator> findByUsername(String username);

}
