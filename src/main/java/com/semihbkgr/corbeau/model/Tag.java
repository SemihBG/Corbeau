package com.semihbkgr.corbeau.model;

import com.semihbkgr.corbeau.model.base.AllAuditable;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@Table("tags")
public class Tag extends AllAuditable {

    @Id
    private int id;

    private String name;

}
