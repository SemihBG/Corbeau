package com.semihbkgr.corbeau.model.projection.combine;

import com.semihbkgr.corbeau.model.Tag;
import com.semihbkgr.corbeau.model.projection.PostDeep;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDeepTag {

    private PostDeep postDeep;
    private Tag tag;

}
