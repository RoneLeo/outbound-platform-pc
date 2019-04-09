package com.chiyun.outboundplatform.utils;

public class OtherUtils {

    //模糊查询时替换为空字段
    public static String nullReplace(String str){
        if(StringUtil.isNull(str)){
            str="%%";
        }else{
            str="%"+str+"%";
        }
        return str;
    }
}
