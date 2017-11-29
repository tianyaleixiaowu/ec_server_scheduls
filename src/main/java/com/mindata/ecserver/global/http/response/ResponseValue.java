package com.mindata.ecserver.global.http.response;

/**
 * @author wuweifeng wrote on 2017/11/29.
 */
public interface ResponseValue {
    /**
     * 状态码
     * @return
     * 状态码
     */
    int getStatus();

    String getMessage();

    /**
     * 对象值
     * @return
     */
    Object getData();
}
