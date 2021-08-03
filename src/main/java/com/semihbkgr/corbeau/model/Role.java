package com.semihbkgr.corbeau.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@Table("roles")
public class Role implements Serializable {

    @Id
    private int id;

    private String name;

}
