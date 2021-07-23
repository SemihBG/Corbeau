package com.semihbkgr.corbeau.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@Table("comments")
public class Comment {

    @Id
    private int id;

    private String content;

    @Column("post_id")
    private int postId;

    private String name;

    private String surname;

    private String email;

    @CreatedDate
    private long createdAt;

}
