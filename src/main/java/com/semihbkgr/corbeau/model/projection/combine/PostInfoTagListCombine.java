package com.semihbkgr.corbeau.model.projection.combine;

import com.semihbkgr.corbeau.model.Tag;
import com.semihbkgr.corbeau.model.projection.PostDeep;
import com.semihbkgr.corbeau.model.projection.PostInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostInfoTagListCombine {

    private PostInfo post;
    private List<Tag> tags;

}
