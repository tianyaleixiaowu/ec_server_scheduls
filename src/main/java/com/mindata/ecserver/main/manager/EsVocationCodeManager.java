package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.es.EsVocationCode;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.mindata.ecserver.global.Constant.ES_INDEX_NAME;
import static com.mindata.ecserver.global.Constant.ES_VOCATION_TYPE_NAME;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

/**
 * @author HanLiqiang wrote on 2017/11/14.
 */
@Service
public class EsVocationCodeManager {
    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

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

    /**
     * 根据名字返回匹配的行业code值
     */
    public HashMap<String, Integer> findByVocationName(String vocationName) {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder().withQuery(matchQuery("vocationName", vocationName));
        SearchQuery searchQuery = builder.build();
        List<EsVocationCode> esVocationCodes = elasticsearchTemplate.queryForList(searchQuery, EsVocationCode.class);
        HashMap<String, Integer> map = new HashMap<>();
        if (esVocationCodes.size() > 0) {
            map.put("vocationCode", esVocationCodes.get(0).getVocationCode());
        } else {
            //如果没有匹配到 则返回其他(code值为18)
            map.put("vocationCode", 18);
        }
        return map;
    }
}
