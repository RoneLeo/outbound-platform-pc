package com.chiyun.outboundplatform.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 切面
 */
@Aspect
@Component
public class WebLogAspect {
    private Logger logger = LoggerFactory.getLogger(String.valueOf(getClass()));
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    /**
     * 切点表达式
     */
    @Pointcut("execution(public * com.chiyun.outboundplatform.web.*.*(..))")
    public void webLog() {
    }

    ;

    /**
     * 前置通知
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        /**
         * 接收到请求,记录请求内容
         */
        startTime.set(System.currentTimeMillis());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        /**
         * 记录下请求内容
         */
        logger.info("URL:" + request.getRequestURL().toString());
//        logger.info("HTTP_METHOD:" + request.getMethod());
//        logger.info("IP:" + request.getRemoteAddr());
        logger.info("CLASS_METHOD:" + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS:" + Arrays.toString(joinPoint.getArgs()));
//        logger.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
    }

    /**
     * 返回后通知
     *
     * @param ret
     * @throws Throwable
     */
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        /**
         * 处理完请求,记录返回内容
         */
//        logger.info("RESPONSE:" + ret);
        logger.info("RESPONSE SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
    }
}