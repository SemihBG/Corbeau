package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.error.IllegalValueException;
import com.semihbkgr.corbeau.model.Image;
import com.semihbkgr.corbeau.repository.ImageRepository;
import com.semihbkgr.corbeau.util.ParameterUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    public Mono<Image> save(@NonNull Image image) {
        return imageRepository.save(image.withId(0));
    }

    @Override
    public Mono<Image> update(int id, @NonNull Image image) throws IllegalValueException {
        if (id <= 0)
            throw new IllegalValueException("id must be positive value", ImageRepository.TABLE_NAME, "id", id);
        return imageRepository.findById(id)
                .switchIfEmpty(Mono.error(() ->
                        new IllegalValueException("Image not available by given id", ImageRepository.TABLE_NAME, "id", id)))
                .map(savedImage -> {
                    savedImage.setName(image.getName());
                    savedImage.setExtension(image.getExtension());
                    savedImage.setWidth(image.getWidth());
                    savedImage.setHeight(image.getHeight());
                    savedImage.setSize(image.getSize());
                    return savedImage;
                })
                .flatMap(imageRepository::save);
    }

    @Override
    public Mono<Image> findByFullName(@NonNull String fullName) {
        var nameExtPair = ParameterUtils.extractNameAndExtension(fullName);
        return imageRepository.findOneByNameAndExtension(nameExtPair.getFirst(), nameExtPair.getSecond());
    }

    @Override
    public Flux<Image> findAll(@NonNull Pageable pageable) {
        return imageRepository.findAllBy(pageable);
    }


    @Override
    public Mono<Long> count() {
        return imageRepository.count();
    }

    @Override
    public Mono<Void> deleteById(int id) throws IllegalValueException {
        if (id <= 0)
            throw new IllegalValueException("id must be positive value", ImageRepository.TABLE_NAME, "id", id);
        return imageRepository.deleteById(id);
    }

}
