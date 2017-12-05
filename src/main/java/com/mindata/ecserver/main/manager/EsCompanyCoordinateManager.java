package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.es.EsCompanyCoordinate;
import com.mindata.ecserver.main.model.secondary.CompanyCoordinateEntity;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.DeleteQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.mindata.ecserver.global.Constant.ES_COORDINATE_TYPE_NAME;
import static com.mindata.ecserver.global.Constant.ES_GEO_INDEX_NAME;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

/**
 * @author hanliqiang wrote on 2017/11/28
 */
@Service
public class EsCompanyCoordinateManager {
    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    public void bulkIndexCompany(List<CompanyCoordinateEntity> companyCoordinates, Boolean force) {
        bulkIndex(companyCoordinates.stream().map(this::convert).collect(Collectors.toList()), force);
    }

    private void bulkIndex(List<EsCompanyCoordinate> companyCoordinates, Boolean force) {
        if (!elasticsearchTemplate.indexExists(ES_GEO_INDEX_NAME)) {
            elasticsearchTemplate.createIndex(ES_GEO_INDEX_NAME);
        }
        try {
            List<IndexQuery> queries = new ArrayList<>();
            for (EsCompanyCoordinate companyCoordinate : companyCoordinates) {
                if (force) {
                    delete(companyCoordinate.getId());
                }
                IndexQuery indexQuery = new IndexQuery();
                indexQuery.setId(companyCoordinate.getId() + "");
                indexQuery.setObject(companyCoordinate);
                indexQuery.setIndexName(ES_GEO_INDEX_NAME);
                indexQuery.setType(ES_COORDINATE_TYPE_NAME);
                queries.add(indexQuery);
            }
            if (queries.size() > 0) {
                elasticsearchTemplate.bulkIndex(queries);
            }
            System.out.println("bulkIndex completed.");
        } catch (Exception e) {
            System.out.println("IndexerService.bulkIndex e;" + e.getMessage());
            throw e;
        }
    }

    /**
     * 根据contactId删除es里的数据
     *
     * @param contactId contactId
     */
    private void delete(Long contactId) {
        BoolQueryBuilder boolQuery = boolQuery();
        boolQuery.must(termQuery("contactId", contactId));
        DeleteQuery deleteQuery = new DeleteQuery();
        deleteQuery.setQuery(boolQuery);
        elasticsearchTemplate.delete(deleteQuery, EsCompanyCoordinate.class);
        System.out.println("删除contactId是" + contactId + "的数据");
    }

    /**
     * 转换为es实体
     *
     * @param companyCoordinateEntity 实体
     * @return 结果
     */
    private EsCompanyCoordinate convert(CompanyCoordinateEntity companyCoordinateEntity) {
        EsCompanyCoordinate esCompanyCoordinate = new EsCompanyCoordinate();
        BeanUtils.copyProperties(companyCoordinateEntity, esCompanyCoordinate);
        esCompanyCoordinate.setGaodeCoordinate(companyCoordinateEntity.getEsGaoDeCoordinate());
        esCompanyCoordinate.setBaiduCoordinate(companyCoordinateEntity.getEsBaiduCoordinate());
        return esCompanyCoordinate;
    }

    /**
     * 执行mapping
     */
    @PostConstruct
    public void executeMapping() {
        if (!elasticsearchTemplate.indexExists(ES_GEO_INDEX_NAME)) {
            elasticsearchTemplate.createIndex(ES_GEO_INDEX_NAME);
            elasticsearchTemplate.putMapping(EsCompanyCoordinate.class);
        }
    }
}
