package com.semihbkgr.corbeau.aspect;

import com.semihbkgr.corbeau.service.CounterService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class InvocationCounterAdvice {

    private final CounterService counterService;

    @Autowired
    public InvocationCounterAdvice(CounterService counterService) {
        this.counterService = counterService;
    }

    @Before("@annotation(InvocationCounter)")
    public void count(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String value=signature.getMethod().getAnnotation(InvocationCounter.class).value();
        for(int i=0;i<signature.getMethod().getParameters().length;i++){
            if(signature.getParameterNames()[i].equals(value)){
                String name=joinPoint.getArgs()[i].toString();
                counterService.incrementBufferAndGet(name);
                log.debug("Counter is increased, name = "+name);
            }
        }
    }

}
