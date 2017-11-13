package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.es.EsContact;
import com.mindata.ecserver.main.repository.es.EsContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.mindata.ecserver.global.Constant.ES_INDEX_NAME;
import static com.mindata.ecserver.global.Constant.ES_TYPE_NAME;

/**
 * elasticsearch的管理类
 * @author wuweifeng wrote on 2017/11/9.
 */
@Service
public class EsContactManager {
    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;
    @Resource
    private EsContactRepository esContactRepository;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 分页查询es中的数据
     */
    public List<EsContact> find(Pageable pageable) {
        Page<EsContact> page = esContactRepository.findAll(pageable);
        return page.getContent();
    }

    /**
     * 从es中找到最新的一条，然后对比数据库看看是不是最新
     */
    public EsContact findTheLastEsContact() {
        logger.info("查询ES中最新的一条数据");
        //如果ES还未初始化
        if (!elasticsearchTemplate.indexExists(ES_INDEX_NAME)) {
            elasticsearchTemplate.createIndex(ES_INDEX_NAME);
            return null;
        }
        Pageable pageable = new PageRequest(0, 1, Sort.Direction.DESC, "id");
        List<EsContact> esContactList = find(pageable);
        if (esContactList.size() == 0) {
            logger.info("ES中没有数据");
            return null;
        }
        logger.info("ES中最新的一条数据为:" + esContactList.get(0));
        return esContactList.get(0);
    }

    public void bulkIndex(List<EsContact> contacts) {
        int counter = 1;
        try {
            List<IndexQuery> queries = new ArrayList<>();
                for (EsContact contact : contacts) {
                    IndexQuery indexQuery = new IndexQuery();
                    indexQuery.setId(contact.getId() + "");
                    indexQuery.setObject(contact);
                    indexQuery.setIndexName(ES_INDEX_NAME);
                    indexQuery.setType(ES_TYPE_NAME);

                //上面的那几步也可以使用IndexQueryBuilder来构建
                //IndexQuery index = new IndexQueryBuilder().withId(person.getId() + "").withObject(person).build();

                queries.add(indexQuery);
                if (counter % 500 == 0) {
                    elasticsearchTemplate.bulkIndex(queries);
                    queries.clear();
                    System.out.println("bulkIndex counter : " + counter);
                }
                counter++;
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
}
