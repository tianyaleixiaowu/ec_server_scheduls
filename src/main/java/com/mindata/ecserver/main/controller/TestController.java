package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.global.coordinate.service.CoordinateService;
import com.mindata.ecserver.global.coordinate.service.impl.BaiduMapServiceImpl;
import com.mindata.ecserver.global.http.response.BaseData;
import com.mindata.ecserver.main.service.CompanyCoordinateService;
import com.mindata.ecserver.main.service.EsContactService;
import com.mindata.ecserver.main.service.FetchCompanyPhoneHistoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author wuweifeng wrote on 2017/11/7.
 */
@RestController
public class TestController {
    @Resource
    private FetchCompanyPhoneHistoryService fetchCompanyPhoneHistoryService;
    @Resource
    private EsContactService esContactService;
    @Resource
    private CoordinateService coordinateService;
    @Resource
    private CompanyCoordinateService companyCoordinateService;

    @GetMapping("/fetch")
    public BaseData fetchCompanyHistory() throws IOException {
        return fetchCompanyPhoneHistoryService.fetch();
    }

    @GetMapping("/push")
    public Object pushDbToEs() {
        esContactService.dbToEs(false);
        return "dbToEs";
    }

    @GetMapping("/push/between")
    public Object pushIdBetweenDbToEs(Long beginId, Long endId) {
        esContactService.partInsertBetween(beginId, endId);
        return "between dbToEs";
    }

    @GetMapping("/force")
    public Object forceDbToEs() {
        esContactService.forceTotal();
        return "force dbToEs";
    }

    @GetMapping("/baidu")
    public String baidu(Boolean force) throws IOException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        companyCoordinateService.completeAllCompanyCoordinate(force);
        return "baidu";
    }
    @GetMapping("/baidu2")
    public String baidu2() throws IOException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        coordinateService.getLocation("北京市丰台区南四环西路188号十八区15号楼1至3层101(园区)","asd","北京");
        return "baidu2";
    }
}
