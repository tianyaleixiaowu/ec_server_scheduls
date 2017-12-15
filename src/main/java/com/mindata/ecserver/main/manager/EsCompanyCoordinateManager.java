package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.es.EsCompanyCoordinate;
import com.mindata.ecserver.main.model.secondary.PtCompanyCoordinate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import static com.mindata.ecserver.global.GeoConstant.PAGE_SIZE;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

/**
 * @author hanliqiang wrote on 2017/11/28
 */
@Service
public class EsCompanyCoordinateManager {
    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;
    @Resource
    private CompanyCoordinateManager companyCoordinateManager;
    @Resource
    private ContactManager contactManager;

    private Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * 根据contactId的范围，插入ES中相应的经纬度数据
     */
    public void bulkIndexCompany(Long beginId, Long endId, Long count, Boolean force) {
        if (count == null) {
            count = contactManager.countIdBetween(beginId, endId);
        }
        if (force == null) {
            force = false;
        }
        dealPartInsertES(beginId, endId, count, force);
    }

    /**
     * 每条线程处理的往ES插入数据的事
     * @param beginId
     * contactBeginId
     * @param endId
     * contactEndId
     * @param force
     * force
     */
    private void dealPartInsertES(Long beginId, Long endId, Long count, Boolean force) {
        logger.info("线程id为" + Thread.currentThread().getId() + "开始处理ES插入的事");
        for (int i = 0; i < count / PAGE_SIZE + 1; i++) {
            Pageable pageable = new PageRequest(i, PAGE_SIZE, Sort.Direction.ASC, "id");
            List<PtCompanyCoordinate> companyCoordinates = companyCoordinateManager.findByContactIdBetween(beginId, endId,
                    pageable);
            if (companyCoordinates.size() == 0) {
                continue;
            }
            bulkIndex(companyCoordinates.stream().map(this::convert).collect(Collectors.toList()), force);
        }
    }

    public void bulkIndexCompany(List<PtCompanyCoordinate> companyCoordinates, Boolean force) {
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
     * @param ptCompanyCoordinate 实体
     * @return 结果
     */
    private EsCompanyCoordinate convert(PtCompanyCoordinate ptCompanyCoordinate) {
        EsCompanyCoordinate esCompanyCoordinate = new EsCompanyCoordinate();
        BeanUtils.copyProperties(ptCompanyCoordinate, esCompanyCoordinate);
        esCompanyCoordinate.setGaodeCoordinate(ptCompanyCoordinate.getEsGaoDeCoordinate());
        esCompanyCoordinate.setBaiduCoordinate(ptCompanyCoordinate.getEsBaiduCoordinate());
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
