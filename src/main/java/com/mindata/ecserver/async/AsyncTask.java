package com.mindata.ecserver.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * @author wuweifeng wrote on 2017/12/14.
 */
@Component
public class AsyncTask {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Async("mySimpleAsync")
    public void doTask(Consumer<?> consumer) {
        logger.info("线程id为" + Thread.currentThread().getId() + " started");
        consumer.accept(null);
    }
}
