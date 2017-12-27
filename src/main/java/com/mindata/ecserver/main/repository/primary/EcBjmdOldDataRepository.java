package com.mindata.ecserver.main.repository.primary;

import com.mindata.ecserver.main.model.primary.EcBjmdOldData;
import com.mindata.ecserver.main.model.primary.EcContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author wuweifeng wrote on 2017/10/25.
 */
public interface EcBjmdOldDataRepository extends JpaRepository<EcBjmdOldData, Long>,
        JpaSpecificationExecutor<EcContactEntity> {

}
