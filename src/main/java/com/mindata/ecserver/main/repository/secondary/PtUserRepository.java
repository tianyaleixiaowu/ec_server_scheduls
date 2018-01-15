package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
public interface PtUserRepository extends JpaRepository<PtUser, Long> {
    PtUser findFirstByCompanyId(Long companyId);

}
