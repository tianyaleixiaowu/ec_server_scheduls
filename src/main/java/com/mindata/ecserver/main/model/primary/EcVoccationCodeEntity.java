package com.mindata.ecserver.main.model.primary;

import javax.persistence.*;

@Entity
@Table(name = "vocation_code")
public class EcVoccationCodeEntity {
    private Integer vocationCode;

    private int parentCode;

    private String vocationName;
    /**
     * 一级二级（1，2）
     */
    private int vocationGrade;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "vocationCode")
    public Integer getVocationCode() {
        return vocationCode;
    }

    public void setVocationCode(Integer vocationCode) {
        this.vocationCode = vocationCode;
    }

    public int getParentCode() {
        return parentCode;
    }

    public void setParentCode(int parentCode) {
        this.parentCode = parentCode;
    }

    public String getVocationName() {
        return vocationName;
    }

    public void setVocationName(String vocationName) {
        this.vocationName = vocationName;
    }

    public int getVocationGrade() {
        return vocationGrade;
    }

    public void setVocationGrade(int vocationGrade) {
        this.vocationGrade = vocationGrade;
    }

    @Override
    public String toString() {
        return "EcVocationCodeEntity{" +
                "vocationCode=" + vocationCode +
                ", parentCode=" + parentCode +
                ", vocationName='" + vocationName + '\'' +
                ", vocationGrade=" + vocationGrade +
                '}';
    }
}
