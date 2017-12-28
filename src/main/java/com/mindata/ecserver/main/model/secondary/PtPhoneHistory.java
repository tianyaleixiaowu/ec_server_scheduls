package com.mindata.ecserver.main.model.secondary;

import com.mindata.ecserver.main.model.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author wuweifeng wrote on 2017/10/25.
 * 通话历史表
 */
@Entity
@Table(name = "pt_phone_history", indexes = {@Index(name = "ec_user_id", columnList =
        "ecUserId"), @Index(name = "start_time", columnList =
        "startTime"), @Index(name = "crm_id", columnList =
        "crmId")})
public class PtPhoneHistory extends BaseEntity {
    /**
     * ecUserId
     */
    private Long ecUserId;
    /**
     * 开始打电话的时间
     */
    private Date startTime;
    /**
     * 电话号码，被拨打的电话号码
     */
    private String callToNo;
    /**
     * 客户id
     */
    private Long crmId;
    /**
     * 通话时长
     */
    private Integer callTime;
    /**
     * 通话方式：
     * 1 电话
     * 2 传真
     * 3 电话会议
     * 4 盒子呼入
     */
    private Integer type;
    /**
     * 录音地址
     */
    private String path;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 客户公司
     */
    private String customerCompany;
    /**
     * md5
     */
    private String md5;
    /**
     * 是真实打电话的，还是为空时我插的值
     */
    private Boolean realRecode;

    public Long getEcUserId() {
        return ecUserId;
    }

    public void setEcUserId(Long ecUserId) {
        this.ecUserId = ecUserId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getCallToNo() {
        return callToNo;
    }

    public void setCallToNo(String callToNo) {
        this.callToNo = callToNo;
    }

    public Long getCrmId() {
        return crmId;
    }

    public void setCrmId(Long crmId) {
        this.crmId = crmId;
    }

    public Integer getCallTime() {
        return callTime;
    }

    public void setCallTime(Integer callTime) {
        this.callTime = callTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getRealRecode() {
        return realRecode;
    }

    public void setRealRecode(Boolean realRecode) {
        this.realRecode = realRecode;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerCompany() {
        return customerCompany;
    }

    public void setCustomerCompany(String customerCompany) {
        this.customerCompany = customerCompany;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
