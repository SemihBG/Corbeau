package com.semihbkgr.corbeau.model.base;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

public class AllAuditable {

    @CreatedBy
    private int createdBy;

    @LastModifiedDate
    private int updatedBy;

    @CreatedDate
    private long createdAt;

    @LastModifiedDate
    private long updatedAt;

}
