package com.mindata.ecserver.global.kafka;

import com.mindata.ecserver.main.manager.PtCustomerStateManager;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.mindata.ecserver.global.KafkaConstant.CUSTOMER_TOPIC;

/**
 * @author wuweifeng wrote on 2018/1/4.
 */
@Component
@ConditionalOnProperty(value = "open.eureka")
public class MessageListener {
    @Resource
    private PtCustomerStateManager ptCustomerStateManager;

    @KafkaListener(topics = CUSTOMER_TOPIC)
    public void listenCustomer(ConsumerRecord<?, ?> cr) {
        //value就是ec_customer_operation的主键id
        String value = (String) cr.value();
        //值是这样的：23411,1或者4343,0 逗号后面的1代表强制，0代表不强制
        ptCustomerStateManager.dealOperationData(Long.valueOf(value.split(",")[0]), Integer.valueOf(value.split("," +
                "")[1]) == 1);
    }
}
