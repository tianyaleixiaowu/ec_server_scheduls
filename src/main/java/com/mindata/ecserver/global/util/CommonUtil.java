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
     */
    public static String token() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 替换数字后两位
     *
     * @param num
     * @return
     */
    public static String getEncrypt(String num) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < num.length(); i++) {
            if (i < 4 || i > 6) {
                buffer.append(num.charAt(i));
            } else {
                buffer.append('0');
            }
        }
        return buffer.toString();
    }
}
