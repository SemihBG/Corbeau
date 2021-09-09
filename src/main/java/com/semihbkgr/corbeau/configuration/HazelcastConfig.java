package com.semihbkgr.corbeau.configuration;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.semihbkgr.corbeau.model.trace.ClientRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfig {

    private final String commentRequestTraceMapName;
    private final String loginRequestTraceMapName;

    public HazelcastConfig(@Value("${hazelcast.trace.comment.map-name:comment-map}") String commentRequestTraceMapName,
                           @Value("${hazelcast.trace.login.map-name:login-map}") String loginRequestTraceMapName) {
        this.commentRequestTraceMapName = commentRequestTraceMapName;
        this.loginRequestTraceMapName = loginRequestTraceMapName;
    }

    @Bean
    public HazelcastInstance hazelcastInstance() {
        return Hazelcast.newHazelcastInstance();
    }

    @Bean
    public IMap<String, ClientRequest> commentRequestTraceMap(HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getMap(commentRequestTraceMapName);
    }

    @Bean
    public IMap<String, ClientRequest> loginRequestTraceMap(HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getMap(loginRequestTraceMapName);
    }

}
