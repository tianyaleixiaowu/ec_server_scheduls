package com.mindata.ecserver.main.repository.primary;

import com.mindata.ecserver.main.model.primary.CompanyQichacha;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author hanliqiang wrote on 2017/12/20
 */
public interface CompanyQichachaRepository extends JpaRepository<CompanyQichacha, Integer> {

    /**
     * 根据公司名称查找行业描述
     *
     * @param companyName 公司名称
     * @return 结果
     */
    List<CompanyQichacha> findByCompanyName(String companyName);
}
