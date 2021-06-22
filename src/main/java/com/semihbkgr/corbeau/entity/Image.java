package com.semihbkgr.corbeau.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="images")
public class Image implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="url_endpoint",nullable = false,unique = true)
    private String urlEndpoint;

    @JsonIgnore
    @Lob
    @Column(name="content",nullable = false)
    private byte[] content;

}
