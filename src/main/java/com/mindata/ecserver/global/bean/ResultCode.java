package com.mindata.ecserver.global.bean;

/**
 * @author wuweifeng wrote on 2017/10/23.
 */
public enum ResultCode {
    //成功
    SUCCESS(200),
    //失败
    FAIL(400);
    public int code;

    ResultCode(int code) {
        this.code = code;
    }
}
