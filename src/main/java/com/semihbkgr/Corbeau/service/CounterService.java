package com.semihbkgr.Corbeau.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class CounterService {

    private final String counterKey;
    private final RedisTemplate<String,Object> redisTemplate;
    private final HashOperations<String, Object, Object> hashOperations;

    private final ConcurrentHashMap<String,Integer> counterBuffer;

    @Autowired
    public CounterService(RedisTemplate<String, Object> redisTemplate,
                          @Value("${counter.key:counter}") String counterKey) {
        this.redisTemplate = redisTemplate;
        hashOperations=redisTemplate.opsForHash();
        this.counterKey=counterKey;
        counterBuffer=new ConcurrentHashMap<>();
    }

    public ConcurrentHashMap<String, Integer> getCounterBuffer() {
        return counterBuffer;
    }

    public int incrementBufferAndGet(@NonNull String urlPoint){
        try {
            int currentValue=counterBuffer.get(urlPoint);
            currentValue++;
            counterBuffer.put(urlPoint,currentValue);
            return currentValue;
        }catch (NullPointerException ignore){
            counterBuffer.put(urlPoint,1);
            return 1;
        }
    }
    
    public void saveIncrement(@NonNull String urlEndpoint,int incrementValue){
        Object v = hashOperations.get(counterKey,urlEndpoint);
        int currentValue = v==null?0:(int)v;
        currentValue+=incrementValue;
        hashOperations.put(counterKey,urlEndpoint,currentValue);
    }

    public int getIncrement(@NonNull String urlEndpoint){
        try{
            return (int)hashOperations.get(counterKey,urlEndpoint);
        }catch (NullPointerException ignore){
            return 0;
        }
    }

}
