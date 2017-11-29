package com.mindata.ecserver.global.http.response;

/**
 * @author wuweifeng wrote on 2017/11/7.
 */
public class BaseData implements ResponseValue {
    private int code;
    private String message;

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int getStatus() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Object getData() {
        return this;
    }

    @Override
    public String toString() {
        return "BaseData{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
