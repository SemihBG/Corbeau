package com.semihbkgr.corbeau.model.projection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDeep {

    private int id;
    private String name;
    private String createdBy;
    private String updatedBy;
    private long createdAt;
    private long updatedAt;
    private long postCount;

}
