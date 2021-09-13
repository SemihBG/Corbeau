package com.semihbkgr.corbeau.service;

import com.hazelcast.map.IMap;
import com.semihbkgr.corbeau.model.trace.ClientRequest;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class LoginTraceService implements RequestTraceService{

    private final IMap<String, ClientRequest> loginTraceMap;

    @Autowired
    public LoginTraceService(@Qualifier("loginRequestTraceMap") IMap<String, ClientRequest> loginTraceMap) {
        this.loginTraceMap = loginTraceMap;
    }

    @Override
    public ClientRequest increaseAndUpdate(@NonNull String clientIpAddr) {
        var currentTimeMs = System.currentTimeMillis();
        var clientRequest = loginTraceMap.get(clientIpAddr);
        if (clientRequest == null)
            clientRequest = ClientRequest.builder()
                    .clientIpAddr(clientIpAddr)
                    .firstRequestTimeMs(currentTimeMs)
                    .lastRequestTimeMs(currentTimeMs)
                    .requestName(RequestTraceService.LOGIN_REQUEST_NAME)
                    .requestCount(0)
                    .build();
        clientRequest.increaseRequestCount();
        loginTraceMap.putAsync(clientIpAddr, clientRequest);
        return clientRequest;
    }

}
