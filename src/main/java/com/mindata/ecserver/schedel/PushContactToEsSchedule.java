package com.mindata.ecserver.schedel;

import com.mindata.ecserver.global.ZkConstant;
import com.mindata.ecserver.global.annotation.EnableZookeeperLockAnnotation;
import com.mindata.ecserver.main.service.EsContactService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 每天晚上3点读取db数据导入到ES
 * @author wuweifeng wrote on 2017/11/5.
 */
@Component
public class PushContactToEsSchedule {
    @Resource
    private EsContactService esContactService;

    /**
     * 注意分布式锁的问题
     */
    @Scheduled(cron = "0 0/20 3 * * ?")
    @EnableZookeeperLockAnnotation(nodeName = ZkConstant.PATH_CONTACT_TO_ES)
    public void executePushDbToEsTask() throws Exception {
        esContactService.dbToEs(true);
    }
}
