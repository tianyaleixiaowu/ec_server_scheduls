package com.mindata.ecserver.schedels;

import com.mindata.ecserver.main.service.EsContactService;
import com.mindata.ecserver.main.service.EsVocationCodeService;
import com.mindata.ecserver.main.service.FetchCompanyPhoneHistoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;

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

	@Test
	public void contextLoads() throws IOException {
		//if (elasticsearchTemplate.indexExists(ES_INDEX_NAME)) {
		//	elasticsearchTemplate.deleteIndex(ES_INDEX_NAME);
		//}
		//System.out.println(fetchCompanyPhoneHistoryService.fetch());
		esContactService.dbToEs();
	}
	@Test
	public void totalInsert(){
		esVocationCodeService.totalInsert();
	}
	@Test
	public void test(){
		StringBuilder industry = new StringBuilder();
		industry.append("a");
		System.out.println(industry.toString().contains(null));
	}

}
