package com.mindata.ecserver.main.repository.primary;

import com.mindata.ecserver.main.model.primary.EcCustomerOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wuweifeng wrote on 2017/10/25.
 */
public interface EcCustomerOperationRepository extends JpaRepository<EcCustomerOperation, Long> {
    /**
     * 查询id范围内的
     *
     * @param begin
     *         起始
     * @param end
     *         结束
     * @param pageable
     *         结束
     * @return 分页结果
     */
    Page<EcCustomerOperation> findByIdBetween(Long begin, Long end, Pageable pageable);

    Integer countByIdBetween(Long begin, Long end);

    EcCustomerOperation findFirstByOrderByIdAsc();

    EcCustomerOperation findFirstByOrderByIdDesc();
}
