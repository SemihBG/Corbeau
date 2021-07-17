package com.semihbkgr.corbeau.model;

import com.semihbkgr.corbeau.model.base.AllAuditable;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@Table("moderators")
public class Moderator extends AllAuditable implements Serializable {

    @Id
    private int id;

    private String name;

    private String password;

    private String email;

    @Transient
    private List<Role> roles;

}
