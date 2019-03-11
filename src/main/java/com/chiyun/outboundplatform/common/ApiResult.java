package com.chiyun.outboundplatform.common;

import java.io.Serializable;

public class ApiResult<T> implements Serializable {

    private int resCode = 200;
    private String resMsg = "success";
    private T data;

    public ApiResult() {
    }

    public ApiResult(int resCode) {
        this.resCode = resCode;
    }

    public ApiResult(String resMsg) {
        this.resMsg = resMsg;
    }

    public ApiResult(T data) {
        this.data = data;
    }

    public ApiResult(int resCode, String resMsg) {
        this.resCode = resCode;
        this.resMsg = resMsg;
    }

    public int getResCode() {
        return resCode;
    }

    public void setResCode(int resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <W> ApiResult<W> SUCCESS(W data) {
        ApiResult<W> result = new ApiResult<>();
        result.setData(data);
        return result;
    }

    public static ApiResult<Object> SUCCESS() {
        ApiResult<Object> result = new ApiResult();
        return result;
    }

    public static ApiResult<Object> FAILURE(String msg) {
        ApiResult<Object> result = new ApiResult<>();
        result.setResCode(-1);
        result.setResMsg(msg);
        return result;
    }

    public static ApiResult<Object> FAILURE() {
        return FAILURE("failure");
    }

    public static ApiResult<Object> UNKNOWN() {
        ApiResult<Object> result = new ApiResult<>();
        result.setResCode(100);
        result.setResMsg("未登录或登陆失败，请重新登录");
        return result;
    }
}
