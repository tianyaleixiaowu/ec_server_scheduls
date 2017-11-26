package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtPhoneHistoryCompany;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
public interface PtPhoneHistoryCompanyRepository extends JpaRepository<PtPhoneHistoryCompany, Integer> {

    /**
     * 倒序查询公司通话历史记录
     * @param companyId
     * 公司id
     * @param pageable
     * 分页
     * @return
     * 结果集
     */
    List<PtPhoneHistoryCompany> findByCompanyIdOrderByStartTimeDesc(Long companyId, Pageable pageable);
}
