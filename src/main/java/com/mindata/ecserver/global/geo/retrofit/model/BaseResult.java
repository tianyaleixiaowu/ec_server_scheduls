package com.mindata.ecserver.global.geo.retrofit.model;

/**
 * @author hanliqiang wrote on 2017/11/24
 */
public class BaseResult {
    private String status;

    private Integer source;

    private Integer accuracy;

    private Integer queryCondition;

    private String queryConditionValue;

    private String level;

    public BaseResult() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
}
