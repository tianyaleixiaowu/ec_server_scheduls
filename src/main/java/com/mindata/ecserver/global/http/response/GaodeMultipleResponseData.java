package com.mindata.ecserver.global.http.response;

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
    /**
     *
     */
    private List<GaodeMultipleResponseBean> pois;
    @Override
    public int getStatus() {
        return "1".equals(status) ? 0 : -1;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public Object getData() {
        return pois;
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
}
