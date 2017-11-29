package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.es.EsCompanyCoordinate;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.mindata.ecserver.global.Constant.ES_COORDINATE_TYPE_NAME;
import static com.mindata.ecserver.global.Constant.ES_INDEX_NAME;

/**
 * @author hanliqiang wrote on 2017/11/28
 */
@Service
public class EsCompanyCoordinateManager {
    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    public void index(EsCompanyCoordinate companyCoordinate) {
        try {
            IndexQuery indexQuery = new IndexQuery();
            indexQuery.setId(companyCoordinate.getId() + "");
            indexQuery.setObject(companyCoordinate);
            indexQuery.setIndexName(ES_INDEX_NAME);
            indexQuery.setType(ES_COORDINATE_TYPE_NAME);
            elasticsearchTemplate.index(indexQuery);
        } catch (Exception e) {
            throw e;
        }
    }
}
