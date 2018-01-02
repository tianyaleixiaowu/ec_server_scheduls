package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.repository.secondary.PtPhoneHistoryRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2017/12/28.
 */
@Component
public class PtPhoneHistoryManager {
    @Resource
    private PtPhoneHistoryRepository ptPhoneHistoryRepository;

    public Integer intentCountByCrmId(Long crmId) {
        return ptPhoneHistoryRepository.findIntentedCountByCrmId(crmId);
    }

    public boolean isIntent(Long crmId) {
        Integer integer = intentCountByCrmId(crmId);
        if (integer == null) {
            return false;
        }
        return integer >= 1;
    }
}
