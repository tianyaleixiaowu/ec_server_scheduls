package com.mindata.ecserver.main.model.es;

import com.mindata.ecserver.global.Constant;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import static com.mindata.ecserver.global.Constant.ES_INDEX_NAME;

/**
 * @author wuweifeng wrote on 2017/11/13.
 * 区域标志
 */
@Document(indexName = ES_INDEX_NAME, type = Constant.ES_TYPE_AREA, indexStoreType = "fs", shards = 5, replicas = 1,
        refreshInterval = "-1")
public class EsCodeArea {
    @Id
    private Integer id;

    private String name;

    private String parentId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
