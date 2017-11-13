package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.primary.EcVoccationCodeEntity;
import com.mindata.ecserver.main.repository.primary.EcVocationCodeRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class VocationCodeManager {
    @Resource
    private EcVocationCodeRepository ecVocationCodeRepository;

    public List<EcVoccationCodeEntity> findVocationCode() {
        return ecVocationCodeRepository.findAll();
    }
}
