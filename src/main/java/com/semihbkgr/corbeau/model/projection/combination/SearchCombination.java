package com.semihbkgr.corbeau.model.projection.combination;

import com.semihbkgr.corbeau.model.projection.PostDeep;
import com.semihbkgr.corbeau.model.projection.SubjectDeep;
import com.semihbkgr.corbeau.model.projection.TagDeep;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchCombination {

    private List<SubjectDeep> subjects;
    private List<TagDeep> tags;
    private List<PostDeep> posts;

}
