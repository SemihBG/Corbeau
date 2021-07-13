package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.model.Image;
import com.semihbkgr.corbeau.repository.ImageRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private static final String CACHE_NAME = "image";

    private final ImageRepository imageRepository;

    //@Cacheable(cacheNames = CACHE_NAME)
    @Override
    public Flux<Image> findAll() {
        return imageRepository.findAll();
    }

    //@CachePut(cacheNames = CACHE_NAME,key = "#image.urlEndpoint")
    @Override
    public Mono<Image> save(@NonNull Image image) {
        return imageRepository.save(image);
    }

    //@CacheEvict(cacheNames = CACHE_NAME)
    //@Transactional
    @Override
    public Mono<Void> deleteById(@NonNull String id) {
        return imageRepository.deleteById(id);
    }

}
