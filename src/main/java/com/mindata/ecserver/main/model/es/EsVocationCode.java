package com.mindata.ecserver.main.model.es;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import static com.mindata.ecserver.global.Constant.ES_INDEX_NAME;
import static com.mindata.ecserver.global.Constant.ES_VOCATION_TYPE_NAME;
/**
 * @author HanLiqiang wrote on 2017/11/14.
 */
@Document(indexName = ES_INDEX_NAME, type = ES_VOCATION_TYPE_NAME, indexStoreType = "fs", shards = 5, replicas = 1,
        refreshInterval = "-1")
public class EsVocationCode {

    @Id
    private Integer vocationCode;

    private Integer parentCode;

    private String vocationName;

    private Integer vocationGrade;


    public Integer getVocationCode() {
        return vocationCode;
    }

    public void setVocationCode(Integer vocationCode) {
        this.vocationCode = vocationCode;
    }

    public Integer getParentCode() {
        return parentCode;
    }

    public void setParentCode(Integer parentCode) {
        this.parentCode = parentCode;
    }

    public String getVocationName() {
        return vocationName;
    }

    public void setVocationName(String vocationName) {
        this.vocationName = vocationName;
    }

    public Integer getVocationGrade() {
        return vocationGrade;
    }

    public void setVocationGrade(Integer vocationGrade) {
        this.vocationGrade = vocationGrade;
    }
}
