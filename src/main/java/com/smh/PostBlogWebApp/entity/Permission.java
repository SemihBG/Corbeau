package com.smh.PostBlogWebApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Table(name="permissions")
@Entity
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column
    private String name;

    @ManyToMany(mappedBy = "permissionList")
    private List<Moderator> moderatorList;

}
