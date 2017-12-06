package com.mindata.ecserver.global.http.response;

/**
 * @author wuweifeng wrote on 2017/11/29.
 */
public class BaiduMutilResponseBean {

    private BaiduLocationResultBean location;

    private String name;

    public BaiduLocationResultBean getLocation() {
        return location;
    }

    public void setLocation(BaiduLocationResultBean location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
