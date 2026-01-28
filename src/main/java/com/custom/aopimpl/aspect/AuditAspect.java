package com.custom.aopimpl.aspect;

import com.custom.aopimpl.annotation.AuditOperation;
import com.custom.aopimpl.entity.AuditLog;
import com.custom.aopimpl.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AuditAspect {

    @Autowired
    private final AuditLogRepository auditLogRepository;

    @AfterReturning(pointcut = "@annotation(auditAnnotation)", returning = "result")
    public void auditAction(JoinPoint joinPoint, AuditOperation auditAnnotation, Object result){
        String methodName = joinPoint.getSignature().getName();
        String actionDescription = auditAnnotation.value();
        String args = Arrays.toString(joinPoint.getArgs());

        AuditLog logEntry = AuditLog.builder()
                .action(actionDescription)
                .methodName(methodName)
                .timeStamp(LocalDateTime.now().toString())
                .details("Args: "+ args)
                .build();

        auditLogRepository.save(logEntry);
        log.info("Audit record saved for action: {}", actionDescription);
    }
}
