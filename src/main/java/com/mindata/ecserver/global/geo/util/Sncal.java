package com.mindata.ecserver.global.geo.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.mindata.ecserver.global.Constant.*;

/**
 * 计算获得sn值
 *
 * @author hanliqiang wrote on 2017/11/27
 */
public class Sncal {
    /**
     * 计算获得sn值
     *
     * @param address 地址
     * @return 结果
     * @throws UnsupportedEncodingException 异常
     */
    public static String getSnValue(String address) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Map<String, String> paramsMap = new LinkedHashMap<>();
        paramsMap.put("address", address);
        paramsMap.put("output", OUTPUT_TYPE);
        paramsMap.put("ak", BAIDU_MAP_AK);
        String paramsStr = toQueryString(paramsMap);
        String wholeStr = BAIDU_MAP_API_URL + paramsStr + BAIDU_MAP_SK;
        String tempStr = URLEncoder.encode(wholeStr, "UTF-8");
        return md5(tempStr);
    }

    /**
     * 对Map内所有value作utf8编码，拼接返回结果
     *
     * @param data map
     * @return 结果
     * @throws UnsupportedEncodingException 异常
     */
    @SuppressWarnings("all")
    private static String toQueryString(Map<?, ?> data)
            throws UnsupportedEncodingException {
        StringBuffer queryString = new StringBuffer();
        for (Map.Entry<?, ?> pair : data.entrySet()) {
            queryString.append(pair.getKey() + "=");
            String[] ss = pair.getValue().toString().split(",");
            if (ss.length > 1) {
                for (String s : ss) {
                    queryString.append(URLEncoder.encode(s, "UTF-8") + ",");
                }
                queryString.deleteCharAt(queryString.length() - 1);
                queryString.append("&");
            } else {
                queryString.append(URLEncoder.encode((String) pair.getValue(),
                        "UTF-8") + "&");
            }
        }
        if (queryString.length() > 0) {
            queryString.deleteCharAt(queryString.length() - 1);
        }
        return queryString.toString();
    }

    /**
     * 来自stackoverflow的MD5计算方法，调用了MessageDigest库函数，并把byte数组结果转换成16进制z
     *
     * @param md5 算法
     * @return 结果
     */
    @SuppressWarnings("all")
    private static String md5(String md5) throws java.security.NoSuchAlgorithmException {
        java.security.MessageDigest md = java.security.MessageDigest
                .getInstance("MD5");
        byte[] array = md.digest(md5.getBytes());
        StringBuffer sb;
        sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                    .substring(1, 3));
        }
        return sb.toString();
    }
}
