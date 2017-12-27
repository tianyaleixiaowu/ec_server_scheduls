package com.mindata.ecserver.main.model.primary;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author wuweifeng wrote on 2017/11/27.
 * 客户状态
 */
@Entity
@Table(name = "ec_customer_operation")
public class EcCustomerOperation {
    private Long id;
    /**
     * 客户id
     */
    private Long crmId;
    /**
     * 操作类型（新增客户，更新客户）
     */
    private String operateType;
    /**
     * 内容
     */
    private String content;
    /**
     * 更新时间
     */
    private Date operateTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 操作人
     */
    private String operateUser;

    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "crm_id")
    public Long getCrmId() {
        return crmId;
    }

    public void setCrmId(Long crmId) {
        this.crmId = crmId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getOperateUser() {
        return operateUser;
    }

    public void setOperateUser(String operateUser) {
        this.operateUser = operateUser;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }
}
