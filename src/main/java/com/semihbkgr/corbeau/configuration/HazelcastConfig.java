package com.semihbkgr.corbeau.configuration;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.semihbkgr.corbeau.model.trace.ClientRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    public IMap<String, ClientRequest> commentRequestTraceMap(@Qualifier("hazelcastInstance") HazelcastInstance hazelcastInstance){
        return hazelcastInstance.getMap();
    }

}
