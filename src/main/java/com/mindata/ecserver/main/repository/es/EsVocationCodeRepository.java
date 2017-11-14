package com.mindata.ecserver.main.repository.es;

import com.mindata.ecserver.main.model.es.EsVocationCode;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
/**
 * @author HanLiqiang wrote on 2017/11/14.
 */
public interface EsVocationCodeRepository extends ElasticsearchCrudRepository<EsVocationCode, Long> {
}
