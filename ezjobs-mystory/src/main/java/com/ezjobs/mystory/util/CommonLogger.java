package com.ezjobs.mystory.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class CommonLogger {
    Logger logger =  LoggerFactory.getLogger(CommonLogger.class);
    
    @Around("execution(* com.ezjobs.mystory..*.*(..))")
    public Object logging(ProceedingJoinPoint pjp) throws Throwable {
    	 StopWatch stopWatch = new StopWatch();
        logger.info("start - " + pjp.getSignature().getDeclaringTypeName() + " / " + pjp.getSignature().getName());
        stopWatch.start();
        Object result = pjp.proceed();
        stopWatch.stop();
        logger.info("finished - " + pjp.getSignature().getDeclaringTypeName() + " / " + pjp.getSignature().getName()
        	+" / " + stopWatch.getTotalTimeMillis() + "(ms)");
        return result;
    }
}