package com.mindata.ecserver.main.model.primary;

import javax.persistence.*;
import java.util.Date;

/**
 * @author hanliqiang wrote on 2017/12/20
 */
@Entity
@Table(name = "qichacha_company")
public class CompanyQichacha {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "qichacha_id")
    private String qichachaId;
    @Column(name = "key_word")
    private String keyWord;
    /**
     * 公司名称
     */
    @Column(name = "company_name")
    private String companyName;
    /**
     * 电话
     */
    @Column(name = "company_telephone")
    private String companyTelephone;
    /**
     * 邮箱
     */
    @Column(name = "company_email")
    private String companyEmail;
    @Column(name = "company_domain")
    private String companyDomain;
    /**
     * 浏览
     */
    @Column(name = "company_browse")
    private Integer companyBrowse;
    /**
     * 关注
     */
    @Column(name = "company_watch")
    private Integer companyWatch;
    /**
     * 地址
     */
    @Column(name = "company_address")
    private String companyAddress;
    /**
     * 状态
     */
    @Column(name = "company_status")
    private String companyStatus;
    /**
     * 统一社会信用代码
     */
    @Column(name = "com_credit_code")
    private String comCreditCode;
    /**
     * 纳税人识别号
     */
    @Column(name = "com_tax_id")
    private String comTaxId;
    /**
     * 法定代表人
     */
    @Column(name = "com_legal_person")
    private String comLegalPerson;
    /**
     * 注册号
     */
    @Column(name = "com_regist_id")
    private String comRegistId;
    /**
     * 注册资本
     */
    @Column(name = "com_regist_cap")
    private String comRegistCap;
    /**
     * 组织机构代码
     */
    @Column(name = "com_company_code")
    private String comcompanyCode;
    /**
     * 公司类型
     */
    @Column(name = "com_company_type")
    private String comCompanyType;
    /**
     * 所属地区
     */
    @Column(name = "com_area")
    private String comArea;
    /**
     * 英文名
     */
    @Column(name = "com_english_name")
    private String comEnglishName;
    /**
     * 所属行业
     */
    @Column(name = "com_profession")
    private String comProfession;
    /**
     * 成立日期
     */
    @Column(name = "com_found_date")
    private String comFoundDate;
    /**
     * 营业期限
     */
    @Column(name = "com_oper_time")
    private String comOperTime;
    /**
     * 经营范围
     */
    @Column(name = "com_range")
    private String comRange;
    /**
     * 登记机关
     */
    @Column(name = "com_regist_authority")
    private String comRegistAuthority;
    /**
     * 核准日期
     */
    @Column(name = "com_check_date")
    private String comCheckDate;
    /**
     * 人员规模
     */
    @Column(name = "com_scale")
    private String comScale;
    /**
     * 经营状态
     */
    @Column(name = "com_status")
    private String comStatus;
    /**
     * 曾用名
     */
    @Column(name = "com_used_name")
    private String comUsedName;
    /**
     * 企业地址
     */
    @Column(name = "com_address")
    private String comAddress;
    /**
     * 描述
     */
    @Column(name = "company_desc")
    private String companyDesc;
    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQichachaId() {
        return qichachaId;
    }

    public void setQichachaId(String qichachaId) {
        this.qichachaId = qichachaId;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyTelephone() {
        return companyTelephone;
    }

    public void setCompanyTelephone(String companyTelephone) {
        this.companyTelephone = companyTelephone;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getCompanyDomain() {
        return companyDomain;
    }

    public void setCompanyDomain(String companyDomain) {
        this.companyDomain = companyDomain;
    }

    public Integer getCompanyBrowse() {
        return companyBrowse;
    }

    public void setCompanyBrowse(Integer companyBrowse) {
        this.companyBrowse = companyBrowse;
    }

    public Integer getCompanyWatch() {
        return companyWatch;
    }

    public void setCompanyWatch(Integer companyWatch) {
        this.companyWatch = companyWatch;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyStatus() {
        return companyStatus;
    }

    public void setCompanyStatus(String companyStatus) {
        this.companyStatus = companyStatus;
    }

    public String getComCreditCode() {
        return comCreditCode;
    }

    public void setComCreditCode(String comCreditCode) {
        this.comCreditCode = comCreditCode;
    }

    public String getComTaxId() {
        return comTaxId;
    }

    public void setComTaxId(String comTaxId) {
        this.comTaxId = comTaxId;
    }

    public String getComLegalPerson() {
        return comLegalPerson;
    }

    public void setComLegalPerson(String comLegalPerson) {
        this.comLegalPerson = comLegalPerson;
    }

    public String getComRegistId() {
        return comRegistId;
    }

    public void setComRegistId(String comRegistId) {
        this.comRegistId = comRegistId;
    }

    public String getComRegistCap() {
        return comRegistCap;
    }

    public void setComRegistCap(String comRegistCap) {
        this.comRegistCap = comRegistCap;
    }

    public String getComcompanyCode() {
        return comcompanyCode;
    }

    public void setComcompanyCode(String comcompanyCode) {
        this.comcompanyCode = comcompanyCode;
    }

    public String getComCompanyType() {
        return comCompanyType;
    }

    public void setComCompanyType(String comCompanyType) {
        this.comCompanyType = comCompanyType;
    }

    public String getComArea() {
        return comArea;
    }

    public void setComArea(String comArea) {
        this.comArea = comArea;
    }

    public String getComEnglishName() {
        return comEnglishName;
    }

    public void setComEnglishName(String comEnglishName) {
        this.comEnglishName = comEnglishName;
    }

    public String getComProfession() {
        return comProfession;
    }

    public void setComProfession(String comProfession) {
        this.comProfession = comProfession;
    }

    public String getComFoundDate() {
        return comFoundDate;
    }

    public void setComFoundDate(String comFoundDate) {
        this.comFoundDate = comFoundDate;
    }

    public String getComOperTime() {
        return comOperTime;
    }

    public void setComOperTime(String comOperTime) {
        this.comOperTime = comOperTime;
    }

    public String getComRange() {
        return comRange;
    }

    public void setComRange(String comRange) {
        this.comRange = comRange;
    }

    public String getComRegistAuthority() {
        return comRegistAuthority;
    }

    public void setComRegistAuthority(String comRegistAuthority) {
        this.comRegistAuthority = comRegistAuthority;
    }

    public String getComCheckDate() {
        return comCheckDate;
    }

    public void setComCheckDate(String comCheckDate) {
        this.comCheckDate = comCheckDate;
    }

    public String getComScale() {
        return comScale;
    }

    public void setComScale(String comScale) {
        this.comScale = comScale;
    }

    public String getComStatus() {
        return comStatus;
    }

    public void setComStatus(String comStatus) {
        this.comStatus = comStatus;
    }

    public String getComUsedName() {
        return comUsedName;
    }

    public void setComUsedName(String comUsedName) {
        this.comUsedName = comUsedName;
    }

    public String getComAddress() {
        return comAddress;
    }

    public void setComAddress(String comAddress) {
        this.comAddress = comAddress;
    }

    public String getCompanyDesc() {
        return companyDesc;
    }

    public void setCompanyDesc(String companyDesc) {
        this.companyDesc = companyDesc;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
