package com.mindata.ecserver.main.repository.es;

import com.mindata.ecserver.main.model.es.EsContact;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

/**
 * @author wuweifeng wrote on 2017/11/9.
 */
public interface EsContactRepository extends ElasticsearchCrudRepository<EsContact, Long> {
}
