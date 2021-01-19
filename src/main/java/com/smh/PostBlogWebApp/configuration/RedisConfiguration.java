package com.smh.PostBlogWebApp.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

@Configuration
public class RedisConfiguration {

    private final String hostname;
    private final int port;

    public RedisConfiguration(@Value("${redis.hostname:localhost}") String hostname,
                              @Value("${redis.port:6379}") int port) {
        this.hostname = hostname;
        this.port = port;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(){
        RedisStandaloneConfiguration configuration=new RedisStandaloneConfiguration();
        configuration.setHostName(hostname);
        configuration.setPort(port);
        return new JedisConnectionFactory(configuration);
    }

    @Bean
    public RedisTemplate<String,Object> redisTemplate(){
        RedisTemplate<String,Object> template=new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }



}
