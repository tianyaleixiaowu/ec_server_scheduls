package com.mindata.ecserver.global.coordinate.http.response;

/**
 * @author wuweifeng wrote on 2017/11/29.
 */
public class BaiduMultipleResponseBean {

    private BaiduLocationResultBean location;

    public BaiduLocationResultBean getLocation() {
        return location;
    }

    public void setLocation(BaiduLocationResultBean location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "BaiduMultipleResponseBean{" +
                "location=" + location +
                '}';
    }
}
