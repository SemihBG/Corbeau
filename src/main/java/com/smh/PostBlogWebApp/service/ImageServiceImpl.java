package com.smh.PostBlogWebApp.service;

import com.smh.PostBlogWebApp.entity.Image;
import com.smh.PostBlogWebApp.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Image save(Image image) {
        return imageRepository.save(Objects.requireNonNull(image,"Image cannot be null"));
    }

    @Nullable
    @Override
    public Image getByEndPoint(String urlEndpoint) {
        return imageRepository.findByUrlEndpoint(Objects.requireNonNull(urlEndpoint,"Image urlEndpoint cannot be null"));
    }

    @Transactional
    @Override
    public void deleteByUrlEndpoint(String urlEndpoint) {
        imageRepository.deleteByUrlEndpoint(Objects.requireNonNull(urlEndpoint,"Image urlEndpoint cannot be null"));
    }

}
