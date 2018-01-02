package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtPhoneHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
public interface PtPhoneHistoryRepository extends JpaRepository<PtPhoneHistory, Long> {

    /**
     * 查询某个客户通话大于60秒的数量，算做有意向
     *
     * @param crmId
     *         客户id
     * @return 数量>1算有意向
     */
    @Query("select count(id) from PtPhoneHistory where crmId = ?1 and callTime >= 120")
    Integer findIntentedCountByCrmId(Long crmId);
}