package com.semihbkgr.corbeau.error;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.semihbkgr.corbeau.model.trace.ClientRequest;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE,
        creatorVisibility = JsonAutoDetect.Visibility.NONE
)
public class RequestTraceException extends RuntimeException {

    @JsonProperty
    private ClientRequest clientRequest;
    private String path;
    private String method;

    public RequestTraceException(ClientRequest clientRequest, String path, String method) {
        super("ClientRequestTraceExcepiton, request: "+clientRequest.getRequestName());
        this.clientRequest = clientRequest;
        this.path = path;
        this.method = method;
    }

}
