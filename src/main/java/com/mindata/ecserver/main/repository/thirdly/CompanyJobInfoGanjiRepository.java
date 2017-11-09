package com.mindata.ecserver.main.repository.thirdly;

import com.mindata.ecserver.main.model.thirdly.CompanyJobInfoGanji;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wuweifeng wrote on 2017/11/8.
 */
public interface CompanyJobInfoGanjiRepository extends JpaRepository<CompanyJobInfoGanji, Integer> {
    List<CompanyJobInfoGanji> findByCompId(Long compId);
}
