package com.mindata.ecserver.main.model.secondary;

import com.mindata.ecserver.main.model.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author wuweifeng wrote on 2017/10/25.
 * 公司单日通话历史统计表
 */
@Entity
@Table(name = "pt_phone_history_company", indexes = {@Index(name = "company_id", columnList =
        "companyId"), @Index(name = "start_time", columnList =
        "startTime")})
public class PtPhoneHistoryCompany extends BaseEntity{
    /**
     * 公司id
     */
    private Long companyId;
    /**
     * 打电话日期（精确到日）
     */
    private Date startTime;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
