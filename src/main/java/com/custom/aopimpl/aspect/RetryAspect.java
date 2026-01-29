package com.custom.aopimpl.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class RetryAspect {

    @Around("@annotation(com.custom.aopimpl.annotation.Retry)")
    public Object retryLogic(ProceedingJoinPoint joinPoint) throws  Throwable{
        int attempts = 0;
        int maxRetries = 3;
        Throwable lastException = null;

        while(attempts < maxRetries){
            try{
                return joinPoint.proceed();
            }catch(Throwable e){
                attempts++;
                lastException = e;
                log.warn("Attempt {} failed for {}. Retrying...",attempts, joinPoint.getSignature().getName());
                Thread.sleep(100 * attempts);
            }
        }
        throw lastException;

    }
}
