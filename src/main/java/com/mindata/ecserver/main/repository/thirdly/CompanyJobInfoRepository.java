package com.mindata.ecserver.main.repository.thirdly;

import com.mindata.ecserver.main.model.thirdly.CompanyJobInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wuweifeng wrote on 2017/11/8.
 */
public interface CompanyJobInfoRepository extends JpaRepository<CompanyJobInfo, Integer> {
    List<CompanyJobInfo> findByCompId(Long compId);
}
