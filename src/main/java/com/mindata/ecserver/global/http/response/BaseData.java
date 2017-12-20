package com.mindata.ecserver.global.http.response;

import com.mindata.ecserver.global.bean.ResultCode;
import com.mindata.ecserver.global.http.response.base.ResponseValue;

/**
 * @author wuweifeng wrote on 2017/11/7.
 */
public class BaseData implements ResponseValue {
    private int code;
    private String message;
    private Object data;

    public void setCode(int code) {
        this.code = code;
    }

    public BaseData setMessage(String message) {
        this.message = message;
        return this;
    }

    public BaseData setCode(ResultCode resultCode) {
        this.code = resultCode.code;
        return this;
    }

    public int getCode() {
        return code;
    }

    public BaseData setData(Object data) {
        this.data = data;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    @Override
    public String toString() {
        return "BaseData{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
