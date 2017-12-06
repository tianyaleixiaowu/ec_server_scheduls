package com.mindata.ecserver.global.http.response;

/**
 * @author wuweifeng wrote on 2017/11/29.
 */
public interface ResponseValue {
    /**
     * 状态码
     * @return 结果
     * 状态码
     */
    int getStatus();

    /**
     * msg
     * @return 结果
     */
    String getMessage();

    /**
     * 对象值
     * @return 结果
     */
    Object getData();
}
