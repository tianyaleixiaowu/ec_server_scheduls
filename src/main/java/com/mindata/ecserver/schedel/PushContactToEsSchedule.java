package com.mindata.ecserver.schedel;

import com.mindata.ecserver.global.ZkConstant;
import com.mindata.ecserver.global.util.CommonUtil;
import com.mindata.ecserver.main.service.EsContactService;
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
 * 每天晚上3点读取db数据导入到ES
 * @author wuweifeng wrote on 2017/11/5.
 */
@Component
public class PushContactToEsSchedule {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private EsContactService esContactService;
    @Resource
    private CuratorFramework client;

    /**
     * 注意分布式锁的问题
     */
    @Scheduled(cron = "0 0/20 3 * * ?")
    public void executePushDbToEsTask() throws Exception {
        InterProcessMutex interProcessMutex = new InterProcessMutex(client, ZkConstant.PATH_FETCH_COORDINATE);
        //只等1毫秒，目的是不管多少docker，只要有一个执行了就OK了，其他的不需要执行
        if (!interProcessMutex.acquire(1L, TimeUnit.MILLISECONDS)) {
            logger.info("主机名为" + CommonUtil.getHostName() + "没取到锁");
            return;
        }
        logger.info("主机名为" + CommonUtil.getHostName() + "取到锁，开始读取db数据导入到ES");
        logger.info("现在时间：" + DateUtil.format(new Date(), NORM_DATETIME_FORMAT));
        logger.info("开始去获取线索信息导入到elasticsearch");
        esContactService.dbToEs(true);
        interProcessMutex.release();
    }
}
