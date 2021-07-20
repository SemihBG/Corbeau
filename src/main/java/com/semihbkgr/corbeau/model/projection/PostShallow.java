package com.semihbkgr.corbeau.model.projection;


import org.springframework.beans.factory.annotation.Value;

public interface PostShallow {

    String getId();

    String getTitle();

    String getCreatedBy();

    String getUpdatedBy();

    long getCreatedAt();

    long getUpdatedAt();

    //TODO fix projection bug
    default String getSubjectName() {
        return "none";
    }

}
