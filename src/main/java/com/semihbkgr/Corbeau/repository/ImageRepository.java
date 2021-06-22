package com.semihbkgr.Corbeau.repository;

import com.semihbkgr.Corbeau.entity.Image;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<Image,Integer> {
    Image findByUrlEndpoint(String urlEndpoint);
    void deleteByUrlEndpoint(String urlEndpoint);
}
