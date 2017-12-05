package com.mindata.ecserver.main.repository.secondary;

import com.mindata.ecserver.main.model.secondary.CompanyCoordinateEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author hanliqiang wrote on 2017/11/26
 */
public interface CompanyCoordinateRepository extends JpaRepository<CompanyCoordinateEntity, Integer>,
        JpaSpecificationExecutor<CompanyCoordinateEntity> {


    /**
     * 查询不太靠谱或者无坐标的数据
     * @param status 状态
     * @param accuracy 准确度
     * @param pageable 分页
     * @return 结果
     */
    Page<CompanyCoordinateEntity> findByStatusOrAccuracy(Integer status,Integer accuracy,Pageable pageable);

    /**
     * 根据contactId 删除
     * @param contactId 公司Id
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value="delete from CompanyCoordinateEntity where contactId = ?1")
    void deleteByContactId(Long contactId);

    /**
     * 根据contactId 查找
     * @param contactId 公司Id
     * @return 结果
     */
    List<CompanyCoordinateEntity> findByContactId(Long contactId);
}
