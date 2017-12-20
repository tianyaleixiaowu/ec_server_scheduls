package com.mindata.ecserver.global.http.response;

import com.mindata.ecserver.global.coordinate.http.response.base.ResponseValue;

import java.util.List;

/**
 * @author hanliqiang wrote on 2017/12/6
 */
public class GaodeMultipleResponseData implements ResponseValue {
    /**
     * 成功是"1"
     */
    private String status;
    /**
     * 数量
     */
    private String count;
    private List<GaodeMultipleResponseBean> pois;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<GaodeMultipleResponseBean> getPois() {
        return pois;
    }

    public void setPois(List<GaodeMultipleResponseBean> pois) {
        this.pois = pois;
    }

    @Override
    public String toString() {
        return "GaodeMultipleResponseData{" +
                "status='" + status + '\'' +
                ", count='" + count + '\'' +
                ", pois=" + pois +
                '}';
    }
}
