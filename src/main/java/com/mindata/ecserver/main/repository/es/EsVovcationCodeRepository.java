package com.mindata.ecserver.main.repository.es;

import com.mindata.ecserver.main.model.es.EsVocationCode;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

public interface EsVovcationCodeRepository extends ElasticsearchCrudRepository<EsVocationCode, Long> {
}
