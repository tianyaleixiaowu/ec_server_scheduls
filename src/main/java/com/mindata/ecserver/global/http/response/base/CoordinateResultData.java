package com.mindata.ecserver.global.http.response.base;

/**
 * @author hanliqiang wrote on 2017/12/7
 */
public class CoordinateResultData implements ResponseValue {
    /**
     * 百度地图坐标 (经度在前，维度在后)
     */
    private String baiduCoordinate;

    /**
     * 高德地图坐标 (经度在前，维度在后)
     */
    private String gaodeCoordinate;
    /**
     * 状态
     * 0 正常  1 多个地址  2 无地址
     */
    private Integer status;
    /**
     * 来源   1 百度map、 2 高德map
     */
    private Integer source;
    /**
     * 准确度
     * 0：确认  1可能对  2 不太靠谱
     */
    private Integer accuracy;
    /**
     * 查询条件    1 地址  2公司名称
     */
    private Integer queryCondition;
    /**
     * 查询条件值
     */
    private String queryConditionValue;
    /**
     * 地点描述
     */
    private String level;

    public String getBaiduCoordinate() {
        return baiduCoordinate;
    }

    public void setBaiduCoordinate(String baiduCoordinate) {
        this.baiduCoordinate = baiduCoordinate;
    }

    public String getGaodeCoordinate() {
        return gaodeCoordinate;
    }

    public void setGaodeCoordinate(String gaodeCoordinate) {
        this.gaodeCoordinate = gaodeCoordinate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }

    public Integer getQueryCondition() {
        return queryCondition;
    }

    public void setQueryCondition(Integer queryCondition) {
        this.queryCondition = queryCondition;
    }

    public String getQueryConditionValue() {
        return queryConditionValue;
    }

    public void setQueryConditionValue(String queryConditionValue) {
        this.queryConditionValue = queryConditionValue;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "CoordinateResultData{" +
                "baiduCoordinate='" + baiduCoordinate + '\'' +
                ", gaodeCoordinate='" + gaodeCoordinate + '\'' +
                ", status=" + status +
                ", source=" + source +
                ", accuracy=" + accuracy +
                ", queryCondition=" + queryCondition +
                ", queryConditionValue='" + queryConditionValue + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
