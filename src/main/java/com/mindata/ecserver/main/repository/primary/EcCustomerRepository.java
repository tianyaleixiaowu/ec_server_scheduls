package com.mindata.ecserver.main.repository.primary;

import com.mindata.ecserver.main.model.primary.EcCustomer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/25.
 */
public interface EcCustomerRepository extends JpaRepository<EcCustomer, Long> {
    /**
     * 查找某个客户最新的状态
     *
     * @param crmId
     *         客户id
     * @param pageable
     *         分页
     * @return 客户状态
     */
    List<EcCustomer> findByCrmIdOrderByUpdateTimeDesc(Long crmId, Pageable pageable);

}
