package com.semihbkgr.corbeau.model;

import com.semihbkgr.corbeau.model.base.TimeAuditable;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("posts")
public class Post extends TimeAuditable implements Serializable {

    @Id
    private String id;

    private String title;

    private String content;

    private String subjectId;

    private int viewCount;

}
