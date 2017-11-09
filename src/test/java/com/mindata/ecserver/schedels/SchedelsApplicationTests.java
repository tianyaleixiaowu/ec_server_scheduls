package com.mindata.ecserver.schedels;

import com.mindata.ecserver.main.service.EsContactService;
import com.mindata.ecserver.main.service.FetchCompanyPhoneHistoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
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

	@Test
	public void contextLoads() throws IOException {
		//System.out.println(fetchCompanyPhoneHistoryService.fetch());
		esContactService.dbToEs();
	}

}
