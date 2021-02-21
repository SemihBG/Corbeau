package com.smh.PostBlogWebApp.util.search;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class SearchPageRequest extends PageRequest {

    protected SearchPageRequest(int page, int size) {
        super(page, size, Sort.unsorted());
    }

    public static SearchPageRequest of(int page,int size){
        return new SearchPageRequest(page,size);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}