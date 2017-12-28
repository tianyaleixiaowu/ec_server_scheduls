package com.mindata.ecserver.schedel;

import com.mindata.ecserver.global.ZkConstant;
import com.mindata.ecserver.global.util.CommonUtil;
import com.mindata.ecserver.main.service.PtCustomerStateService;
import com.xiaoleilu.hutool.date.DateUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.xiaoleilu.hutool.date.DatePattern.NORM_DATETIME_FORMAT;

/**
 * 每天8点处理customer表数据
 * @author wuweifeng wrote on 2017/11/5.
 */
@Component
public class DealCustomerSchedule {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private PtCustomerStateService ptCustomerStateService;
    @Resource
    private CuratorFramework client;

    /**
     * 注意分布式锁的问题
     */
    @Scheduled(cron = "0 0/20 8 * * ?")
    public void executePushDbToEsTask() throws Exception {
        //检查分布式锁
        InterProcessMutex interProcessMutex = new InterProcessMutex(client, ZkConstant.PATH_DEAL_CUSTOMER);
        //只等1毫秒，目的是不管多少docker，只要有一个执行了就OK了，其他的不需要执行
        if (!interProcessMutex.acquire(1L, TimeUnit.MILLISECONDS)) {
             logger.info("主机名为" + CommonUtil.getHostName() + "没取到锁");
             return;
        }
        logger.info("主机名为" + CommonUtil.getHostName() + "取到锁，开始处理customer表数据");
        logger.info("现在时间：" + DateUtil.format(new Date(), NORM_DATETIME_FORMAT));
        logger.info("开始去处理customer数据");
        ptCustomerStateService.dealCustomerState();
        interProcessMutex.release();
    }
}
