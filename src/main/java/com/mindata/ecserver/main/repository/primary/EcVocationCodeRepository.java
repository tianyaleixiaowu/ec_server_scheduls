package com.mindata.ecserver.main.repository.primary;

import com.mindata.ecserver.main.model.primary.EcVoccationCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface EcVocationCodeRepository extends JpaRepository<EcVoccationCodeEntity, Integer>,
        JpaSpecificationExecutor<EcVoccationCodeEntity> {

}
