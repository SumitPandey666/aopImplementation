package com.custom.aopimpl.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    //Logic for @LogExecutionTime
    @Around("@annotation(com.custom.aopimpl.annotation.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint)throws Throwable{
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;
        log.info("Method: {} excuted in {} ms", joinPoint.getSignature().getName(), executionTime);

        return proceed;
    }

    //Logic for @TrackErrors
    @AfterThrowing(pointcut = "@annotation(com.custom.aopimpl.annotation.TrackErrors)", throwing = "ex")
    public void logErrors(JoinPoint joinPoint, Throwable ex){
        log.error("Exception in method: {} | Reason: {} | Args: {}",
                joinPoint.getSignature().getName(),
                ex.getMessage(),
                joinPoint.getArgs());
    }

}
