package com.mindata.ecserver.schedel;

import com.mindata.ecserver.global.ZkConstant;
import com.mindata.ecserver.global.annotation.EnableZookeeperLockAnnotation;
import com.mindata.ecserver.main.service.FetchCompanyPhoneHistoryService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 每天晚上2点获取昨天的通话历史
 * @author wuweifeng wrote on 2017/11/5.
 */
@Component
public class FetchPhoneHistorySchedule {
    @Resource
    private FetchCompanyPhoneHistoryService fetchCompanyPhoneHistoryService;

    /**
     * 注意分布式锁的问题
     */
    @Scheduled(cron = "0 0/20 6 * * ?")
    @EnableZookeeperLockAnnotation(nodeName = ZkConstant.PATH_FETCH_PHONEHISTORY)
    public void executeFetchPhoneHistoryTask() throws Exception {
        fetchCompanyPhoneHistoryService.fetch();
    }
}
