package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.primary.EcCustomerOperation;
import com.mindata.ecserver.main.repository.primary.EcCustomerOperationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2017/12/27.
 */
@Component
public class EcCustomerOperationManager {
    @Resource
    private EcCustomerOperationRepository ecCustomerOperationRepository;

    public Page<EcCustomerOperation> find(Pageable pageable) {
        return ecCustomerOperationRepository.findAll(pageable);
    }

    public Page<EcCustomerOperation> findByIdBetween(Long beginId, Long endId, Pageable pageable) {
        return ecCustomerOperationRepository.findByIdBetween(beginId, endId, pageable);
    }

    public EcCustomerOperation findOne(Long id) {
        return ecCustomerOperationRepository.findOne(id);
    }

    /**
     * 查询最新的一条数据的Id
     *
     * @return id
     */
    public Long findLastOperationId() {
        //要查询的起始Operation的id
        EcCustomerOperation ecCustomerOperation = ecCustomerOperationRepository.findFirstByOrderByIdDesc();
        return ecCustomerOperation.getId();
    }

    public Long findFirstOperationId() {
        //要查询的起始Operation的id
        EcCustomerOperation ecCustomerOperation = ecCustomerOperationRepository.findFirstByOrderByIdDesc();
        return ecCustomerOperation.getId();
    }

    /**
     * 查询id范围内的数量
     * @param beginId
     * 开始
     * @param endId
     * 结束
     * @return
     * 数量
     */
    public Integer countByIdBetween(Long beginId, Long endId) {
        return ecCustomerOperationRepository.countByIdBetween(beginId, endId);
    }
}
