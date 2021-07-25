package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.model.Image;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

public interface ImageContentService {

    Mono<Image> save(String name,Mono<FilePart> filePartMono);

    Mono<Image> update(String fullName,String newName, Mono<FilePart> filePartMono);

    Mono<Void> delete(String fullName);

}
