package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtPhoneHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
public interface PtPhoneHistoryRepository extends JpaRepository<PtPhoneHistory, Long> {

    /**
     * 查询某个客户的累计沟通时长
     *
     * @param crmId
     *         客户id
     * @return 总时长
     */
    @Query("select  sum(callTime) from PtPhoneHistory where crmId = ?1 and (startTime between ?2 and ?3)")
    Integer findTotalContactTimeByCrmId(Long crmId, Date begin, Date end);
}
