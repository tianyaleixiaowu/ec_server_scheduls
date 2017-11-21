package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.es.EsContact;
import com.mindata.ecserver.main.model.primary.EcContactEntity;
import com.mindata.ecserver.main.repository.primary.EcContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    @Resource
    private EsContactManager esContactManager;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass().getName());

    public Page<EcContactEntity> findContact(Pageable pageable) {
        return ecContactRepository.findAll(pageable);
    }

    /**
     * 查询没有省市code的
     *
     * @param pageable
     *         分页
     * @return 结果
     */
    private Page<EcContactEntity> findContactByProvince(String province, Pageable pageable) {
        return ecContactRepository.findByProvince(province, pageable);
    }

    /**
     * 查询createTime晚于目标时间的
     */
    public Page<EcContactEntity> findContactByCreateTimeAfter(Date createTime, Pageable pageable) {
        return ecContactRepository.findByCreateTimeAfter(createTime, pageable);
    }

    /**
     * 查询id大于某个id且目标时间早于晚上12点前的
     */
    public Page<EcContactEntity> findByIdGreaterThanAndCreateTimeLessThan(Long id, Date endTime, Pageable pageable) {
        return ecContactRepository.findByIdGreaterThanAndCreateTimeLessThan(id, endTime, pageable);
    }

    /**
     * 查询id大于某个id的
     */
    public Page<EcContactEntity> findByIdGreaterThan(Long id, Pageable pageable) {
        return ecContactRepository.findByIdGreaterThan(id, pageable);
    }

    /**
     * 该方法是更新老数据的行业和省市信息的
     * 补齐省市县code表
     */
    public void completeAreaCode() {
        LOGGER.info("开始补齐省市县");
        Pageable pageable = new PageRequest(0, 100, Sort.Direction.ASC, "id");
        Page<EcContactEntity> ecContactEntities = findContactByProvince("0", pageable);
        int total = ecContactEntities.getTotalPages();
        for (int i = 0; i < total; i++) {
            Page<EcContactEntity> entities = findContactByProvince("0", pageable);
            for (EcContactEntity ecContactEntity : entities.getContent()) {
                HashMap<String, Integer> map = ecCodeAreaManager.findAreaCode(ecContactEntity.getAddress());
                ecContactEntity.setProvince(map.get("province") + "");
                ecContactEntity.setCity(map.get("city") + "");
                ecContactRepository.save(ecContactEntity);
                EsContact esContact = esContactManager.findById(ecContactEntity.getId());
                if (esContact != null) {
                    LOGGER.info("es在更新id为" + ecContactEntity.getId() + "的地址信息");
                    esContact.setProvince(map.get("province"));
                    esContact.setCity(map.get("city"));
                    esContact.setAddress(ecContactEntity.getAddress());
                    esContactManager.save(esContact);
                }
            }
        }
        LOGGER.info("补齐完毕");
    }

    /**
     * 更新code值
     *
     * @param compId
     *         公司Id
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
