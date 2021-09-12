package com.semihbkgr.corbeau.model;

import com.semihbkgr.corbeau.model.base.TimeAuditable;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@Table("comments")
public class Comment extends TimeAuditable {

    @Id
    private int id;

    @NotEmpty(message = "{comment.content.notEmpty}")
    @Length(min = 4, max = 256, message = "{comment.content.length}")
    private String content;

    @NotEmpty(message = "{comment.name.notEmpty}")
    @Length(min = 4, max = 32, message = "{comment.name.length}")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "{comment.name.pattern}")
    private String name;

    @NotEmpty(message = "{comment.surname.notEmpty}")
    @Length(min = 4, max = 32, message = "{comment.surname.length}")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "{comment.surname.pattern}")
    private String surname;

    @NotEmpty(message = "{comment.email.notEmpty}")
    @Length(min = 4, max = 64, message = "{comment.email.length}")
    @Email(regexp = "^(.+)@(.+)$", message = "{comment.email.emil}")
    private String email;

    @PositiveOrZero(message = "{comment.postId.positiveOrZero}")
    @Column("post_id")
    private int postId;

}
