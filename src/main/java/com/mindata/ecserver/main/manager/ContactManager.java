package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.primary.EcContactEntity;
import com.mindata.ecserver.main.repository.primary.EcContactRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author wuweifeng wrote on 2017/11/9.
 */
@Service
public class ContactManager {
    @Resource
    private EcContactRepository ecContactRepository;

    public Page<EcContactEntity> findContact(Pageable pageable) {
        return ecContactRepository.findAll(pageable);
    }

    /**
     * 查询createTime晚于目标时间的
     */
    public Page<EcContactEntity> findContactByCreateTimeAfter(Date createTime, Pageable pageable) {
        return ecContactRepository.findByCreateTimeAfter(createTime, pageable);
    }
}
