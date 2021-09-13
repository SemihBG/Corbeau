package com.semihbkgr.corbeau.service;

import com.hazelcast.map.IMap;
import com.semihbkgr.corbeau.model.trace.ClientRequest;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CommentTraceService implements RequestTraceService {

    private final IMap<String, ClientRequest> commentTraceMap;

    @Autowired
    public CommentTraceService(@Qualifier("commentRequestTraceMap") IMap<String, ClientRequest> commentTraceMap) {
        this.commentTraceMap = commentTraceMap;
    }

    @Override
    public ClientRequest increaseAndUpdate(@NonNull String clientIpAddr) {
        var currentTimeMs = System.currentTimeMillis();
        var clientRequest = commentTraceMap.get(clientIpAddr);
        if (clientRequest == null)
            clientRequest = ClientRequest.builder()
                    .clientIpAddr(clientIpAddr)
                    .firstRequestTimeMs(currentTimeMs)
                    .lastRequestTimeMs(currentTimeMs)
                    .requestName(RequestTraceService.COMMENT_REQUEST_NAME)
                    .requestCount(0)
                    .build();
        clientRequest.increaseRequestCount();
        commentTraceMap.putAsync(clientIpAddr, clientRequest);
        return clientRequest;
    }

}
