package com.mindata.ecserver.main.model.es;

import com.mindata.ecserver.global.Constant;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.Date;

import static com.mindata.ecserver.global.Constant.ES_INDEX_NAME;

/**
 * @author wuweifeng wrote on 2017/11/8.
 */
@Document(indexName = ES_INDEX_NAME, type = Constant.ES_TYPE_NAME, indexStoreType = "fs", shards = 5, replicas = 1,
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
     * 省
     */
    @Field(index = FieldIndex.not_analyzed)
    private String province;
    /**
     * 市
     */
    @Field(index = FieldIndex.not_analyzed)
    private String city;
    /**
     * 来源（58、桔子）
     */
    @Field(index = FieldIndex.not_analyzed)
    private Integer websiteId;
    /**
     * 人员数量
     */
    @Field(index = FieldIndex.not_analyzed)
    private Integer memberSizeTag;
    /**
     * 行业编码
     */
    private Integer vocation;
    /**
     * 行业信息
     */
    private String industry;
    /**
     * 该记录在数据库的创建日期
     */
    //@Field(type = FieldType.Date)
    private Date createTime;
    /**
     * 插入ES库的日期
     */
    @Field(
            type = FieldType.Date,
            index = FieldIndex.not_analyzed,
            store = true,
            format = DateFormat.custom, pattern = "dd-MM-yyyy hh:mm:ss"
    )
    private Long insertTime;

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

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getWebsiteId() {
        return websiteId;
    }

    public void setWebsiteId(Integer websiteId) {
        this.websiteId = websiteId;
    }

    public Integer getMemberSizeTag() {
        return memberSizeTag;
    }

    public void setMemberSizeTag(Integer memberSizeTag) {
        this.memberSizeTag = memberSizeTag;
    }

    public Integer getVocation() {
        return vocation;
    }

    public void setVocation(Integer vocation) {
        this.vocation = vocation;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Long insertTime) {
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

