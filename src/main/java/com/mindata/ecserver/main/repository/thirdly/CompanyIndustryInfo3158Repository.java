package com.mindata.ecserver.main.repository.thirdly;

import com.mindata.ecserver.main.model.thirdly.CompanyIndustryInfo3158;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author hanliqiang wrote on 2017/11/14
 */
public interface CompanyIndustryInfo3158Repository extends JpaRepository<CompanyIndustryInfo3158, Integer> {
    /**
     * 根据compId 查找行业名称
     *
     * @param compId 公司Id
     * @return 结果
     */
    List<CompanyIndustryInfo3158> findByCompId(Long compId);
}
