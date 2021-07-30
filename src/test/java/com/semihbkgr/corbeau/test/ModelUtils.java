package com.semihbkgr.corbeau.test;

import com.semihbkgr.corbeau.model.base.AllAuditable;

public class ModelUtils {

    private static final String DEFAULT_CREATE_BY_VALUE="test";
    private static final String DEFAULT_UPDATED_BY_VALUE="test";

    public static <A extends AllAuditable> A setAuditsOfAuditableModel (A a){
        a.setCreatedBy(DEFAULT_CREATE_BY_VALUE);
        a.setUpdatedBy(DEFAULT_UPDATED_BY_VALUE);
        a.setCreatedAt(System.currentTimeMillis());
        a.setUpdatedAt(System.currentTimeMillis());
        return a;
    }

}
