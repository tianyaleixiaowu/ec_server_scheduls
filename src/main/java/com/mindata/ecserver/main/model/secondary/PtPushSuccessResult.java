package com.mindata.ecserver.main.model.secondary;

import com.mindata.ecserver.main.model.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * @author wuweifeng wrote on 2017/10/24.
 * 推送成功的表
 */
@Entity
@Table(name = "pt_push_success_result",
        indexes = {@Index(name = "create_time", columnList = "createTime"),
                @Index(name = "follow_user_id", columnList = "followUserId")})
public class PtPushSuccessResult extends BaseEntity {
    /**
     * 数据的id
     */
    private Long contactId;
    /**
     * 线索中的公司名
     */
    private String companyName;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 行业
     */
    private Integer vocation;
    /**
     * 沟通结果
     */
    private Integer saleState;
    /**
     * 省
     */
    private Integer province;
    /**
     * 市
     */
    private Integer city;
    /**
     * 来源（58、桔子）
     */
    private Integer websiteId;
    /**
     * 在EC的客户id
     */
    private Long crmId;
    /**
     * 跟进人的公司id
     */
    private Long companyId;
    /**
     * 操作人id
     */
    private Long optUserId;
    /**
     * 跟进人id
     */
    private Long followUserId;
    /**
     * 跟进人的部门id
     */
    private Long departmentId;

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getVocation() {
        return vocation;
    }

    public void setVocation(Integer vocation) {
        this.vocation = vocation;
    }

    public Integer getSaleState() {
        return saleState;
    }

    public void setSaleState(Integer saleState) {
        this.saleState = saleState;
    }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public Integer getWebsiteId() {
        return websiteId;
    }

    public void setWebsiteId(Integer websiteId) {
        this.websiteId = websiteId;
    }

    public Long getCrmId() {
        return crmId;
    }

    public void setCrmId(Long crmId) {
        this.crmId = crmId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getOptUserId() {
        return optUserId;
    }

    public void setOptUserId(Long optUserId) {
        this.optUserId = optUserId;
    }

    public Long getFollowUserId() {
        return followUserId;
    }

    public void setFollowUserId(Long followUserId) {
        this.followUserId = followUserId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public String toString() {
        return "PtPushSuccessResult{" +
                "contactId=" + contactId +
                ", companyName='" + companyName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", vocation=" + vocation +
                ", saleState=" + saleState +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", websiteId=" + websiteId +
                ", crmId=" + crmId +
                ", companyId=" + companyId +
                ", optUserId=" + optUserId +
                ", followUserId=" + followUserId +
                ", departmentId=" + departmentId +
                '}';
    }
}
