package com.mindata.ecserver.main.model.es;

import com.mindata.ecserver.global.Constant;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.Date;

import static com.mindata.ecserver.global.Constant.ES_GEO_INDEX_NAME;

/**
 * @author hanliqiang wrote on 2017/11/28
 */
@Document(indexName = ES_GEO_INDEX_NAME, type = Constant.ES_COORDINATE_TYPE_NAME, indexStoreType = "fs", shards = 5, replicas = 1,
        refreshInterval = "-1")
public class EsCompanyCoordinate {
    @Id
    private Long id;
    /**
     * 未推送表的id
     */
    private Long contactId;
    /**
     * 百度地图坐标 (纬度在前，经度在后)
     */
    @GeoPointField
    private String baiduCoordinate;

    /**
     * 高德地图坐标 (纬度在前，经度在后)
     */
    @GeoPointField
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

    private String level;
    /**
     * 插入es日期
     */
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
