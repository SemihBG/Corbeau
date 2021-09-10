package com.semihbkgr.corbeau.model.trace;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientRequest implements Serializable {

    private String clientIpAddr;
    private String requestName;
    private int requestCount;
    private long firstRequestTimeMs;
    private long lastRequestTimeMs;

    public ClientRequest increaseRequestCount(){
        requestCount++;
        return this;
    }

}
