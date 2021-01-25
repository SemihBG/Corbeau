package com.smh.PostBlogWebApp.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Table(name="subjects")
@Entity
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name",nullable = false, unique = true)
    private String name;

    @Column(name="url_endpoint",nullable = false)
    private String urlEndpoint;

    @OneToMany(mappedBy = "subject")
    private List<Post> postList;


}
