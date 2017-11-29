package com.mindata.ecserver.global.http.response;

import java.util.List;

/**
 * @author wuweifeng wrote on 2017/11/29.
 */
public class BaiduMutilResponseData implements ResponseValue {
    /**
     * 成功是"OK"
     */
    private String status;
    /**
     * 返回多个经纬度
     */
    private List<BaiduMutilResponseBean> results;

    @Override
    public int getStatus() {
        return "OK".equals(status) ? 0 : -1;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public Object getData() {
        return results;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<BaiduMutilResponseBean> getResults() {
        return results;
    }

    public void setResults(List<BaiduMutilResponseBean> results) {
        this.results = results;
    }
}
