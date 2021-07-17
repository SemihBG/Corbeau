package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.error.IllegalValueException;
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

    private final ImageRepository imageRepository;

    @Override
    public Flux<Image> findAll() {
        return imageRepository.findAll();
    }

    @Override
    public Mono<Image> save(@NonNull Image image) {
        return imageRepository.save(image.withId(0));
    }

    @Override
    public Mono<Image> update(int id, @NonNull Image image) throws IllegalValueException {
        return imageRepository.findById(id)
                .switchIfEmpty(Mono.error(() ->
                        new IllegalValueException("Image not available by given id", imageRepository.TABLE_NAME, "id", id)))
                .then(imageRepository.save(image.withId(id)));
    }

    @Override
    public Mono<Void> deleteById(int id) throws IllegalValueException {
        return imageRepository.findById(id)
                .switchIfEmpty(Mono.error(() ->
                        new IllegalValueException("Image not available by given id", imageRepository.TABLE_NAME, "id", id)))
                .then(imageRepository.deleteById(id));
    }

}
