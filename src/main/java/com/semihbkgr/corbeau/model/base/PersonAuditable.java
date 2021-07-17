package com.semihbkgr.corbeau.model.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonAuditable {

    @CreatedBy
    private int createdBy;

    @LastModifiedDate
    private int updatedBy;

}
