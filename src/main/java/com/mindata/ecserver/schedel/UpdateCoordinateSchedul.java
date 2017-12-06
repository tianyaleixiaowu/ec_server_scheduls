package com.mindata.ecserver.schedel;

import com.mindata.ecserver.main.service.CompanyCoordinateService;
import com.xiaoleilu.hutool.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import static com.xiaoleilu.hutool.date.DatePattern.NORM_DATETIME_FORMAT;

/**
 * @author hanliqiang wrote on 2017/11/30
 */
@Component
public class UpdateCoordinateSchedul {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private CompanyCoordinateService coordinateService;

    /**
     * 定时修补位置信息
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void executeUpdateCoordinateTask() throws IOException, NoSuchAlgorithmException {
        //检查分布式锁
        System.out.println("定时任务开始");
        logger.info("现在时间：" + DateUtil.format(new Date(), NORM_DATETIME_FORMAT));
        logger.info("开始更新坐标数据库信息");
        coordinateService.timingUpdateCoordinate();
    }
}
