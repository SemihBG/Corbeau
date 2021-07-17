package com.semihbkgr.corbeau.model.base;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedDate;

public class PersonAuditable {

    @CreatedBy
    private int createdBy;

    @LastModifiedDate
    private int updatedBy;

}
