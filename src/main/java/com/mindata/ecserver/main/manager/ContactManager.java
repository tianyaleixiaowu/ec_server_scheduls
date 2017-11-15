package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.primary.EcContactEntity;
import com.mindata.ecserver.main.repository.primary.EcContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author wuweifeng wrote on 2017/11/9.
 */
@Service
public class ContactManager {
    @Resource
    private EcContactRepository ecContactRepository;
    @Resource
    private EcCodeAreaManager ecCodeAreaManager;
    @Resource
    private CompanyIndustryInfoManager companyIndustryInfoManager;
    @Resource
    private EsVocationCodeManager esVocationCodeManager;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass().getName());

    public Page<EcContactEntity> findContact(Pageable pageable) {
        return ecContactRepository.findAll(pageable);
    }

    /**
     * 查询createTime晚于目标时间的
     */
    public Page<EcContactEntity> findContactByCreateTimeAfter(Date createTime, Pageable pageable) {
        return ecContactRepository.findByCreateTimeAfter(createTime, pageable);
    }

    /**
     * 该方法是更新老数据的行业和省市信息的
     * 补齐省市县code表
     */
    //@PostConstruct
    public void completeAreaCode() {
        LOGGER.info("开始补齐省市县");
        Pageable pageable = new PageRequest(0, 100);
        Page<EcContactEntity> ecContactEntities = findContact(pageable);
        int total = ecContactEntities.getTotalPages();
        for (int i = 0; i < total; i++) {
            pageable = new PageRequest(i, 100);
            Page<EcContactEntity> entities = findContact(pageable);
            for (EcContactEntity ecContactEntity : entities.getContent()) {
                if (ecContactEntity.getProvince().equals("0") || ecContactEntity.getCity().equals("0")) {
                    HashMap<String, Integer> map = ecCodeAreaManager.findAreaCode(ecContactEntity.getAddress());
                    ecContactEntity.setProvince(map.get("province") + "");
                    ecContactEntity.setCity(map.get("city") + "");
                    ecContactRepository.save(ecContactEntity);
                }
                LOGGER.info("开始补齐行业code");
                updateVocationCode(ecContactEntity.getCompId());
            }
        }
        LOGGER.info("补齐完毕");
    }

    /**
     * 更新code值
     *
     * @param compId 公司Id
     */
    private void updateVocationCode(Long compId) {
        List<String> industryList = companyIndustryInfoManager.getIndustryInfoForDb(compId);
        HashMap<String, Integer> map = esVocationCodeManager.findByVocationName(industryList.get(0));
        Integer vocationCode = map.get("vocationCode");
        Integer num = ecContactRepository.updateCodeByVocationName(vocationCode, compId);
        if (num > 0) {
            LOGGER.info("更新成功" + num + "条vocationCode");
        }
    }

}
