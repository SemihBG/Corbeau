package com.smh.PostBlogWebApp.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Profile("development")
@Component
@Slf4j
public class CacheStartUp implements CommandLineRunner {

    @Value("${redis.startup.clear:false}")
    private boolean clear;

    @Autowired
    private CacheManager cacheManager;

    @Override
    public void run(String... args) throws Exception {
        if(clear){
            cacheManager.getCacheNames()
                    .parallelStream()
                    .forEach(c->{
                        cacheManager.getCache(c).clear();
                        log.info("Cache cleared in start up time, name:{}",c);
                    });
        }
    }

}
