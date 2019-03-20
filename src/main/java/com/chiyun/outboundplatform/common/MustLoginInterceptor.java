package com.chiyun.outboundplatform.common;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
        if (annotation != null) {
            if (entity == null) {
                resultMsg(response);
                return false;
            }
        }
        return true;
    }

    private void resultMsg(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter pw = response.getWriter();
        ApiResult<Object> result = ApiResult.UNKNOWN();
        String resultjson = JSONObject.toJSONString(result);
        pw.write(resultjson);
    }
}
