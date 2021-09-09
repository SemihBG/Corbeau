package com.semihbkgr.corbeau.model.trace;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientRequest {

    private String clientIpAddr;
    private String requestUrl;
    private String requestMethod;
    private int requestCount;
    private long firstRequestTimeMs;
    private long lastRequestTimeMs;

}
