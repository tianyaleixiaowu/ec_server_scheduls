package com.mindata.ecserver.main.model.thirdly;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author hanliqiang wrote on 2017/11/14
 */
@MappedSuperclass
public class BaseCompanyIndustryInfo {
    @Id
    private Integer id;

    @Column(name = "comp_id")
    private Long compId;

    private String industry;

    private String comintro;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }

    public String getComintro() {
        return comintro;
    }

    public void setComintro(String comintro) {
        this.comintro = comintro;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }
}
