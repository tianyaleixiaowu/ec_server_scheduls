package com.mindata.ecserver.main.model.es;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

import static com.mindata.ecserver.global.Constant.ES_INDEX_NAME;

/**
 * @author wuweifeng wrote on 2017/11/8.
 */
@Document(indexName = ES_INDEX_NAME, type = "contact", indexStoreType = "fs", shards = 5, replicas = 1,
        refreshInterval = "-1")
public class EsContact {
    @Id
    private Long id;
    /**
     * 对应爬虫的公司id
     */
    private Long compId;
    /**
     * 人名
     */
    private String name;
    /**
     * 公司名
     */
    private String company;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 座机
     */
    private String phone;
    private String address;
    /**
     * 备注字段
     */
    private String memo;
    /**
     * 已备案，未备案
     */
    private String ipcFlag;
    /**
     * 推送的状态（0未推送，1成功，2失败）
     */
    private Integer state;
    /**
     * 主要招聘岗位
     */
    private String mainJob;
    /**
     * 招聘岗位名称
     */
    private String jobName;
    /**
     * 福利 （5险1金）
     */
    private String welfare;
    /**
     * 职位描述
     */
    private String posDes;
    /**
     * 公司简介
     */
    private String comintro;
    /**
     * 该记录在数据库的创建日期
     */
    @Field(type = FieldType.Date)
    private Date createTime;
    /**
     * 插入ES库的日期
     */
    @Field(type = FieldType.Date)
    private Date insertTime;

    public String getMainJob() {
        return mainJob;
    }

    public void setMainJob(String mainJob) {
        this.mainJob = mainJob;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpcFlag() {
        return ipcFlag;
    }

    public void setIpcFlag(String ipcFlag) {
        this.ipcFlag = ipcFlag;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }


    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

