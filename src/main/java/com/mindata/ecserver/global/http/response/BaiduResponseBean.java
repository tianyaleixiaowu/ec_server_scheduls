package com.mindata.ecserver.global.http.response;

/**
 * @author wuweifeng wrote on 2017/11/29.
 */
public class BaiduResponseBean {
    private Integer precise;

    private Integer confidence;

    private String level;

    private BaiduLocationResultBean location;

    public Integer getPrecise() {
        return precise;
    }

    public void setPrecise(Integer precise) {
        this.precise = precise;
    }

    public Integer getConfidence() {
        return confidence;
    }

    public void setConfidence(Integer confidence) {
        this.confidence = confidence;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public BaiduLocationResultBean getLocation() {
        return location;
    }

    public void setLocation(BaiduLocationResultBean location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "BaiduResponseBean{" +
                "precise=" + precise +
                ", confidence=" + confidence +
                ", level='" + level + '\'' +
                ", location=" + location +
                '}';
    }
}
