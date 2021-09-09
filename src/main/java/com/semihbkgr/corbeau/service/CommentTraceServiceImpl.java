package com.semihbkgr.corbeau.service;

import com.hazelcast.map.IMap;
import com.semihbkgr.corbeau.model.trace.ClientRequest;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class CommentTraceServiceImpl implements CommentTraceService {

    private final IMap<String, ClientRequest> commentTraceMap;

    @Autowired
    public CommentTraceServiceImpl(@Qualifier("commentRequestTraceMap") IMap<String, ClientRequest> commentTraceMap) {
        this.commentTraceMap = commentTraceMap;
    }

    @Override
    public ClientRequest saveOrIncrement(@NonNull String clientIdAddr) {
        var currentTimeMs = System.currentTimeMillis();
        var clientRequest = commentTraceMap.get(clientIdAddr);
        if (clientIdAddr == null)
            clientRequest = ClientRequest.builder()
                    .clientIpAddr(clientIdAddr)
                    .firstRequestTimeMs(currentTimeMs)
                    .lastRequestTimeMs(currentTimeMs)
                    .requestName(CommentTraceService.COMMENT_REQUEST_NAME)
                    .requestCount(0)
                    .build();
        return commentTraceMap.put(clientIdAddr, clientRequest.increaseRequestCount());
    }

}
