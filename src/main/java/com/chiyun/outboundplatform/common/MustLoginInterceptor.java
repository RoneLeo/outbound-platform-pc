package com.chiyun.outboundplatform.common;

import com.chiyun.outboundplatform.utils.MessageUtils;
import com.chiyun.outboundplatform.utils.SessionUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class MustLoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod;
        try {
            handlerMethod = (HandlerMethod) handler;
        } catch (Exception e) {
            return true;
        }
        Method method = handlerMethod.getMethod();
        MustLogin annotation = method.getAnnotation(MustLogin.class);
        Object entity = SessionHelper.getuser();
        Integer role = SessionHelper.getrole();
        if (annotation != null) {
            int[] needs = annotation.rolerequired();
            for (int need : needs) {
                if (need == 0 || (role != null && role == need)) {
                    ApiResult<Object> isLogin = SessionUtil.isLogin(SessionHelper.getSession());
                    if (isLogin.getResCode() < 200) {
                        MessageUtils.resultMsg(response, isLogin);
                        return false;
                    }
                    return true;
                }
            }
            if (role == null || entity == null) {
                MessageUtils.resultMsg(response, ApiResult.UNKNOWN());
            } else {
                MessageUtils.resultMsg(response, ApiResult.FAILURE("您没有权限进行此操作"));
            }
            return false;
        }
        return true;
    }

}
