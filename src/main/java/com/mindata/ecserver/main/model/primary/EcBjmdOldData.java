package com.mindata.ecserver.main.model.primary;

import javax.persistence.*;

/**
 * 麦达老数据
 * @author wuweifeng wrote on 2017/12/27.
 */
@Entity
@Table(name = "ec_bjmd_olddata",
        indexes = {@Index(name = "crm_id", columnList = "crm_id")})
public class EcBjmdOldData {
    private Long crmId;

    @Id
    @Column(name = "crm_id")
    public Long getCrmId() {
        return crmId;
    }

    public void setCrmId(Long crmId) {
        this.crmId = crmId;
    }
}
