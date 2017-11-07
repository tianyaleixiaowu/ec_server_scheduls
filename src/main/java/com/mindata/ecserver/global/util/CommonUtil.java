package com.mindata.ecserver.global.util;

import java.util.UUID;

/**
 * @author wuweifeng wrote on 2017/11/7.
 */
public class CommonUtil {
    /**
     * 生成uuid
     *
     * @return
     */
    public static String token() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
