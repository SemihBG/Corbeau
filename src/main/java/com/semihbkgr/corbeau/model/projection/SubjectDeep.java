package com.semihbkgr.corbeau.model.projection;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubjectDeep {

    private int id;
    private String name;
    private String createdBy;
    private String updatedBy;
    private long createdAt;
    private long updatedAt;
    private long postCount;

}
