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

    @Column(name="url_endpoint",nullable = false,unique = true)
    private String urlEndpoint;

    @OneToMany(mappedBy = "subject",fetch = FetchType.LAZY)
    private List<Post> postList;


}
