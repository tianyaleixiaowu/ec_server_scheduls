package com.mindata.ecserver.main.model.thirdly;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author wuweifeng wrote on 2017/11/8.
 */
@Entity
@Table(name = "company_code")
public class CompanyCode {
    @Id
    private Long compId;
    private String compName;

    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }
}
