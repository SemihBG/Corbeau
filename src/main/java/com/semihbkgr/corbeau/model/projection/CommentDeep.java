package com.semihbkgr.corbeau.model.projection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDeep {

    private int id;
    private String name;
    private String surname;
    private String email;
    private String content;
    private int postId;
    private long  createdAt;
    private long  updatedAt;
    private String postTitle;

}
