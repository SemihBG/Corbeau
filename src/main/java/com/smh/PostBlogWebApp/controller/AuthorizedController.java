package com.smh.PostBlogWebApp.controller;

import com.smh.PostBlogWebApp.entity.Image;
import com.smh.PostBlogWebApp.service.ImageService;
import com.smh.PostBlogWebApp.util.Images;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/authorized")
public class AuthorizedController {

    @Autowired
    private ImageService imageService;

    @PutMapping(value="/image", consumes = {"multipart/form-data"},produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] saveImage(@RequestPart(value = "urlEndpoint") String urlEndpoint,
                            @RequestPart("image") MultipartFile file) throws IOException {

        if(urlEndpoint==null || file==null || /*file.getBytes()==null ||*/ urlEndpoint.length()==0 || file.getBytes().length==0){
            throw new NullPointerException("urlEndpoint and file cannot be null or empty");
        }

        if(urlEndpoint.equals(Images.IMAGE_NOT_FOUND_IMAGE_NAME) || urlEndpoint.equals(Images.ICON_IMAGE_NAME)){
            throw new IllegalArgumentException("urlEndpoint is duplicated with predefined image names");
        }

        byte[]content=file.getInputStream().readAllBytes();
        Image image=new Image();
        image.setContent(content);
        image.setUrlEndpoint(urlEndpoint);
        return imageService.save(image).getContent();
    }

    @DeleteMapping("/image/{urlEndpoint}")
    public void deleteImage(@PathVariable("urlEndpoint") String urlEndpoint){
        imageService.deleteByUrlEndpoint(urlEndpoint);
    }

}
