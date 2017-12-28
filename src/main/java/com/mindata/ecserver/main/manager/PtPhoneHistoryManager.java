package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.repository.secondary.PtPhoneHistoryRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author wuweifeng wrote on 2017/12/28.
 */
@Component
public class PtPhoneHistoryManager {
    @Resource
    private PtPhoneHistoryRepository ptPhoneHistoryRepository;

    public Integer totalContactTimeBetween(Long crmId, Date begin, Date end) {
        return ptPhoneHistoryRepository.findTotalContactTimeByCrmId(crmId, begin, end);
    }

    public boolean isIntented(Long crmId, Date begin, Date end) {
        Integer integer = totalContactTimeBetween(crmId, begin, end);
        if (integer == null) {
            return false;
        }
        return integer >= 60;
    }
}
