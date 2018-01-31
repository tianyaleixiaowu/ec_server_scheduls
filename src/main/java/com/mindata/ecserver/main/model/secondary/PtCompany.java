package com.mindata.ecserver.main.model.secondary;

import com.mindata.ecserver.main.model.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author wuweifeng wrote on 2017/10/25.
 * 平台添加的公司
 */
@Entity
@Table(name = "pt_company")
public class PtCompany extends BaseEntity {
    /**
     * 公司名称
     */
    private String name;
    /**
     * 公司状态（0正常，欠费等等）
     */
    private Integer status;
    /**
     * 购买状态(1初次购买，2已续费，3快过期，4已过期)
     */
    private Integer buyStatus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getBuyStatus() {
        return buyStatus;
    }

    public void setBuyStatus(Integer buyStatus) {
        this.buyStatus = buyStatus;
    }
}
