package com.semihbkgr.corbeau.repository;

import com.semihbkgr.corbeau.model.Image;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ImageRepository extends R2dbcRepository<Image, Integer> {

    String TABLE_NAME = "images";

    Flux<Image> findAllBy(Pageable pageable);

    Mono<Image> findOneByNameAndExtension(String name, String extension);

}
