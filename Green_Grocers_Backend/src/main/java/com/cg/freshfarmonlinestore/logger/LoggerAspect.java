package com.cg.freshfarmonlinestore.logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Aspect for logging execution of service methods.
 */
@Component
@Aspect
public class LoggerAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerAspect.class);

    /**
     * Advice that logs when a method in the service package is called.
     * 
     * @param joinPoint The join point representing the method call.
     */
    @Before("execution(* com.cg.freshfarmonlinestore.service..*(..))")
    public void logMethodCall(JoinPoint joinPoint) {
        LOGGER.info("Method called: " + joinPoint.getSignature().getName());
    }

    /**
     * Advice that logs when a method in the service package is executed successfully.
     * 
     * @param joinPoint The join point representing the method execution.
     */
    @AfterReturning("execution(* com.cg.freshfarmonlinestore.service..*(..))")
    public void logMethodExecutedSuccessfully(JoinPoint joinPoint) {
        LOGGER.info("Method executed successfully: " + joinPoint.getSignature().getName());
    }

    /**
     * Advice that logs when a method in the service package throws an exception.
     * 
     * @param joinPoint The join point representing the method where the exception was thrown.
     */
    @AfterThrowing("execution(* com.cg.freshfarmonlinestore.service..*(..))")
    public void logMethodCrash(JoinPoint joinPoint) {
        LOGGER.info("Error thrown at: " + joinPoint.getSignature().getName());
    }
}
