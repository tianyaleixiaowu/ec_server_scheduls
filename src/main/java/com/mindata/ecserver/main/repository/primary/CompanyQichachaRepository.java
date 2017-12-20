package com.mindata.ecserver.main.repository.primary;

import com.mindata.ecserver.main.model.primary.CompanyQichcacha;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author hanliqiang wrote on 2017/12/20
 */
public interface CompanyQichachaRepository extends JpaRepository<CompanyQichcacha, Integer> {

    /**
     * 根据公司名称查找行业描述
     *
     * @param companyName 公司名称
     * @return 结果
     */
    List<CompanyQichcacha> findBycompanyName(String companyName);
}
