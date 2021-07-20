package com.semihbkgr.corbeau.model;

import com.semihbkgr.corbeau.model.base.AllAuditable;
import com.semihbkgr.corbeau.model.base.TimeAuditable;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("posts")
public class Post extends AllAuditable implements Serializable {

    @Id
    private int id;

    private String title;

    private String content;

    private String subjectId;

    @Transient
    private int viewCount;

}
