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

import static com.mindata.ecserver.global.Constant.STATE_NORMAL;

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


    /**
     * 计算两个id间的数量
     * @param beginId
     * begin
     * @param endId
     * end
     * @return
     * 数量
     */
    public Long countIdBetween(Long beginId, Long endId) {
        return ecContactRepository.countByIdBetween(beginId, endId);
    }

    public Page<EcContactEntity> findContact(Pageable pageable) {
        return ecContactRepository.findAll(pageable);
    }

    /**
     * 分页查询所有
     * @param pageable
     * pageable
     * @return
     * 分页结果
     */
    public Page<EcContactEntity> findAll(Pageable pageable) {
        return ecContactRepository.findAll(pageable);
    }

    /**
     * 查询没有省市code的
     *
     * @param pageable 分页
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
     * 查询id在某个范围的
     */
    public Page<EcContactEntity> findByIdBetween(Long beginId, Long endId, Pageable pageable) {
        return ecContactRepository.findByIdBetween(beginId, endId, pageable);
    }

    /**
     * 查询最新的一条
     */
    public EcContactEntity findLastOne() {
        Pageable pageable = new PageRequest(0, 1, Sort.Direction.DESC, "id");
        return ecContactRepository.findAll(pageable).getContent().get(0);
    }

    /**
     * 查询第一条
     */
    public EcContactEntity findFirstOne() {
        Pageable pageable = new PageRequest(0, 1, Sort.Direction.ASC, "id");
        return ecContactRepository.findAll(pageable).getContent().get(0);
    }

    public Integer deleteByIds(String ids) {
        if (ids.endsWith(",")) {
            ids = ids.substring(0, ids.length() - 1);
        }
        return ecContactRepository.updateState(ids);
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
     * 根据状态查询所有正常状态的公司
     */
    public Page<EcContactEntity> findByState(Pageable pageable) {
        return ecContactRepository.findByState(STATE_NORMAL, pageable);
    }

    /**
     * 更新code值
     *
     * @param compId 公司Id
     */
    private void updateVocationCode(Long compId,String companyName) {
        List<String> industryList = companyIndustryInfoManager.getIndustryInfoForDb(compId,companyName);
        HashMap<String, Integer> map = esVocationCodeManager.findByVocationName(industryList.get(0));
        Integer vocationCode = map.get("vocationCode");
        Integer num = ecContactRepository.updateCodeByVocationName(vocationCode, compId);
        if (num > 0) {
            LOGGER.info("更新成功" + num + "条vocationCode");
        }
    }

    /**
     * 查询某段时间内的数据
     */
    public Page<EcContactEntity> findByDateBetween(Date begin, Date end, Pageable pageable) {
        return ecContactRepository.findByCreateTimeBetween(begin,end,pageable);
    }

    public EcContactEntity findOne(Long id) {
        return ecContactRepository.findById(id);
    }

}
