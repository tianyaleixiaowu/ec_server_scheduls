package com.mindata.ecserver.main.repository.primary;

import com.mindata.ecserver.main.model.primary.CompanyCoordinateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author hanliqiang wrote on 2017/11/26
 */
public interface CompanyCoordinateRepository extends JpaRepository<CompanyCoordinateEntity, Integer>,
        JpaSpecificationExecutor<CompanyCoordinateEntity>  {
    /**
     * 根据contactId去查找数据
     * @param contactId
     * @return
     */
    List<CompanyCoordinateEntity> findByContactId(Long contactId);
}
