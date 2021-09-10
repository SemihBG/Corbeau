package com.semihbkgr.corbeau.model.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonAuditable {

    @CreatedBy
    protected String createdBy;

    @LastModifiedBy
    protected String updatedBy;

}
