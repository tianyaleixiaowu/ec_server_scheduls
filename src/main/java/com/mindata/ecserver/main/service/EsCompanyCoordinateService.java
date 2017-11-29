package com.mindata.ecserver.main.service;

import com.mindata.ecserver.main.manager.EsCompanyCoordinateManager;
import com.mindata.ecserver.main.model.es.EsCompanyCoordinate;
import com.mindata.ecserver.main.model.primary.CompanyCoordinateEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author hanliqiang wrote on 2017/11/28
 */
@Service
public class EsCompanyCoordinateService {
    @Resource
    private EsCompanyCoordinateManager esCompanyCoordinateManager;
    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public void insert(CompanyCoordinateEntity coordinateEntity) {
//        if (elasticsearchTemplate.typeExists(ES_INDEX_NAME, ES_COORDINATE_TYPE_NAME)) {
//            return;
//        }
        logger.info("开始往ES导入公司坐标信息");
        esCompanyCoordinateManager.index(this.convert(coordinateEntity));
    }


    private EsCompanyCoordinate convert(CompanyCoordinateEntity companyCoordinateEntity) {
        EsCompanyCoordinate esCompanyCoordinate = new EsCompanyCoordinate();
        BeanUtils.copyProperties(companyCoordinateEntity, esCompanyCoordinate);
        return esCompanyCoordinate;
    }

}
