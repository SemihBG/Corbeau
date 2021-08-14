package com.semihbkgr.corbeau.model.projection.combination;

import com.semihbkgr.corbeau.model.Tag;
import com.semihbkgr.corbeau.model.projection.PostDeep;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDeepTagList {

    private PostDeep post;
    private List<Tag> tags;

}
