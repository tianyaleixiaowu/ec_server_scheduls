package com.mindata.ecserver.global.http.response;

import java.util.List;

/**
 * @author hanliqiang wrote on 2017/11/29
 */
public class BaiduConvertResponseData implements ResponseValue {
    private Integer status;

    private List<BaiduConvertResponseBean> result;
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
        return null;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<BaiduConvertResponseBean> getResult() {
        return result;
    }

    public void setResult(List<BaiduConvertResponseBean> result) {
        this.result = result;
    }
}
