package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.global.Constant;
import com.mindata.ecserver.main.model.es.EsVocationCode;
import com.mindata.ecserver.main.vo.VocationCodeVo;
import com.xiaoleilu.hutool.util.StrUtil;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.mindata.ecserver.global.Constant.ES_INDEX_NAME;
import static com.mindata.ecserver.global.Constant.ES_VOCATION_TYPE_NAME;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

@Service
public class EsVocationCodeManager {
    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;
//    @Resource
//    private EsVovcationCodeRepository esVovcationCodeRepository;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public void bulkIndex(List<EsVocationCode> vocationCodes) {
        try {
            List<IndexQuery> queries = new ArrayList<>();
            for (EsVocationCode vocationCode : vocationCodes) {
                IndexQuery indexQuery = new IndexQuery();
                indexQuery.setId(vocationCode.getVocationCode() + "");
                indexQuery.setObject(vocationCode);
                indexQuery.setIndexName(ES_INDEX_NAME);
                indexQuery.setType(ES_VOCATION_TYPE_NAME);
                queries.add(indexQuery);
            }
            if (queries.size() > 0) {
                elasticsearchTemplate.bulkIndex(queries);
            }
            System.out.println("vocationCode bulkIndex completed.");
        } catch (Exception e) {
            System.out.println("IndexerService.bulkIndex e;" + e.getMessage());
            throw e;
        }
    }
    public VocationCodeVo findByVocationName(String vocationName){
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder().withQuery(matchQuery("vocationName", vocationName));
        SearchQuery searchQuery = builder.build();
        List<EsVocationCode> esVocationCodes = elasticsearchTemplate.queryForList(searchQuery, EsVocationCode.class);
        VocationCodeVo vo = null;
        if(esVocationCodes.size()>0){
            EsVocationCode esVocationCode = esVocationCodes.get(0);
            vo = new VocationCodeVo();
            vo.setVocationCode(esVocationCode.getVocationCode());
        }else{
            vo = new VocationCodeVo();
            vo.setVocationCode(18);
        }
        return vo;
    }
}
