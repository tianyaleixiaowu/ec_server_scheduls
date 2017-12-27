package com.mindata.ecserver.main.model.primary;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author wuweifeng wrote on 2017/11/27.
 */
@Entity
@Table(name = "ec_customer")
public class EcCustomer {
    /**
     * 客户id
     */
    private Long crmId;
    /**
     * 操作类型
     */
    private Integer statusCode;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * ecUserId
     */
    private Long createUserId;

    @Id
    @Column(name = "crm_id")
    public Long getCrmId() {
        return crmId;
    }

    public void setCrmId(Long crmId) {
        this.crmId = crmId;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }
}
