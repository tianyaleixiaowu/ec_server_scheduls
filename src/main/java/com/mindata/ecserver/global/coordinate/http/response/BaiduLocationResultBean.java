package com.mindata.ecserver.global.coordinate.http.response;

/**
 * @author wuweifeng wrote on 2017/11/29.
 */
public class BaiduLocationResultBean {
    private Float lng;

    private Float lat;

    public Float getLng() {
        return lng;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public String getCoordinate() {
        return lng + "," + lat;
    }

    @Override
    public String toString() {
        return "BaiduLocationResultBean{" +
                "lng=" + lng +
                ", lat=" + lat +
                '}';
    }
}
