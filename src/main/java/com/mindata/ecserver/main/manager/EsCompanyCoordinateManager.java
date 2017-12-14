package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.es.EsCompanyCoordinate;
import com.mindata.ecserver.main.model.secondary.CompanyCoordinateEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.DeleteQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.mindata.ecserver.global.Constant.ES_COORDINATE_TYPE_NAME;
import static com.mindata.ecserver.global.Constant.ES_GEO_INDEX_NAME;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

/**
 * @author hanliqiang wrote on 2017/11/28
 */
@Service
public class EsCompanyCoordinateManager {
    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public void bulkIndexCompany(List<CompanyCoordinateEntity> companyCoordinates, Boolean force) {
        bulkIndex(companyCoordinates.stream().map(this::convert).collect(Collectors.toList()), force);
    }

    private void bulkIndex(List<EsCompanyCoordinate> companyCoordinates, Boolean force) {
        try {
            List<IndexQuery> queries = new ArrayList<>();
            if (force) {
                companyCoordinates.forEach(esCompanyCoordinate -> delete(esCompanyCoordinate.getContactId()));
            }
            for (EsCompanyCoordinate companyCoordinate : companyCoordinates) {
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
            logger.info("bulkIndex completed.");
        } catch (Exception e) {
            logger.info("IndexerService.bulkIndex e;" + e.getMessage());
            throw e;
        }
    }

    /**
     * 根据contactId删除es里的数据
     *
     * @param contactId contactId
     */
    private void delete(Long contactId) {
        DeleteQuery deleteQuery = new DeleteQuery();
        deleteQuery.setQuery(termQuery("contactId", contactId));
        elasticsearchTemplate.delete(deleteQuery, EsCompanyCoordinate.class);
        logger.info("删除contactId是" + contactId + "的经纬度数据");
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
            Map<String, Object> map = new HashMap<>(2);
            //如果不设置，翻页到1千时会报错，后期修改命令：curl -XPUT http://127.0.0.1:9200/cmdb-now/_settings -d '{ "index" : {
            // "max_result_window" : 100000000}}'
            map.put("index.max_result_window", 1000000);
            elasticsearchTemplate.createIndex(ES_GEO_INDEX_NAME, map);
            elasticsearchTemplate.putMapping(EsCompanyCoordinate.class);
        }
    }
}
