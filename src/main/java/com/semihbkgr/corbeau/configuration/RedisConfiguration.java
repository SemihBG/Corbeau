package com.semihbkgr.corbeau.configuration;

/*
@Configuration
public class RedisConfiguration {

    private final String hostname;
    private final int port;

    public RedisConfiguration(@Value("${spring.redis.host}") String hostname,
                              @Value("${spring.redis.port}") int port) {
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
        template.setDefaultSerializer(new JdkSerializationRedisSerializer());
        return template;
    }

}
*/