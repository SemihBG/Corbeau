package com.smh.PostBlogWebApp.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name="url_endpoint",nullable = false,unique = true)
    private String urlEndpoint;

    @Lob
    @Column(name="content",nullable = false)
    private byte[] content;

}
