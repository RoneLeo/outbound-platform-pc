package com.chiyun.outboundplatform.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.UUID;


public class StringUtil {

    public static boolean isNull(String str) {
        if ("".equals(str) || str == null) {
            return true;
        }
        return false;
    }

    public static boolean isNotNull(String str) {
        if ("".equals(str) || str == null) {
            return false;
        }
        return true;
    }



    public static double getMoneyDouble(Double d) {
        double value = new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return value;
    }

    /**
     * 判断对象中属性值是否全为空
     *
     * @param object
     * @return
     */
    public static boolean checkObjAllFieldsIsNull(Object object) {
        if (null == object) {
            return true;
        }
        try {
            for (Field f : object.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                System.out.print(f.getName() + ":");
                System.out.println(f.get(object));
                if (f.get(object) != null && !StringUtil.isNull(f.get(object).toString())) {
                    return false;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * 获取32位 UUID
     * @return
     */
    public  static String  getUUID32(){
        String  uuid= UUID.randomUUID().toString().replace("-","");
        return  uuid;
    }

    /**
     * 获取64位 UUID
     * @return
     */
    public  static String  getUUID64(){
        String  uuid= UUID.randomUUID().toString().replace("-","")+UUID.randomUUID().toString().replace("-","");
        return  uuid;
    }

}
