package com.mindata.ecserver.main.model.secondary;

import com.mindata.ecserver.main.model.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author wuweifeng wrote on 2017/10/25.
 * 菜单表
 */
@Entity
@Table(name = "pt_role")
public class PtRole extends BaseEntity {

    /**
     * 角色名（admin，manager，sale）
     */
    private String name;
    /**
     * 角色描述（超级管理员，管理员，部门经理）
     */
    private String sign;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
