package com.mindata.ecserver.global.geo.retrofit.model.response;

/**
 * @author hanliqiang wrote on 2017/11/27
 */
public class GaodeCoordinateResultBean {
    private String location;

    private String level;

    private String province;

    private String city;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
