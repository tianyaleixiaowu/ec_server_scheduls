package com.mindata.ecserver.global.http.response;

/**
 * @author wuweifeng wrote on 2017/11/29.
 */
public class BaiduResponseData implements ResponseValue {

    private Integer status;

    private BaiduResponseBean result;

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public Object getData() {
        return result;
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
}
