package com.mindata.ecserver.main.model.secondary;

import javax.persistence.*;
import java.util.Date;

/**
 * 公司坐标
 *
 * @author hanliqiang wrote on 2017/11/24
 */
@Entity
@Table(name = "pt_company_coordinate")
public class CompanyCoordinateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    /**
     * 未推送表的id
     */
    @Column(name = "contact_id")
    private Long contactId;
    /**
     * 百度地图坐标 (经度在前，维度在后)
     */
    @Column(name = "baidu_coordinate")
    private String baiduCoordinate;

    /**
     * 高德地图坐标 (经度在前，维度在后)
     */
    @Column(name = "gaode_coordinate")
    private String gaodeCoordinate;
    /**
     * 状态
     * 0 正常  1 多个地址  2 无地址
     */
    @Column(name = "status")
    private Integer status;
    /**
     * 来源   1 百度map、 2 高德map
     */
    @Column(name = "source")
    private Integer source;
    /**
     * 准确度
     * 0：确认  1可能对  2 不太靠谱
     */
    @Column(name = "accuracy")
    private Integer accuracy;
    /**
     * 查询条件    1 地址  2公司名称
     */
    @Column(name = "query_condition")
    private Integer queryCondition;
    /**
     * 查询条件值
     */
    @Column(name = "query_condition_value")
    private String queryConditionValue;
    @Column(name = "level")
    private String level;
    /**
     * 创建时间
     */
    private Date createTime;

    public String getEsBaiduCoordinate() {
        if (baiduCoordinate != null) {
            String[] array = baiduCoordinate.split(",");
            return array[1] + "," + array[0];
        }
        return null;
    }

    public String getEsGaoDeCoordinate() {
        if (gaodeCoordinate != null) {
            String[] array = gaodeCoordinate.split(",");
            return array[1] + "," + array[0];
        }
        return null;
    }

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


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
