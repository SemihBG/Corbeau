package com.smh.PostBlogWebApp.service;


import com.smh.PostBlogWebApp.entity.Image;

public interface ImageService {

    Image save(Image image);
    Image getByEndPoint(String urlEndpoint);
    void deleteByUrlEndpoint(String urlEndpoint);

}
