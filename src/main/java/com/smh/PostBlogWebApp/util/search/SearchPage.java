package com.smh.PostBlogWebApp.util.search;

import lombok.NonNull;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class SearchPage<T> implements Serializable {

    private final List<T> content;
    private final SearchPageRequest searchPageRequest;
    private final int count;

    public SearchPage(@NonNull List<T> content,@NonNull SearchPageRequest searchPageRequest,int count) {
        this.content = Collections.unmodifiableList(content);
        this.searchPageRequest=searchPageRequest;
        this.count=count;
    }

    public List<T> toList() {
        return Collections.unmodifiableList(content);
    }

    public int contentSize(){
        return content.size();
    }

    public SearchPageRequest getSearchPageRequest() {
        return searchPageRequest;
    }

    public int getCount() {
        return count;
    }

}
