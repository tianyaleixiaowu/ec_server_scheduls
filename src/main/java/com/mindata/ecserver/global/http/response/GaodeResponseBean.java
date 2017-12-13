package com.mindata.ecserver.global.http.response;

/**
 * @author wuweifeng wrote on 2017/11/29.
 */
public class GaodeResponseBean {
    /**
     * location: "116.285657,39.835448"
     */
    private String location;
    /**
     * 如"门牌号"
     */
    private String level;

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

    @Override
    public String toString() {
        return "GaodeResponseBean{" +
                "location='" + location + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
