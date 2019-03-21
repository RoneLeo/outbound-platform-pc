package com.chiyun.outboundplatform.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
}
