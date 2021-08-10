package com.semihbkgr.corbeau.model;

import com.semihbkgr.corbeau.model.base.AllAuditable;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@Table("posts")
public class Post extends AllAuditable implements Serializable {

    @Id
    private int id;

    @NotNull(message = "{post.title.notnull}")
    @Length(min = 8, max = 64, message = "{post.title.length}")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "{post.title.pattern}")
    private String title;

    //TODO content custom tag validaiton
    private String content;

    @PositiveOrZero(message = "{post.subjectId.positiveOrZero}")
    @Column("subject_id")
    private int subjectId;

    @NotNull(message = "{post.endpoint.notnull}")
    @Length(min = 4, max = 64, message = "{post.endpoint.length}")
    @Pattern(regexp = "^[a-zA-Z0-9-]+$", message = "{post.endpoint.pattern}")
    private String endpoint;

    private boolean activated;

    private String thumbnailEndpoint;

    private String description;

    @Transient
    private int viewCount;

}
