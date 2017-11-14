package com.mindata.ecserver.main.repository.primary;

import com.mindata.ecserver.main.model.primary.EcVocationCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author HanLiqiang wrote on 2017/11/14.
 */
public interface EcVocationCodeRepository extends JpaRepository<EcVocationCodeEntity, Integer>,
        JpaSpecificationExecutor<EcVocationCodeEntity> {

}
