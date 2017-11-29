package com.mindata.ecserver.global.http.response;

import java.util.List;

/**
 * @author wuweifeng wrote on 2017/11/29.
 */
public class GaodeResponseData implements ResponseValue {
    /**
     * 成功是"1"
     */
    private String status;

    private List<GaodeResponseBean> geocodes;

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
        return geocodes;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<GaodeResponseBean> getGeocodes() {
        return geocodes;
    }

    public void setGeocodes(List<GaodeResponseBean> geocodes) {
        this.geocodes = geocodes;
    }
}
