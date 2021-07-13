package com.semihbkgr.corbeau.repository;

import com.semihbkgr.corbeau.model.Image;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<Image,Integer> {
    Image findByUrlEndpoint(String urlEndpoint);
    void deleteByUrlEndpoint(String urlEndpoint);
}
