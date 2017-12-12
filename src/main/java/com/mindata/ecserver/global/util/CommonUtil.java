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
     * @return token
     */
    public static String token() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 获取总页数
     *
     * @param totalCount 总数量
     * @param pageSize   一页多少
     * @return 结果
     */
    public static Integer getTotalPages(Integer totalCount, Integer pageSize) {
        return totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
    }

}
