package com.mindata.ecserver.schedel;

import com.mindata.ecserver.global.ZkConstant;
import com.mindata.ecserver.global.annotation.EnableZookeeperLockAnnotation;
import com.mindata.ecserver.main.service.CompanyCoordinateService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 每天晚上2点读取nopush表数据的经纬度
 * @author wuweifeng wrote on 2017/11/5.
 */
@Component
public class FetchCoordinateSchedule {
    @Resource
    private CompanyCoordinateService companyCoordinateService;

    /**
     * 注意分布式锁的问题
     */
    @Scheduled(cron = "0 0/20 2 * * ?")
    @EnableZookeeperLockAnnotation(nodeName = ZkConstant.PATH_FETCH_COORDINATE)
    public void executeFetchCoordinateTask() throws Exception {
        companyCoordinateService.completeRemainCompanyCoordinate(false);
    }
}
