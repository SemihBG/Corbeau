package com.semihbkgr.corbeau.model.projection;

import com.semihbkgr.corbeau.model.base.TimeAuditable;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostShallow extends TimeAuditable {

    private String id;

    private String title;

    private String subjectId;

}
