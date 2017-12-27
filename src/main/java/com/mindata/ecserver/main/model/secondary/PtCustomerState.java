package com.mindata.ecserver.main.model.secondary;

import com.mindata.ecserver.main.model.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author wuweifeng wrote on 2017/11/1.
 * 客户跟踪结果
 */
@Entity
@Table(name = "pt_customer_operation",
        indexes = {@Index(name = "create_time", columnList = "createTime"),
                @Index(name = "crm_id", columnList = "crmId"),
                @Index(name = "customer_operation_id", columnList = "customerOperationId")})
public class PtCustomerState extends BaseEntity {

    /**
     * 在EC的客户id
     */
    private Long crmId;
    /**
     *（1新入库，2感兴趣，3拜访、远程，4需求匹配，5合作成交，6 已放弃）
     */
    private Integer statusCode;
    /**
     *  销售状态（0其他，1有意向，2已成交）
     */
    private Integer saleState;
    /**
     * 跟进人
     */
    private String followUser;
    /**
     * 最后操作时间
     */
    private Date operateTime;
    /**
     * 操作类型（1新增客户，2更新客户，3转让客户，4拨打对方电话，5拜访客户，6放弃客户，共41种）
     */
    private String operateType;
    /**
     * 客户来源，1来自麦达技术，2来自市场，3其他
     */
    private Integer sourceFrom;
    /**
     * 是否是老数据
     */
    private Boolean oldData;
    /**
     * customerOperation表的Id
     */
    private Long customerOperationId;
    /**
     * 入库时间
     */
    private Date createTime;

    public Long getCrmId() {
        return crmId;
    }

    public void setCrmId(Long crmId) {
        this.crmId = crmId;
    }

    public Boolean getOldData() {
        return oldData;
    }

    public void setOldData(Boolean oldData) {
        this.oldData = oldData;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Long getCustomerOperationId() {
        return customerOperationId;
    }

    public void setCustomerOperationId(Long customerOperationId) {
        this.customerOperationId = customerOperationId;
    }

    public Integer getSaleState() {
        return saleState;
    }

    public void setSaleState(Integer saleState) {
        this.saleState = saleState;
    }

    public String getFollowUser() {
        return followUser;
    }

    public void setFollowUser(String followUser) {
        this.followUser = followUser;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public Integer getSourceFrom() {
        return sourceFrom;
    }

    public void setSourceFrom(Integer sourceFrom) {
        this.sourceFrom = sourceFrom;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
