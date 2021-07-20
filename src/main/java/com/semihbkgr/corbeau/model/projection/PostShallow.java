package com.semihbkgr.corbeau.model.projection;


public interface PostShallow {

    String getId();

    String getTitle();

    String getCreatedBy();

    String getUpdatedBy();

    long getCreatedAt();

    long getUpdatedAt();

    int getSubjectId();

    String getSubjectName();

}
