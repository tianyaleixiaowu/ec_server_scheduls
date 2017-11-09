package com.mindata.ecserver.main.model.primary;

import javax.persistence.*;
import java.util.Date;

/**
 * @author wuweifeng wrote on 2017/10/24.
 * 爬虫爬取的企业信息
 */
@Entity
@Table(name = "ec_contact_no_push")
public class EcContactEntity {
    private Long id;
    /**
     * 人名
     */
    private String name;
    /**
     * 公司名
     */
    private String company;
    /**
     * 是否是企业法人
     */
    private Boolean legal;
    /**
     * 客户性别0无/1/男/2女
     */
    private Integer gender;
    private String mobile;
    private String phone;
    private String email;
    private String fax;
    private String province;
    private String city;
    private String title;
    private String qq;
    private String wechat;
    private String address;

    /**
     * 行业编码
     */
    private Integer vocation;
    /**
     * 官网
     */
    private String url;
    /**
     * 备注字段
     */
    private String memo;
    /**
     * 行业标签
     */
    private Integer vocationTag;
    /**
     * 人员数量
     */
    private Integer memberSizeTag;
    /**
     * 是否招聘销售
     */
    private Boolean needSale;
    /**
     * 来源（58、桔子）
     */
    private Integer websiteId;
    /**
     * 推送的状态（0未推送，1成功，2失败）
     */
    private Integer state;
    /**
     * 对应爬虫的公司id
     */
    private Long compId;
    /**
     * ipc备案
     */
    private String ipcFlag;
    /**
     * 主要招聘岗位
     */
    private String mainJob;

    private Date createTime;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ipc_flag")
    public String getIpcFlag() {
        return ipcFlag;
    }

    public void setIpcFlag(String ipcFlag) {
        this.ipcFlag = ipcFlag;
    }

    @Basic
    @Column(name = "main_job")
    public String getMainJob() {
        return mainJob;
    }

    public void setMainJob(String mainJob) {
        this.mainJob = mainJob;
    }

    @Basic
    @Column(name = "comp_id")
    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "company")
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Basic
    @Column(name = "legal")
    public Boolean getLegal() {
        return legal;
    }

    public void setLegal(Boolean legal) {
        this.legal = legal;
    }

    @Basic
    @Column(name = "gender")
    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "fax")
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Basic
    @Column(name = "province")
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Basic
    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "qq")
    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    @Basic
    @Column(name = "wechat")
    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "vocation")
    public Integer getVocation() {
        return vocation;
    }

    public void setVocation(Integer vocation) {
        this.vocation = vocation;
    }

    @Basic
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "memo")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Basic
    @Column(name = "vocation_tag")
    public Integer getVocationTag() {
        return vocationTag;
    }

    public void setVocationTag(Integer vocationTag) {
        this.vocationTag = vocationTag;
    }

    @Basic
    @Column(name = "member_size_tag")
    public Integer getMemberSizeTag() {
        return memberSizeTag;
    }

    public void setMemberSizeTag(Integer memberSizeTag) {
        this.memberSizeTag = memberSizeTag;
    }

    @Basic
    @Column(name = "need_sale")
    public Boolean getNeedSale() {
        return needSale;
    }

    public void setNeedSale(Boolean needSale) {
        this.needSale = needSale;
    }

    @Basic
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "website_id")
    public Integer getWebsiteId() {
        return websiteId;
    }

    public void setWebsiteId(Integer websiteId) {
        this.websiteId = websiteId;
    }

    @Basic
    @Column(name = "state")
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
