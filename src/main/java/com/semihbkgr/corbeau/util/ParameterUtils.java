package com.semihbkgr.corbeau.util;

import org.springframework.data.util.Pair;
import org.springframework.lang.Nullable;

public class ParameterUtils {

    public static int parsePageToIndex(@Nullable String pageStr) {
        if (pageStr == null) return 0;
        try {
            int page = Integer.parseInt(pageStr);
            return Math.max(page - 1, -1);
        } catch (NumberFormatException ignore) {
            return -1;
        }
    }

    public static String extractExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }

    public static Pair<String,String> extractNameAndExtension(String fileName){
        var index=fileName.lastIndexOf('.');
        return Pair.of(fileName.substring(0,index),fileName.substring(index+1));
    }

}
