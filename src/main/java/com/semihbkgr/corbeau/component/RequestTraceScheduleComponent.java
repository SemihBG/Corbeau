package com.semihbkgr.corbeau.component;

import com.hazelcast.map.IMap;
import com.semihbkgr.corbeau.model.trace.ClientRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@EnableScheduling
@EnableAsync
public class RequestTraceScheduleComponent {

    private final IMap<String, ClientRequest> commentRequestTraceMap;
    private final IMap<String, ClientRequest> loginRequestTraceMap;

    @Autowired
    public RequestTraceScheduleComponent(IMap<String, ClientRequest> commentRequestTraceMap,
                                         IMap<String, ClientRequest> loginRequestTraceMap) {
        this.commentRequestTraceMap = commentRequestTraceMap;
        this.loginRequestTraceMap = loginRequestTraceMap;
    }

    @Scheduled(fixedDelay = 30_000)
    @Async
    public void removeExpiredCommentTraces(){
        var currentTimeMs=System.currentTimeMillis();
        commentRequestTraceMap.entrySet()
                .parallelStream()
                .filter(entry-> currentTimeMs-entry.getValue().getFirstRequestTimeMs()>=300_000)
                .map(Map.Entry::getKey)
                .forEach(commentRequestTraceMap::remove);
    }

    @Scheduled(fixedDelay = 30_000)
    @Async
    public void removeExpiredLoginTraces(){
        var currentTimeMs=System.currentTimeMillis();
        loginRequestTraceMap.entrySet()
                .parallelStream()
                .filter(entry-> currentTimeMs-entry.getValue().getFirstRequestTimeMs()>=300_000)
                .map(Map.Entry::getKey)
                .forEach(loginRequestTraceMap::remove);
    }

}
