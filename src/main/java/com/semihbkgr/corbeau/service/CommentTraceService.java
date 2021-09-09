package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.model.trace.ClientRequest;

public interface CommentTraceService {

    String COMMENT_REQUEST_NAME="Comment";

    ClientRequest saveOrIncrement(String clientIdAddr);
    
}
