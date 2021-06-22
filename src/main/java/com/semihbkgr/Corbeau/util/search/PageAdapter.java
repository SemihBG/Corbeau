package com.semihbkgr.Corbeau.util.search;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class PageAdapter<T> implements Page<T> {

    private final SearchPage<T> searchPage;

    private PageAdapter (@NonNull SearchPage<T> searchPage){
        this.searchPage=searchPage;
    }

    public static<E> PageAdapter<E> of(SearchPage<E> searchPage){
        return new PageAdapter<>(searchPage);
    }

    @Override
    public int getTotalPages() {
        return searchPage.getCount()/searchPage.getSearchPageRequest().getPageSize()+
                (searchPage.getCount()%searchPage.getSearchPageRequest().getPageSize()==0?0:1);
    }

    @Override
    public long getTotalElements() {
        return searchPage.getCount();
    }

    @Override
    public int getNumber() {
        return searchPage.getSearchPageRequest().getPageNumber();
    }

    @Override
    public int getSize() {
        return searchPage.getSearchPageRequest().getPageSize();
    }

    @Override
    public int getNumberOfElements() {
        return searchPage.contentSize();
    }

    @Override
    public List<T> getContent() {
        return searchPage.toList();
    }

    @Override
    public boolean hasContent() {
        return true;
    }

    @Override
    public Sort getSort() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isFirst() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isLast() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasNext() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasPrevious() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Pageable nextPageable() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Pageable previousPageable() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <U> Page<U> map(Function<? super T, ? extends U> converter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<T> iterator() {
        return searchPage.toList().iterator();
    }

}
