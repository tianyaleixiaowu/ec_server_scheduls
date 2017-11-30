package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.CompanyCoordinateEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author hanliqiang wrote on 2017/11/26
 */
public interface CompanyCoordinateRepository extends JpaRepository<CompanyCoordinateEntity, Integer>,
        JpaSpecificationExecutor<CompanyCoordinateEntity> {
    /**
     * 根据contactId去查找数据
     *
     * @param contactId
     * @return
     */
    List<CompanyCoordinateEntity> findByContactId(Long contactId);

    /**
     * 根据状态去查找数据
     * @param status
     * @return
     */
    Page<CompanyCoordinateEntity> findByStatus(Integer status,Pageable pageable);

    /**
     * 根据准确度去
     * @param accuracy
     * @param pageable
     * @return
     */
    Page<CompanyCoordinateEntity> findByAccuracy(Integer accuracy,Pageable pageable);

    /**
     * 查询不太靠谱或者无坐标的数据
     * @param status
     * @param accuracy
     * @param pageable
     * @return
     */
    Page<CompanyCoordinateEntity> findByStatusOrAccuracy(Integer status,Integer accuracy,Pageable pageable);

}
