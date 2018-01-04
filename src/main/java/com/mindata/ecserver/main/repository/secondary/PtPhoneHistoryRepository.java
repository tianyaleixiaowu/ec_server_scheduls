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
     * 查询某个客户通话大于60秒的数量，算做有意向
     *
     * @param crmId
     *         客户id
     * @return 数量>1算有意向
     */
    @Query("select count(id) from PtPhoneHistory where crmId = ?1 and callTime >= 60 and createTime > ?2 and " +
            "createTime < ?3")
    Integer findIntentedCountByCrmId(Long crmId, Date begin, Date end);
}
