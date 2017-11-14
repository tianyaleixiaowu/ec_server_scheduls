package com.mindata.ecserver.main.service;

import com.mindata.ecserver.main.manager.EsVocationCodeManager;
import com.mindata.ecserver.main.manager.VocationCodeManager;
import com.mindata.ecserver.main.model.es.EsVocationCode;
import com.mindata.ecserver.main.model.primary.EcVocationCodeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.mindata.ecserver.global.Constant.ES_INDEX_NAME;
import static com.mindata.ecserver.global.Constant.ES_VOCATION_TYPE_NAME;

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
            logger.error("没有数据");
            return;
        }
        esVocationCodeManager.bulkIndex(codeEntityList.stream().map(this::convert).collect(Collectors.toList()));
    }

    private EsVocationCode convert(EcVocationCodeEntity ecVocationCodeEntity) {
        EsVocationCode esVocationCode = new EsVocationCode();
        BeanUtils.copyProperties(ecVocationCodeEntity, esVocationCode);
        return esVocationCode;
    }

    public HashMap<String, Integer> queryCodeByVocationName(String vocationName) {
        return esVocationCodeManager.findByVocationName(vocationName);
    }
}
