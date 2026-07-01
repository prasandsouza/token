package com.JWT.token.Aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.logging.Logger;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(LoggingAspect.class);

    // This pointcut matches any method annotated with @LogExecution
    @Around("@annotation(com.example.aopdemo.annotation.LogExecution)")
    public Object logInputAndOutput(ProceedingJoinPoint joinPoint) throws Throwable {

        // 1. Get method metadata
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();

        // 2. Log the Input arguments before method execution
        log.info(">>> AOP LOG [INPUT]  - Method: {} | Arguments: {}", methodName, Arrays.toString(args));

        Object result;
        try {
            // 3. Allow the actual method to execute
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            // Optional: Log if an exception happens
            log.error(">>> AOP LOG [ERROR]  - Method: {} threw exception: {}", methodName, throwable.getMessage());
            throw throwable;
        }

        // 4. Log the Output return value after method execution
        log.info("<<< AOP LOG [OUTPUT] - Method: {} | Return Value: {}", methodName, result);

        // 5. Return the result back to the original caller
        return result;
    }
}
