package com.mindata.ecserver.global.http.response;

import com.mindata.ecserver.global.coordinate.http.response.base.ResponseValue;

import java.util.List;

/**
 * @author wuweifeng wrote on 2017/11/29.
 */
public class BaiduMultipleResponseData implements ResponseValue {
    /**
     * 成功是0
     */
    private Integer status;
    /**
     * 返回数据总数
     */
    private Integer total;
    /**
     * 返回多个经纬度
     */
    private List<BaiduMultipleResponseBean> results;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<BaiduMultipleResponseBean> getResults() {
        return results;
    }

    public void setResults(List<BaiduMultipleResponseBean> results) {
        this.results = results;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "BaiduMultipleResponseData{" +
                "status=" + status +
                ", total=" + total +
                ", results=" + results +
                '}';
    }
}
