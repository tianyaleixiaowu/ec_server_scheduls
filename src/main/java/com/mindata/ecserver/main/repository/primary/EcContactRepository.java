package com.mindata.ecserver.main.repository.primary;

import com.mindata.ecserver.main.model.primary.EcContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author wuweifeng wrote on 2017/10/25.
 */
public interface EcContactRepository extends JpaRepository<EcContactEntity, Integer>,
        JpaSpecificationExecutor<EcContactEntity> {

    /**
     * 根据id集合查询线索集合
     *
     * @param ids
     *         id集合
     * @return 线索集合
     */
    List<EcContactEntity> findByIdIn(List<Integer> ids);
}
