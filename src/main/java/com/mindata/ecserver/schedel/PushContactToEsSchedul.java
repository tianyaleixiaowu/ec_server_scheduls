package com.mindata.ecserver.schedel;

import com.mindata.ecserver.main.service.EsContactService;
import com.xiaoleilu.hutool.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;

import static com.xiaoleilu.hutool.date.DatePattern.NORM_DATETIME_FORMAT;

/**
 * 每天晚上3点读取db数据导入到ES
 * @author wuweifeng wrote on 2017/11/5.
 */
@Component
public class PushContactToEsSchedul {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private EsContactService esContactService;

    /**
     * 注意分布式锁的问题
     */
    @Scheduled(cron = "0 0 3 * * ?")
    public void executeFetchPhoneHistoryTask() throws IOException {
        //检查分布式锁
        System.out.println("定时任务开始");
        logger.info("现在时间：" + DateUtil.format(new Date(), NORM_DATETIME_FORMAT));
        logger.info("开始去获取线索信息导入到elasticsearch");
        esContactService.dbToEs();
    }
}
