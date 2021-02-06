package com.smh.PostBlogWebApp.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE)
public class CacheStartUp implements CommandLineRunner {

    @Value("${redis.startup.clear:false}")
    private boolean clear;

    @Autowired
    private RedisCacheManager cacheManager;

    @Override
    public void run(String... args) throws Exception {
        if(clear){
            cacheManager.getCacheNames()
                    .parallelStream()
                    .forEach(cacheName->{
                        Cache cache=cacheManager.getCache(cacheName);
                        if(cache!=null){
                            cache.clear();
                        }
                        log.error("Cache cleared in start up time, name:{}",cacheName);
                    });

        }
    }

}
