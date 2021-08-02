package com.semihbkgr.corbeau.model;

import com.semihbkgr.corbeau.model.base.AllAuditable;
import com.semihbkgr.corbeau.model.base.TimeAuditable;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@Table("subjects")
public class Subject extends AllAuditable implements Serializable {

    @Id
    private int id;

    @Length(min =5,max = 25,message = "Post name length must be between 5-25")
    private String name;

}
