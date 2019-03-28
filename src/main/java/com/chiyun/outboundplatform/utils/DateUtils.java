package com.chiyun.outboundplatform.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wazto on 2019/3/21.
 */
public class DateUtils {
    static SimpleDateFormat sdfall = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static SimpleDateFormat sdfall2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    static SimpleDateFormat sdfall3 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    static SimpleDateFormat sdfday = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat sdfday2 = new SimpleDateFormat("yyyy/MM/dd");

    public static Date getDateByString(String data) {

        Date time = null;
        if (StringUtil.isNull(data))
            return null;
        try {
            time = sdfall.parse(data);
        } catch (ParseException ignored) {
        }
        if (time == null) {
            try {
                time = sdfday.parse(data);
            } catch (ParseException ignored) {
            }
            if (time == null) {
                try {
                    time = sdfall2.parse(data);
                } catch (ParseException ignored) {
                }
                if (time == null) {
                    try {
                        time = sdfall3.parse(data);
                    } catch (ParseException ignored) {
                    }
                    if (time == null) {
                        try {
                            time = sdfday2.parse(data);
                        } catch (ParseException ignored) {
                        }
                    }
                }
            }
        }
        return time;
    }

    /**
     * 获取日期的开始与结尾
     */
    public static Date getDayTime(Date begin, Date end, int flag) {
        if (begin == null && end != null) {
            begin = end;
        } else if (begin != null && end == null) {
            end = new Date();
        }
        if (flag == 0) {
            // 开始
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(begin);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            begin = calendar.getTime();
            return begin;
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(end);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            end = calendar.getTime();
            return end;
        }
    }
}
