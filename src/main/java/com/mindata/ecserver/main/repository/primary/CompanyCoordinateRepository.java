package com.mindata.ecserver.main.repository.primary;

import com.mindata.ecserver.main.model.primary.CompanyCoordinate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author hanliqiang wrote on 2017/11/26
 */
public interface CompanyCoordinateRepository extends JpaRepository<CompanyCoordinate, Integer>,
        JpaSpecificationExecutor<CompanyCoordinate>  {
}
