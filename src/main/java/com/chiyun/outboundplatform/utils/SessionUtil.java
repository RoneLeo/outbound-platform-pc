package com.chiyun.outboundplatform.utils;

import com.chiyun.outboundplatform.common.ApiResult;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by wazto on 2019/3/29.
 */
public class SessionUtil {
    private static final Map<String, String> sessionList = new HashMap<String, String>();

    public static void put(String key, String value) {
        sessionList.put(key, value);
    }

    public static ApiResult<Object> isLogin(HttpSession session) {
        String sessionId = session.getId();//获取sessionid
        String id = String.valueOf(session.getAttribute("id"));//用户id，map的键值
        String sessionValue = getMapValue(id);
        if (sessionValue == null) {
            return ApiResult.UNKNOWN();//未登录
        } else if (!sessionValue.equals(sessionId)) {
            return ApiResult.SEIZE();//该帐号在其他地方登录
        } else if (sessionValue.equals(sessionId)) {
            //已登录
            return ApiResult.REPEATLOGIN();//已登录
        }
        return ApiResult.FAILURE("其他情况");//其他情况
    }

    public static String getMapValue(String key) {
        String result = null;
        Iterator<Map.Entry<String, String>> iterator = sessionList.entrySet().iterator();
        Map.Entry<String, String> entry;
        while (iterator.hasNext()) {
            entry = iterator.next();
            if (key.equals(entry.getKey())) {
                result = entry.getValue();
                break;
            }
        }
        System.out.print("result_value:" + result);
        return result;
    }

    public static String getMapKey(String value) {
        String result = null;
        Iterator<Map.Entry<String, String>> iterator = sessionList.entrySet().iterator();
        Map.Entry<String, String> entry;
        while (iterator.hasNext()) {
            entry = iterator.next();
            if (value.equals(entry.getValue())) {
                result = entry.getKey();
                break;
            }
        }
        System.out.print("result_key:" + result);
        return result;
    }
}
