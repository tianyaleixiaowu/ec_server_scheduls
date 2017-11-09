package com.mindata.ecserver.main.repository.thirdly;

import com.mindata.ecserver.main.model.thirdly.CompanyJobInfoZl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wuweifeng wrote on 2017/11/8.
 */
public interface CompanyJobInfoZlRepository extends JpaRepository<CompanyJobInfoZl, Integer> {
    List<CompanyJobInfoZl> findByCompId(Long compId);
}
