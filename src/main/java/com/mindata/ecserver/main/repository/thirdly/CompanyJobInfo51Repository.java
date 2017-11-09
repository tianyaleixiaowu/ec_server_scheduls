package com.mindata.ecserver.main.repository.thirdly;

import com.mindata.ecserver.main.model.thirdly.CompanyJobInfo51;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wuweifeng wrote on 2017/11/8.
 */
public interface CompanyJobInfo51Repository extends JpaRepository<CompanyJobInfo51, Integer> {
    List<CompanyJobInfo51> findByCompId(Long compId);
}
