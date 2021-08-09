package com.semihbkgr.corbeau.model;

import com.semihbkgr.corbeau.model.base.AllAuditable;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@Table("images")
public class Image extends AllAuditable implements Serializable {

    @Id
    private int id;

    @NotEmpty(message = "{}")
    @Length(min = 4,max = 32,message = "{}")
    @Pattern(regexp = "",message = "{}")
    private String name;

    @Length(min = 2,max = 4,message = "{}")
    @Pattern(regexp = "",message = "{}")
    private String extension;

    @Max(value = 3_000,message = "{}")
    @Min(value = 300,message = "{}")
    private int width;

    @Max(value = 3_000,message = "{}")
    @Min(value = 300,message = "{}")
    private int height;

    private long size;

}
