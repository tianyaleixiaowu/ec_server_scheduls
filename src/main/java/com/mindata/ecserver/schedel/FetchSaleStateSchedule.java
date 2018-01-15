package com.mindata.ecserver.schedel;

import com.mindata.ecserver.global.ZkConstant;
import com.mindata.ecserver.global.annotation.EnableZookeeperLockAnnotation;
import com.mindata.ecserver.main.service.FetchCustomerSaleStateService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 每天晚上获取昨天的销售统计
 * @author wuweifeng wrote on 2018/1/15.
 */
@Component
public class FetchSaleStateSchedule {
    @Resource
    private FetchCustomerSaleStateService fetchCustomerSaleStateService;

    /**
     * 注意分布式锁的问题
     */
    @Scheduled(cron = "0 0/20 1 * * ?")
    @EnableZookeeperLockAnnotation(nodeName = ZkConstant.PATH_SALE_STATE)
    public void executeFetchPhoneHistoryTask() throws Exception {
        fetchCustomerSaleStateService.fetch();
    }
}
