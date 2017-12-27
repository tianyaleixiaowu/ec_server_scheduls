package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.repository.primary.EcBjmdOldDataRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2017/12/27.
 */
@Component
public class EcBjmdOldDataManager {
    @Resource
    private EcBjmdOldDataRepository ecBjmdOldDataRepository;

    public boolean existCrmId(Long crmId) {
        return ecBjmdOldDataRepository.exists(crmId);
    }
}
