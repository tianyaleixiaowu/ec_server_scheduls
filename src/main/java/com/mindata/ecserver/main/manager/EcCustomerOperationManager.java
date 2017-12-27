package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.primary.EcCustomerOperation;
import com.mindata.ecserver.main.repository.primary.EcCustomerOperationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    /**
     * 查询最新的一条数据的Id
     *
     * @return id
     */
    public Long findLastOperationId() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(0, 1, sort);
        Page<EcCustomerOperation> page = find(pageable);
        //要查询的起始Operation的id
        Long id = 0L;
        if (page.getTotalElements() > 0L) {
            id = page.getContent().get(0).getId();
        }
        return id;
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
