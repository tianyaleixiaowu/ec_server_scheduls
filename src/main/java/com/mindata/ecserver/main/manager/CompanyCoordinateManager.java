package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.global.geo.GeoCoordinateService;
import com.mindata.ecserver.global.http.response.CoordinateResultData;
import com.mindata.ecserver.global.util.CommonUtil;
import com.mindata.ecserver.main.model.primary.EcContactEntity;
import com.mindata.ecserver.main.model.secondary.CompanyCoordinateEntity;
import com.mindata.ecserver.main.repository.secondary.CompanyCoordinateRepository;
import com.xiaoleilu.hutool.util.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.mindata.ecserver.global.GeoConstant.NONE_ADDRESS;
import static com.mindata.ecserver.global.GeoConstant.NORELIABLE_ACCURAY;

/**
 * @author hanliqiang wrote on 2017/11/24
 */
@Service
public class CompanyCoordinateManager {
    @Resource
    private CompanyCoordinateRepository coordinateRepository;
    @Resource
    private EcCodeAreaManager ecCodeAreaManager;
    @Resource
    private GeoCoordinateService geoCoordinateService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Transactional(rollbackFor = Exception.class)
    public List<CompanyCoordinateEntity> saveByContacts(List<EcContactEntity> contactEntities, Boolean force) throws
            IOException {
        if (force == null) {
            force = false;
        }
        List<CompanyCoordinateEntity> coordinateEntities = new ArrayList<>();
        for (EcContactEntity ecContactEntity : contactEntities) {
            String city = ecCodeAreaManager.findNameById(ecContactEntity.getCity(), ecContactEntity.getProvince());
            List<CoordinateResultData> temp = geoCoordinateService.getLocation(ecContactEntity.getAddress(),
                    ecContactEntity.getCompany(), city);
            coordinateEntities.addAll(save(temp, ecContactEntity.getId(), force));
        }
        return coordinateEntities;
    }

    /**
     * 插入一个contactId的经纬度
     *
     * @param contactId
     *         未推送表的Id
     */
    private List<CompanyCoordinateEntity> save(List<CoordinateResultData> resultDatas, Long contactId, Boolean force) {
        logger.info("开始插入contactId为" + contactId + "的经纬度数据");
        List<CompanyCoordinateEntity> coordinateEntities = coordinateRepository.findByContactId(contactId);
        if (CollectionUtil.isNotEmpty(coordinateEntities) && !force) {
            return coordinateEntities;
        }
        logger.info("删除contactId为" + contactId + "的经纬度数据");
        coordinateRepository.deleteByContactId(contactId);
        List<CompanyCoordinateEntity> companyCoordinateEntities = coordinateRepository.save(resultDatas.stream().map
                (resultData -> convert
                        (resultData, contactId)).collect
                (Collectors.toList
                        ()));
        logger.info("contactId为" + contactId + "的经纬度数据，已经插入成功");
        return companyCoordinateEntities;
    }

    /**
     * 转换为数据库实体
     *
     * @param resultData
     *         参数
     * @return 结果
     */
    private CompanyCoordinateEntity convert(CoordinateResultData resultData, Long contactId) {
        CompanyCoordinateEntity coordinateEntity = new CompanyCoordinateEntity();
        BeanUtils.copyProperties(resultData, coordinateEntity);
        coordinateEntity.setContactId(contactId);
        coordinateEntity.setCreateTime(CommonUtil.getNow());
        return coordinateEntity;
    }

    /**
     * 查询不太靠谱或者没有坐标的数据
     *
     * @param pageable
     *         分页
     * @return 结果
     */
    public Page<CompanyCoordinateEntity> findByStatusOrAccuracy(Pageable pageable) {
        return coordinateRepository.findByStatusOrAccuracy(NONE_ADDRESS, NORELIABLE_ACCURAY, pageable);
    }

}
