package com.epam.esm.boot.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;

@Slf4j
@ControllerAdvice
public class ControllerLoggingAdvice {
    @Pointcut("execution(public * com.epam.esm.service..*(..))")
    private void allInService(){}

    @Before("allInService")
    private void logAllInService(JoinPoint jp) {
        log.info(String.format("Request to %s with parameters %s", jp.getSignature(), Arrays.toString(jp.getArgs())));
    }
}
