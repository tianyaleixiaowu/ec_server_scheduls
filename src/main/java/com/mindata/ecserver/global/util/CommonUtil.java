package com.mindata.ecserver.global.util;

import com.xiaoleilu.hutool.date.DateUtil;

import java.util.Date;
import java.util.UUID;

/**
 * @author wuweifeng wrote on 2017/11/7.
 */
public class CommonUtil {
    public static Date getNow() {
        return new Date();
    }

    public static long getTimeStamp() {
        return System.currentTimeMillis();
    }

    public static String getNowStr() {
        return DateUtil.formatDateTime(new Date());
    }

    /**
     * 生成uuid
     *
     * @return
     * token
     */
    public static String token() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
