package com.ezjobs.mystory.util;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
public class CommonLogger {
    static final Logger logger =  LoggerFactory.getLogger(CommonLogger.class);
    
    public static Logger getLogger() {
    	return logger;
    }
    
    @Around("execution(* com.ezjobs.mystory..*.*(..))")
    public Object loggingDefault(ProceedingJoinPoint pjp) throws Throwable {
    	 StopWatch stopWatch = new StopWatch();
        logger.info("start - " + pjp.getSignature().getDeclaringTypeName() + " / " + pjp.getSignature().getName());
        stopWatch.start();
        Object result = pjp.proceed();
        stopWatch.stop();
        logger.info("finished - " + pjp.getSignature().getDeclaringTypeName() + " / " + pjp.getSignature().getName()
        	+" / " + stopWatch.getTotalTimeMillis() + "(ms)");
        return result;
    }
    
    @Around("execution(* com.ezjobs.mystory.controller..*.*(..))")
    public Object loggingController(ProceedingJoinPoint pjp) throws Throwable {
        Object result = pjp.proceed();
        long start = System.currentTimeMillis();
	    logger.info("Elapsed before Jsp: " + (System.currentTimeMillis() - start));
	    HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
	    request.setAttribute("startTime", start);
        return result;
    }
}