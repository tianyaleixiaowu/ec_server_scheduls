package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.es.EsCompanyCoordinate;
import com.mindata.ecserver.main.model.secondary.CompanyCoordinateEntity;
import com.xiaoleilu.hutool.util.StrUtil;
import org.springframework.beans.BeanUtils;
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
        IndexQuery indexQuery = new IndexQuery();
        indexQuery.setId(companyCoordinate.getId() + "");
        indexQuery.setObject(companyCoordinate);
        indexQuery.setIndexName(ES_INDEX_NAME);
        indexQuery.setType(ES_COORDINATE_TYPE_NAME);
        elasticsearchTemplate.index(indexQuery);
    }
    @SuppressWarnings("all")
    public void dbToEs(CompanyCoordinateEntity coordinateEntity){
        EsCompanyCoordinate esCompanyCoordinate = new EsCompanyCoordinate();
        BeanUtils.copyProperties(coordinateEntity, esCompanyCoordinate);
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer buffer = new StringBuffer();
        //es插值将经纬度转换位置
        if (StrUtil.isNotEmpty(coordinateEntity.getBaiduCoordinate())) {
            String[] baiduCoordinateArr = coordinateEntity.getBaiduCoordinate().split(",");
            stringBuffer.append(baiduCoordinateArr[1] + ",").append(baiduCoordinateArr[0]);
            esCompanyCoordinate.setBaiduCoordinate(stringBuffer.toString());
            if (StrUtil.isNotEmpty(coordinateEntity.getGaodeCoordinate())) {
                String[] gaodeCoordinateArr = coordinateEntity.getGaodeCoordinate().split(",");
                buffer.append(gaodeCoordinateArr[1] + ",").append(gaodeCoordinateArr[0]);
                esCompanyCoordinate.setGaodeCoordinate(buffer.toString());
            }
            this.index(esCompanyCoordinate);
        } else {
            if (StrUtil.isNotEmpty(coordinateEntity.getGaodeCoordinate())) {
                String[] gaodeCoordinateArr = coordinateEntity.getGaodeCoordinate().split(",");
                buffer.append(gaodeCoordinateArr[1] + ",").append(gaodeCoordinateArr[0]);
                esCompanyCoordinate.setGaodeCoordinate(buffer.toString());
            }
            this.index(esCompanyCoordinate);
        }

    }
}
