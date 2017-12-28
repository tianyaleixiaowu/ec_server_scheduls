package com.mindata.ecserver.schedel;

import com.mindata.ecserver.global.ZkConstant;
import com.mindata.ecserver.global.annotation.EnableZookeeperLockAnnotation;
import com.mindata.ecserver.main.service.PtCustomerStateService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 每天8点处理customer表数据
 * @author wuweifeng wrote on 2017/11/5.
 */
@Component
public class DealCustomerSchedule {
    @Resource
    private PtCustomerStateService ptCustomerStateService;

    /**
     * 注意分布式锁的问题
     */
    @Scheduled(cron = "0 0/20 8 * * ?")
    @EnableZookeeperLockAnnotation(nodeName = ZkConstant.PATH_DEAL_CUSTOMER)
    public void executeDealCustomerOperationTask() {
        ptCustomerStateService.dealCustomerState();
    }
}
