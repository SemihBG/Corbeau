package com.semihbkgr.corbeau.service;

import com.semihbkgr.corbeau.model.trace.ClientRequest;

public interface RequestTraceService {

    String COMMENT_REQUEST_NAME="Comment";
    String LOGIN_REQUEST_NAME="Login";

    ClientRequest increaseAndUpdate(String clientIpAddr);

}
