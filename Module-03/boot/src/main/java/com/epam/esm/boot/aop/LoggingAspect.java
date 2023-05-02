package com.epam.esm.boot.aop;

import com.epam.esm.exception.BusinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {
    private final ObjectMapper mapper;

    @Pointcut("execution(public * com.epam.esm.service..*(..))")
    private void allInService() {
    }

    @Pointcut("execution(public * com.epam.esm.api..*(..))")
    private void allRestEndpoints() {
    }

    @Before("allInService()")
    private void logAllInService(JoinPoint jp) {
        log.info("Request to {}", jp.getSignature().getName());
    }

    @Before("allRestEndpoints()")
    private void logRestRequest(JoinPoint jp) throws JsonProcessingException {
        log.info("Request to {}", jp.getSignature().toShortString());
        log.info("Body:\n{}", mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jp.getArgs()));
    }

    @AfterReturning(value = "allRestEndpoints()", returning = "response")
    private void logRestResponse(JoinPoint jp, ResponseEntity<?> response) throws JsonProcessingException {
        log.info("Response for: {}", jp.getSignature().toShortString());
        log.info("Status: {}", response.getStatusCode());
        log.debug("Body:\n{}", mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response.getBody()));
    }

    @AfterThrowing(value = "allRestEndpoints()", throwing = "exception")
    private void logRestBusinessException(JoinPoint jp, BusinessException exception) {
        log.info("Error for: {}", jp.getSignature().toShortString());
        log.warn("Error message: {}", exception.getMessage());
    }

    @AfterThrowing(value = "allRestEndpoints()", throwing = "exception")
    private void logRestRuntimeException(JoinPoint jp, RuntimeException exception) {
        if (!(exception instanceof BusinessException))
            log.warn("Error for: {}", jp.getSignature().toShortString(), exception);
    }
}
