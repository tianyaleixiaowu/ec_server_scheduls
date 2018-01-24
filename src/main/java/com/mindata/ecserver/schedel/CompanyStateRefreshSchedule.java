package com.mindata.ecserver.schedel;

import com.mindata.ecserver.global.ZkConstant;
import com.mindata.ecserver.global.annotation.EnableZookeeperLockAnnotation;
import com.mindata.ecserver.main.service.CompanyStateRefreshService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 每天刷新Company的status
 * @author wuweifeng wrote on 2018/1/15.
 */
@Component
public class CompanyStateRefreshSchedule {
    @Resource
    private CompanyStateRefreshService companyStateRefreshService;

    /**
     * 注意分布式锁的问题
     */
    @Scheduled(cron = "0 0/20 0 * * ?")
    @EnableZookeeperLockAnnotation(nodeName = ZkConstant.PATH_COMPANY_STATE)
    public void executeFetchPhoneHistoryTask() throws Exception {
        companyStateRefreshService.refresh();
    }
}
