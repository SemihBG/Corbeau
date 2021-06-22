package com.semihbkgr.corbeau.service;


import com.semihbkgr.corbeau.entity.Image;

import java.util.List;

public interface ImageService {

    List<Image> findAll();
    Image save(Image image);
    Image getByEndPoint(String urlEndpoint);
    void deleteByUrlEndpoint(String urlEndpoint);

}
