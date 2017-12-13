package com.mindata.ecserver.global.http.response;

/**
 * @author hanliqiang wrote on 2017/12/6
 */
public class GaodeMultipleResponseBean {
    /**
     * 名称
     */
    private String name;
    /**
     * 经纬度
     */
    private String location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "GaodeMultipleResponseBean{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
