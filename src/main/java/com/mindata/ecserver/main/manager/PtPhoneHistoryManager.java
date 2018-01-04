package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.repository.secondary.PtPhoneHistoryRepository;
import com.xiaoleilu.hutool.date.DateUtil;
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

    public Integer intentCountByCrmId(Long crmId, Date begin, Date end) {
        return ptPhoneHistoryRepository.findIntentedCountByCrmId(crmId, begin, end);
    }

    public boolean isIntent(Long crmId, Date operateTime) {
        Date begin = DateUtil.beginOfMonth(operateTime);
        Date end = DateUtil.endOfMonth(operateTime);

        Integer integer = intentCountByCrmId(crmId, begin, end);
        return integer != null && integer >= 1;
    }
}
