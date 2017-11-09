package com.mindata.ecserver.main.model.thirdly;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @author wuweifeng wrote on 2017/10/25.
 */
@MappedSuperclass
public class BaseCompanyJobInfo {
    @Id
    private Long id;
    /**
     * 公司id
     */
    private Long compId;
    /**
     * 招聘岗位名称
     */
    private String jobName;
    /**
     * 月薪
     */
    private String salary;
    /**
     * 工作地点
     */
    private String workAddr;
    /**
     * 发布时间（11-02发布）
     */
    private String publishTime;
    /**
     * 要求经验
     */
    private String jobExperience;
    /**
     * 学历（本科）
     */
    private String degree;
    /**
     * 招聘人数（8人，若干）
     */
    private String requireNum;
    /**
     * 福利 （5险1金）
     */
    private String welfare;
    /**
     * 职位描述
     */
    @Column(name = "posDes")
    private String posDes;
    /**
     * 公司简介
     */
    private String comintro;
    /**
     * 更新时间
     */
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getWorkAddr() {
        return workAddr;
    }

    public void setWorkAddr(String workAddr) {
        this.workAddr = workAddr;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getJobExperience() {
        return jobExperience;
    }

    public void setJobExperience(String jobExperience) {
        this.jobExperience = jobExperience;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getRequireNum() {
        return requireNum;
    }

    public void setRequireNum(String requireNum) {
        this.requireNum = requireNum;
    }

    public String getWelfare() {
        return welfare;
    }

    public void setWelfare(String welfare) {
        this.welfare = welfare;
    }

    public String getPosDes() {
        return posDes;
    }

    public void setPosDes(String posDes) {
        this.posDes = posDes;
    }

    public String getComintro() {
        return comintro;
    }

    public void setComintro(String comintro) {
        this.comintro = comintro;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
