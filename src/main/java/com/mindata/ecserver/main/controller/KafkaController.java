package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.main.manager.EcCustomerOperationManager;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.mindata.ecserver.global.KafkaConstant.CUSTOMER_TOPIC;

/**
 * @author wuweifeng wrote on 2018/1/4.
 */
@RestController
@RequestMapping("/kafka")
public class KafkaController {
    @Resource
    private EcCustomerOperationManager ecCustomerOperationManager;
    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    @RequestMapping("/customer")
    public Object dealCustomer(Long beginId, Long endId, Boolean force) {
        if (beginId == null) {
            beginId = ecCustomerOperationManager.findFirstOperationId();
        }
        if (endId == null) {
            endId = ecCustomerOperationManager.findLastOperationId();
        }
        int forceCode = 0;
        if (force != null && force) {
            forceCode = 1;
        }
        for (Long i = beginId; i < endId; i++) {
            kafkaTemplate.send(CUSTOMER_TOPIC, i + "," + forceCode);
        }
        return "customer id信息导入到kafka完毕，id范围是" + beginId + "---" + endId;
    }
}
