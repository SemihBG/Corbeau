package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.entity.Image;
import com.semihbkgr.corbeau.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class ImageServiceImpl implements ImageService {

    private static final String CACHE_NAME="image";

    @Autowired
    private ImageRepository imageRepository;

    @Cacheable(cacheNames = CACHE_NAME)
    @Override
    public List<Image> findAll() {
        return null;
    }

    @CachePut(cacheNames = CACHE_NAME,key = "#image.urlEndpoint")
    @Override
    public Image save(Image image) {
        return imageRepository.save(Objects.requireNonNull(image,"Image cannot be null"));
    }

    @Cacheable(cacheNames = CACHE_NAME)
    @Nullable
    @Override
    public Image getByEndPoint(String urlEndpoint) {
        return imageRepository.findByUrlEndpoint(Objects.requireNonNull(urlEndpoint,"Image urlEndpoint cannot be null"));
    }

    @CacheEvict(cacheNames = CACHE_NAME)
    @Transactional
    @Override
    public void deleteByUrlEndpoint(String urlEndpoint) {
        imageRepository.deleteByUrlEndpoint(Objects.requireNonNull(urlEndpoint,"Image urlEndpoint cannot be null"));
    }

}
