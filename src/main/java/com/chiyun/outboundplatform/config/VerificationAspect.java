//package com.chiyun.outboundplatform.config;
//
//import com.chiy.szssgl.common.ApiResult;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Configuration;
//
//@Aspect
//@Configuration
//public class VerificationAspect {
//
//    private final static Logger logger = LoggerFactory.getLogger(VerificationAspect.class);
//
//    //    @Pointcut("execution(public * com.chiy.szssgl.web.*.*(..))&&!within(com.joey.controller.IndexController)")
//    @Pointcut("within(com.chiy.szssgl.web..*)&&!within(com.chiy.szssgl.web.IndexController)")
//    public void login() {
//
//    }
//
//    @Around("login()")
//    public Object signVerification(ProceedingJoinPoint pjp) throws Throwable {
//        SignVerification sv = new SignVerification();
//        if (sv.Verification()) {
//            return pjp.proceed();
//        } else {
//            return ApiResult.UNKNOWN();
//        }
//    }
//
//}
