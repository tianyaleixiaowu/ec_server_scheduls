package com.mindata.ecserver.global.geo.retrofit.model.response;

/**
 * @author hanliqiang wrote on 2017/11/24
 */
public class CoordinateResultBean {
    private Integer precise;

    private Integer confidence;

    private String level;

    private LocationResultBean location;

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

    public LocationResultBean getLocation() {
        return location;
    }

    public void setLocation(LocationResultBean location) {
        this.location = location;
    }
}
