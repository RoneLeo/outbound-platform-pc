package com.chiyun.outboundplatform.utils;

import com.alibaba.fastjson.JSONObject;
import com.chiyun.outboundplatform.common.ApiResult;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by wazto on 2019/3/21.
 */
public class MessageUtils {
    public static void resultMsg(HttpServletResponse response, ApiResult<Object> result) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter pw = response.getWriter();
        String resultjson = JSONObject.toJSONString(result);
        pw.write(resultjson);
    }
}
