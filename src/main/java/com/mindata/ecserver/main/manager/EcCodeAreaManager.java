package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.es.EsCodeArea;
import com.mindata.ecserver.main.model.primary.CodeAreaEntity;
import com.mindata.ecserver.main.repository.primary.CodeAreaRepository;
import com.xiaoleilu.hutool.util.StrUtil;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.mindata.ecserver.global.Constant.ES_INDEX_NAME;
import static com.mindata.ecserver.global.Constant.ES_TYPE_AREA;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
@Service
public class EcCodeAreaManager {
    @Resource
    private CodeAreaRepository codeAreaRepository;
    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    private final Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 只截取地址的前10位
     */
    private final static int CUT_LENGTH = 10;
    private final static String PROVINCE = "province";
    private final static String CITY = "city";

    @PostConstruct
    public void areaToEs() {
        if (!elasticsearchTemplate.indexExists(ES_INDEX_NAME)) {
            elasticsearchTemplate.createIndex(ES_INDEX_NAME);
        }
        if (elasticsearchTemplate.typeExists(ES_INDEX_NAME, ES_TYPE_AREA)) {
            return;
        }
        logger.info("开始往ES导入区域信息");
        List<CodeAreaEntity> areaEntityList = codeAreaRepository.findAll();

        int counter = 1;
        try {
            List<IndexQuery> queries = new ArrayList<>();
            for (CodeAreaEntity area : areaEntityList) {
                IndexQuery indexQuery = new IndexQuery();
                indexQuery.setId(area.getId() + "");
                indexQuery.setObject(area);
                indexQuery.setIndexName(ES_INDEX_NAME);
                indexQuery.setType(ES_TYPE_AREA);

                queries.add(indexQuery);
                if (counter % 500 == 0) {
                    elasticsearchTemplate.bulkIndex(queries);
                    queries.clear();
                    System.out.println("area bulkIndex counter : " + counter);
                }
                counter++;
            }
            if (queries.size() > 0) {
                elasticsearchTemplate.bulkIndex(queries);
            }
            System.out.println("area bulkIndex completed.");
        } catch (Exception e) {
            System.out.println("IndexerService.bulkIndex e;" + e.getMessage());
            throw e;
        }
    }

    /**
     * 根据字符串匹配查询省市
     *
     * @return 省id
     */
    public HashMap<String, Integer> findAreaCode(String area) {
        if (area.length() > CUT_LENGTH) {
            area = area.substring(0, 10);
        }
        HashMap<String, Integer> hashMap = new HashMap<>(2);
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        Integer provinceCode = findProvince(area);
        //没找到省，直接去匹配市，找到了，就定下省，再去找市
        if (provinceCode != null) {
            boolQueryBuilder.must(matchQuery("parentId", provinceCode));
            hashMap.put(PROVINCE, provinceCode);
        }
        
        boolQueryBuilder.must(matchQuery("name", area));

        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder);
        List<EsCodeArea> codeAreas = elasticsearchTemplate.queryForList(builder.build(), EsCodeArea.class);
        if (codeAreas.size() > 0) {
            EsCodeArea codeArea = codeAreas.get(0);
            //如果是省，那么市就留空即可
            if (StrUtil.equals(codeArea.getParentId(), "0")) {
                hashMap.put(PROVINCE, codeArea.getId());
                hashMap.put(CITY, 0);
            } else {
                //如果匹配到了市或者县
                hashMap.put(CITY, codeArea.getId());
                //譬如110101
                hashMap.put(PROVINCE, codeArea.getId() / 10000 * 10000);
            }
        } else {
            hashMap.putIfAbsent(PROVINCE, 0);
            hashMap.putIfAbsent(CITY, 0);
        }
        return hashMap;
    }

    /**
     * 获取所有的省
     *
     * @return
     * 根据区域查询省
     */
    private Integer findProvince(String area) {
        if (area.contains("北京")) {
            return 110000;
        } else if (area.contains("天津")) {
            return 120000;
        } else if (area.contains("河北")) {
            return 130000;
        } else if (area.contains("山西")) {
            return 140000;
        } else if (area.contains("内蒙古")) {
            return 150000;
        } else if (area.contains("辽宁")) {
            return 210000;
        } else if (area.contains("吉林")) {
            return 220000;
        } else if (area.contains("黑龙江")) {
            return 230000;
        } else if (area.contains("上海")) {
            return 310000;
        } else if (area.contains("江苏")) {
            return 320000;
        } else if (area.contains("浙江")) {
            return 330000;
        } else if (area.contains("安徽")) {
            return 340000;
        } else if (area.contains("福建")) {
            return 350000;
        } else if (area.contains("江西")) {
            return 360000;
        } else if (area.contains("山东")) {
            return 370000;
        } else if (area.contains("河南")) {
            return 410000;
        } else if (area.contains("湖北")) {
            return 420000;
        } else if (area.contains("湖南")) {
            return 430000;
        } else if (area.contains("广东")) {
            return 440000;
        } else if (area.contains("广西")) {
            return 450000;
        } else if (area.contains("海南")) {
            return 460000;
        } else if (area.contains("重庆")) {
            return 500000;
        } else if (area.contains("四川")) {
            return 510000;
        } else if (area.contains("贵州")) {
            return 520000;
        } else if (area.contains("云南")) {
            return 530000;
        } else if (area.contains("西藏")) {
            return 540000;
        } else if (area.contains("陕西")) {
            return 610000;
        } else if (area.contains("甘肃")) {
            return 620000;
        } else if (area.contains("青海")) {
            return 630000;
        } else if (area.contains("宁夏")) {
            return 640000;
        } else if (area.contains("新疆")) {
            return 650000;
        } else if (area.contains("台湾")) {
            return 710000;
        } else if (area.contains("香港")) {
            return 810000;
        } else if (area.contains("澳门")) {
            return 820000;
        }

        return null;
    }

}
