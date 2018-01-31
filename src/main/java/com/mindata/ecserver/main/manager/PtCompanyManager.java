package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.secondary.PtCompany;
import com.mindata.ecserver.main.repository.secondary.PtCompanyRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wuweifeng wrote on 2018/1/31.
 */
@Component
public class PtCompanyManager {
    @Resource
    private PtCompanyRepository ptCompanyRepository;


    public List<PtCompany> findAllNormal() {
        return ptCompanyRepository.findByBuyStatusNot(4);
    }

}
