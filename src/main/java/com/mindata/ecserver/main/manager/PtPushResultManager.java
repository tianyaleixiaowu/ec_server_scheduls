package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.repository.secondary.PtPushSuccessResultRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2017/12/27.
 */
@Component
public class PtPushResultManager {
    @Resource
    private PtPushSuccessResultRepository ptPushSuccessResultRepository;
    /**
     * 查看该crmId是否是我们推送的
     *
     * @param crmId
     *         crmId
     * @return 结果
     */
    public boolean existCrmId(Long crmId) {
        return ptPushSuccessResultRepository.findByCrmId(crmId).size() > 0;
    }
}
