package com.semihbkgr.corbeau.model;

import com.semihbkgr.corbeau.model.base.AllAuditable;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@Table("posts")
public class Post extends AllAuditable implements Serializable {

    @Id
    private int id;

    @Length(min = 8, max = 64, message = "{post.title.length}")
    private String title;

    private String content;

    @Column("subject_id")
    private int subjectId;

    private String endpoint;

    private boolean activated;

    @Transient
    private int viewCount;

}
