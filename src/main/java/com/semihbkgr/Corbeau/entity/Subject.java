package com.semihbkgr.Corbeau.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Data
@NoArgsConstructor
@Table(name="subjects")
@Entity
public class Subject implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name",nullable = false, unique = true)
    private String name;

    @Column(name="url_endpoint",nullable = false,unique = true)
    private String urlEndpoint;

    @ToString.Exclude
    @JsonBackReference
    @OneToMany(mappedBy = "subject",fetch = FetchType.LAZY)
    private List<Post> postList;

}
