package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
public interface PtCompanyRepository extends JpaRepository<PtCompany, Long>,
        JpaSpecificationExecutor<PtCompany> {

    /**
     * 查询除了过期的数据
     * @param buyStatus buyStatus
     * @return List
     */
    List<PtCompany> findByBuyStatusNot(Integer buyStatus);

}
