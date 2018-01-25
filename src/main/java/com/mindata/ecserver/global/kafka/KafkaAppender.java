package com.mindata.ecserver.global.kafka;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.mindata.ecserver.ApplicationContextProvider;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * 日志
 * @author wuweifeng wrote on 2018/1/25.
 */
public class KafkaAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    @Override
    protected void append(ILoggingEvent iLoggingEvent) {
        KafkaTemplate kafkaTemplate = ApplicationContextProvider.getBean(KafkaTemplate.class);
        if (kafkaTemplate != null) {
            kafkaTemplate.send("topic_ec_server_scheduls", iLoggingEvent.toString());
        }
    }
}
