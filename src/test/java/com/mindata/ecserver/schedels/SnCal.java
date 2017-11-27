package com.mindata.ecserver.schedels;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author hanliqiang wrote on 2017/11/20
 */
public class SnCal {
    public static void main(String[] args) throws UnsupportedEncodingException {
        SnCal snCal = new SnCal();
        // 计算sn跟参数对出现顺序有关，get请求请使用LinkedHashMap保存<key,value>，该方法根据key的插入顺序排序；
        Map paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put("address", "北京市通州区永乐店镇永新路12040号");
        paramsMap.put("output", "json");
        paramsMap.put("ak", "uDWUhXo4WDUecxSGD97bE1ztHtZKn2sW");
        // 调用下面的toQueryString方法，对LinkedHashMap内所有value作utf8编码，拼接返回结果address=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&output=json&ak=yourak
        String paramsStr = snCal.toQueryString(paramsMap);
        String wholeStr = new String("/geocoder/v2/?" + paramsStr + "ksQopZsTHGGd6g6j6X8FQGRcxHYqvoQG");
        String tempStr = URLEncoder.encode(wholeStr, "UTF-8");
        // 调用下面的MD5方法得到最后的sn签名
        System.out.println(snCal.MD5(tempStr));
    }
    // 对Map内所有value作utf8编码，拼接返回结果
    public String toQueryString(Map<?, ?> data)
            throws UnsupportedEncodingException {
        StringBuffer queryString = new StringBuffer();
        for (Map.Entry<?, ?> pair : data.entrySet()) {
            queryString.append(pair.getKey() + "=");
            String ss[] = pair.getValue().toString().split(",");
            if(ss.length>1){
                for(String s:ss){
                    queryString.append(URLEncoder.encode(s,"UTF-8") + ",");
                }
                queryString.deleteCharAt(queryString.length()-1);
                queryString.append("&");
            }
            else{
                queryString.append(URLEncoder.encode((String) pair.getValue(),
                        "UTF-8") + "&");
            }
        }
        if (queryString.length() > 0) {
            queryString.deleteCharAt(queryString.length() - 1);
        }
        return queryString.toString();
    }
    // 来自stackoverflow的MD5计算方法，调用了MessageDigest库函数，并把byte数组结果转换成16进制
    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                        .substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
}
