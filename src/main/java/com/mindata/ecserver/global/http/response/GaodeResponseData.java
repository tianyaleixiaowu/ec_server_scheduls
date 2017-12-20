package com.mindata.ecserver.global.http.response;

import com.mindata.ecserver.global.coordinate.http.response.base.ResponseValue;

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

    public String getStatus() {
        return status;
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

    @Override
    public String toString() {
        return "GaodeResponseData{" +
                "status='" + status + '\'' +
                ", geocodes=" + geocodes +
                '}';
    }
}
