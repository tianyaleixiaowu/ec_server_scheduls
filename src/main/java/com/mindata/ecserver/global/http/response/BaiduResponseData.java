package com.mindata.ecserver.global.http.response;

import com.mindata.ecserver.global.coordinate.http.response.base.ResponseValue;

/**
 * @author wuweifeng wrote on 2017/11/29.
 */
public class BaiduResponseData implements ResponseValue {

    private Integer status;

    private BaiduResponseBean result;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BaiduResponseBean getResult() {
        return result;
    }

    public void setResult(BaiduResponseBean result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "BaiduResponseData{" +
                "status=" + status +
                ", result=" + result +
                '}';
    }
}
