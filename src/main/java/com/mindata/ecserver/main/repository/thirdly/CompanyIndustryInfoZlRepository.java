package com.mindata.ecserver.main.repository.thirdly;

import com.mindata.ecserver.main.model.thirdly.CompanyIndustryInfoZl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author hanliqiang wrote on 2017/11/14
 */
public interface CompanyIndustryInfoZlRepository extends JpaRepository<CompanyIndustryInfoZl, Integer> {
    /**
     * 根据compId 查找行业名称
     *
     * @param compId 公司Id
     * @return 结果
     */
    List<CompanyIndustryInfoZl> findByCompId(Long compId);
}
