package com.semihbkgr.Corbeau.util.search;

public class SearchText {

    private String text;

    public SearchText() {
    }

    public static SearchText empty(){
        return new SearchText();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
