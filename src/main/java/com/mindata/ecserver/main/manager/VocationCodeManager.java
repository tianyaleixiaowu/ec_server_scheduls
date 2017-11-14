package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.primary.EcVocationCodeEntity;
import com.mindata.ecserver.main.repository.primary.EcVocationCodeRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author HanLiqiang wrote on 2017/11/14.
 */
@Service
public class VocationCodeManager {
    @Resource
    private EcVocationCodeRepository ecVocationCodeRepository;

    public List<EcVocationCodeEntity> findVocationCode() {
        return ecVocationCodeRepository.findAll();
    }
}
