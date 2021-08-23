package com.semihbkgr.corbeau.util;

import org.springframework.ui.Model;

public class PageUtils {

    public static  void addPabeAttributedToModel(Model model, long count, int pageIndex, int pageSize){
        var pageCount = (int) Math.ceil((double) count / pageSize);
        model.addAttribute("count", count);
        model.addAttribute("count", count);
        model.addAttribute("page", pageIndex + 1);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("hasPrevious", pageIndex > 0);
        model.addAttribute("hasNext", pageIndex + 1 < pageCount);
    }

}
