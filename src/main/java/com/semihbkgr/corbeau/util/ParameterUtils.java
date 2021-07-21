package com.semihbkgr.corbeau.util;

import org.springframework.lang.Nullable;

public class ParameterUtils {

    public static int parsePageToIndex(@Nullable String pageStr){
        if(pageStr==null) return 0;
        try{
            int page=Integer.parseInt(pageStr);
            return Math.max(page-1, -1);
        }catch (NumberFormatException ignore){
            return -1;
        }
    }

}
