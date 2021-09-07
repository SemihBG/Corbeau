package com.semihbkgr.corbeau.model.request;

import com.semihbkgr.corbeau.model.Post;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class PostUpdate extends Post {

    private List<Integer> tags;

}
