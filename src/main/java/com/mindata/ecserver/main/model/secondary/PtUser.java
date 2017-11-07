package com.mindata.ecserver.main.model.secondary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mindata.ecserver.main.model.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * @author wuweifeng wrote on 2017/10/25.
 * 平台用户
 */
@Entity
@Table(name = "pt_user", indexes = {@Index(name = "company_id", columnList =
        "companyId"), @Index(name = "department_id", columnList =
        "departmentId"), @Index(name = "account", columnList =
        "account")})
public class PtUser extends BaseEntity {
    /**
     * 用户名称
     */
    private String name;
    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    @JsonIgnore
    private String password;
    /**
     * 公司ID
     */
    private Integer companyId;
    /**
     * 部门ID
     */
    private Integer departmentId;
    /**
     * 称号
     */
    private String title;
    /**
     * 工号
     */
    private String jobNumber;
    /**
     * 在EC的userId
     */
    private Long ecUserId;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 备注
     */
    private String memo;
    /**
     * 状态，（0正常，1被删除）
     */
    private Integer state;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getEcUserId() {
        return ecUserId;
    }

    public void setEcUserId(Long ecUserId) {
        this.ecUserId = ecUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
