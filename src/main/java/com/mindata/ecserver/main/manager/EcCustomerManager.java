package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.primary.EcCustomer;
import com.mindata.ecserver.main.repository.primary.EcCustomerRepository;
import com.xiaoleilu.hutool.util.CollectionUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wuweifeng wrote on 2017/12/27.
 */
@Component
public class EcCustomerManager {
    @Resource
    private EcCustomerRepository ecCustomerRepository;

    public Integer statusCode(Long crmId) {
        Pageable pageable = new PageRequest(0, 1);
        List<EcCustomer> customerList = ecCustomerRepository.findByCrmIdOrderByUpdateTimeDesc(crmId, pageable);
        if (CollectionUtil.isEmpty(customerList)) {
            return 0;
        }
        return customerList.get(0).getStatusCode();
    }
}
