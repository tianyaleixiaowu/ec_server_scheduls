package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.PtCompanyCoordinateEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author hanliqiang wrote on 2017/11/26
 */
public interface CompanyCoordinateRepository extends JpaRepository<PtCompanyCoordinateEntity, Integer>,
        JpaSpecificationExecutor<PtCompanyCoordinateEntity> {

    /**
     * 查询不太靠谱或者无坐标的数据
     *
     * @param status
     *         状态
     * @param accuracy
     *         准确度
     * @param pageable
     *         分页
     * @return 结果
     */
    Page<PtCompanyCoordinateEntity> findByStatusOrAccuracy(Integer status, Integer accuracy, Pageable pageable);

    /**
     * 根据contactId 删除
     *
     * @param contactId
     *         公司Id
     * @return 删除数量
     */
    Integer deleteByContactId(Long contactId);

    /**
     * 根据contactId 查找
     *
     * @param contactId
     *         公司Id
     * @return 结果
     */
    List<PtCompanyCoordinateEntity> findByContactId(Long contactId);
}
