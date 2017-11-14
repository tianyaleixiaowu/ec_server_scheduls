package com.mindata.ecserver.main.service;

import com.mindata.ecserver.global.exception.EcException;
import com.mindata.ecserver.main.manager.EsVocationCodeManager;
import com.mindata.ecserver.main.manager.VocationCodeManager;
import com.mindata.ecserver.main.model.es.EsVocationCode;
import com.mindata.ecserver.main.model.primary.EcVocationCodeEntity;
import com.mindata.ecserver.main.vo.VocationCodeVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.mindata.ecserver.global.Constant.ES_INDEX_NAME;
import static com.mindata.ecserver.global.Constant.ES_TYPE_AREA;
import static com.mindata.ecserver.global.Constant.ES_VOCATION_TYPE_NAME;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

/**
 * @author HanLiqiang wrote on 2017/11/14.
 */
@Service
public class EsVocationCodeService {
    @Resource
    private VocationCodeManager vocationCodeManager;
    @Resource
    private EsVocationCodeManager esVocationCodeManager;
    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 将所有数据插入到ES
     */
    @PostConstruct
    public void totalInsert() {
        if (elasticsearchTemplate.typeExists(ES_INDEX_NAME, ES_VOCATION_TYPE_NAME)) {
            return;
        }
        logger.info("开始往ES导入行业信息");
        List<EcVocationCodeEntity> codeEntityList = vocationCodeManager.findVocationCode();
        if (codeEntityList.size() == 0) {
            throw new EcException("没有数据");
        }
        esVocationCodeManager.bulkIndex(codeEntityList.stream().map(this::convert).collect(Collectors.toList()));
    }

    private EsVocationCode convert(EcVocationCodeEntity ecVocationCodeEntity) {
        EsVocationCode esVocationCode = new EsVocationCode();
        BeanUtils.copyProperties(ecVocationCodeEntity, esVocationCode);
        return esVocationCode;
    }

    public VocationCodeVo queryVocationName(String vocationName) {
        VocationCodeVo vo = esVocationCodeManager.findByVocationName(vocationName);
        return vo;
    }
}
