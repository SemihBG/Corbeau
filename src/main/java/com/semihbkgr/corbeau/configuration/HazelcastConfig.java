package com.semihbkgr.corbeau.configuration;

import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfig {

    @Bean
    public HazelcastInstance hazelcastInstance(@Autowired(required = false) Config config) {
        if (config == null)
            return Hazelcast.newHazelcastInstance();
        return Hazelcast.newHazelcastInstance(config);
    }

    @Bean
    public ClientConfig clientConfig(){
        var clientConfig=new ClientConfig();
        return clientConfig;
    }

    @Bean

}
