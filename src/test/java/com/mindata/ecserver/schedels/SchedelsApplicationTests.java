package com.mindata.ecserver.schedels;

import com.mindata.ecserver.main.service.EsContactService;
import com.mindata.ecserver.main.service.EsVocationCodeService;
import com.mindata.ecserver.main.service.FetchCompanyPhoneHistoryService;
import com.mindata.ecserver.schedel.FetchCoordinateSchedule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SchedelsApplicationTests {
	@Resource
	private FetchCompanyPhoneHistoryService fetchCompanyPhoneHistoryService;
	@Resource
	private EsContactService esContactService;
	@Resource
	private ElasticsearchTemplate elasticsearchTemplate;
	@Resource
	private EsVocationCodeService esVocationCodeService;
	@Resource
	private FetchCoordinateSchedule fetchCoordinateSchedule;

	@Test
	public void contextLoads() throws Exception {
		//if (!elasticsearchTemplate.indexExists(ES_INDEX_NAME)) {
		//	elasticsearchTemplate.createIndex(ES_INDEX_NAME);
		//}
		//System.out.println(fetchCompanyPhoneHistoryService.fetch());
		//esContactService.dbToEs();
	}

}
