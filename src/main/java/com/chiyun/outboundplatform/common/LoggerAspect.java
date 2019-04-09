package com.chiyun.outboundplatform.common;

import com.chiyun.outboundplatform.entity.LogEntity;
import com.chiyun.outboundplatform.web.LogController;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

@Aspect
@Component
public class LoggerAspect {

    private final Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

    @Resource
    private LogController loggerController;

    /**
     * 要处理的方法，包名+类名+方法名
     */
    @Pointcut("@annotation(com.chiyun.outboundplatform.common.ControllerLog)")
    private void cut() {
    }

    /**
     * 在调用上面 @Pointcut标注的方法前执行以下方法
     *
     * @param joinPoint 用于获取类方法
     */
    @Before("cut()")
    public void doBefore(JoinPoint joinPoint) {
    }

    /**
     * 无论Controller中调用方法以何种方式结束，都会执行
     */
    @After("cut()")
    public void doAfter(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        try {
        String mz = (String) session.getAttribute("mz");
        int czrid = (int) session.getAttribute("id");

            LogEntity log = new LogEntity();
            log.setCzsj(getControllerMethodDescription(joinPoint));
            StringBuffer sb = new StringBuffer("请求方法:");
            sb.append(joinPoint.getTarget().getClass().getName()).append(".").append(joinPoint.getSignature().getName()).append("()");
            log.setBz(sb.toString());
            log.setCzrid(czrid);
            log.setCzr(mz);
            loggerController.addLog(log);
        } catch (Exception e) {
            logger.error("异常信息:{}", e.getMessage());
        }
    }

    /**
     * 在调用上面 @Pointcut标注的方法后执行。用于获取返回值
     *
     * @param obj
     */
    @AfterReturning(returning = "obj", pointcut = "cut()")
    public void doAfterReturning(Object obj) {
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public static String getControllerMethodDescription(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(ControllerLog.class).description();
                    break;
                }
            }
        }
        return description;
    }
}
