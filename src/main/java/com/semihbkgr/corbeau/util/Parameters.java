package com.semihbkgr.corbeau.util;

import lombok.NonNull;

public class Parameters {


    //Parses string page parameter to page count index
    public static int parsePageCountIndex(@NonNull String page)throws ParsePageCountException {

        int pageCount=0;

        //Parse string to int
        try{
            pageCount=Integer.parseInt(page);
        }catch (NumberFormatException e){
            throw new ParsePageCountException(e.getMessage(),e);
        }

        //set to index value;
        pageCount--;

        //check if index value is invalid
        if(pageCount<0){
            throw new ParsePageCountException("Invalid index value");
        }

        return pageCount;

    }


}
