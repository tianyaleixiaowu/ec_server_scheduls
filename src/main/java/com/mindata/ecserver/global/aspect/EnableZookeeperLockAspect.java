package com.mindata.ecserver.global.aspect;

import com.mindata.ecserver.global.annotation.EnableZookeeperLockAnnotation;
import com.mindata.ecserver.global.util.CommonUtil;
import com.xiaoleilu.hutool.date.DateUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.xiaoleilu.hutool.date.DatePattern.NORM_DATETIME_FORMAT;

/**
 * @author wuweifeng wrote on 2017/12/28.
 */
@Component
@Aspect
@ConditionalOnProperty(value = "open.eureka")
public class EnableZookeeperLockAspect {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private CuratorFramework client;

    @Around("@annotation(enableZookeeperLockAnnotation)")
    public Object around(ProceedingJoinPoint pjp, EnableZookeeperLockAnnotation enableZookeeperLockAnnotation) throws Throwable {
        //如果没启用，就什么也不干
        if (!enableZookeeperLockAnnotation.enable()) {
            return pjp.proceed();
        }
        //检查分布式锁
        InterProcessMutex interProcessMutex = new InterProcessMutex(client, enableZookeeperLockAnnotation.nodeName());
        //只等1毫秒，目的是不管多少docker，只要有一个执行了就OK了，其他的不需要执行
        if (!interProcessMutex.acquire(500L, TimeUnit.MILLISECONDS)) {
            logger.info("主机名为" + CommonUtil.getHostName() + "没取到锁");
            return null;
        }
        logger.info("主机名为" + CommonUtil.getHostName() + "取到锁");
        logger.info("现在时间：" + DateUtil.format(new Date(), NORM_DATETIME_FORMAT));
        logger.info("开始执行 : " + pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName());
        Object object = pjp.proceed();
        //线程等待1秒，让锁持有的时间长一些
        Thread.sleep(1000);
        //释放锁
        interProcessMutex.release();
        return object;
    }
}
