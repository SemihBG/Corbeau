package com.semihbkgr.corbeau.model.projection;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostDeep {

    private int id;
    private String title;
    private int subjectId;
    private String endpoint;
    private boolean activated;
    private String thumbnailEndpoint;
    private String description;
    private String subjectName;
    private String createdBy;
    private String updatedBy;
    private long createdAt;
    private long updatedAt;

}
