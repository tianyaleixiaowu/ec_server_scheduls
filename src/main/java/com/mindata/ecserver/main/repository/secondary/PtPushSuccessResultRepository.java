package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtPushSuccessResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
public interface PtPushSuccessResultRepository extends JpaRepository<PtPushSuccessResult, Long>,
        JpaSpecificationExecutor<PtPushSuccessResult> {


    /**
     * 根据crmId查询结果
     *
     * @param crmId
     *         crmId
     * @return 结果
     */
    List<PtPushSuccessResult> findByCrmId(Long crmId);
}
