package com.mindata.ecserver.global.util;

import com.xiaoleilu.hutool.date.DatePattern;
import com.xiaoleilu.hutool.date.DateUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    /**
     * 截取double2位
     *
     * @param d
     *         d
     * @return 截取后结果
     */
    public static Double cutDouble2(Double d) {
        if (d == null) {
            return 0.00;
        }
        DecimalFormat df = new DecimalFormat("######0.00");
        return Double.parseDouble(df.format(d));
    }

    /**
     * 只接受这样的格式2017-09-3，不能带时分秒
     *
     * @param date
     *         年月日
     * @return 该天的开始
     */
    public static Date beginOfDay(String date) {
        DateFormat dateFormat = new SimpleDateFormat(DatePattern.NORM_DATETIME_PATTERN);
        try {
            return dateFormat.parse(date + " 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 只接受这样的格式2017-09-3，不能带时分秒
     *
     * @param date
     *         年月日
     * @return 该天的结束
     */
    public static Date endOfDay(String date) {
        DateFormat dateFormat = new SimpleDateFormat(DatePattern.NORM_DATETIME_PATTERN);
        try {
            return dateFormat.parse(date + " 23:59:59");
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取本机计算机名称
     */
    public static String getHostName() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostName();
    }
}
