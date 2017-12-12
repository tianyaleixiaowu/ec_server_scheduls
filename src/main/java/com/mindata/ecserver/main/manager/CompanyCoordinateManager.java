package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.global.geo.GeoCoordinateService;
import com.mindata.ecserver.global.http.response.CoordinateResultData;
import com.mindata.ecserver.global.util.CommonUtil;
import com.mindata.ecserver.main.model.primary.EcContactEntity;
import com.mindata.ecserver.main.model.secondary.CompanyCoordinateEntity;
import com.mindata.ecserver.main.repository.secondary.CompanyCoordinateRepository;
import com.xiaoleilu.hutool.util.CollectionUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.mindata.ecserver.global.Constant.NONE_ADDRESS;
import static com.mindata.ecserver.global.Constant.NORELIABLE_ACCURAY;

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

    public List<CompanyCoordinateEntity> saveByContacts(List<EcContactEntity> contactEntities, Boolean force) throws IOException {
        if (force == null) {
            force = false;
        }
        List<CompanyCoordinateEntity> coordinateEntities = new ArrayList<>();
        for (EcContactEntity ecContactEntity : contactEntities) {
            String city = ecCodeAreaManager.findNameById(ecContactEntity.getCity(), ecContactEntity.getProvince());
//            String address = "北京市";
//            String company = "北京博茶茶业有限公司";
//            String cityname = "北京市";
            List<CoordinateResultData> temp = geoCoordinateService.getLocation(ecContactEntity.getAddress(), ecContactEntity.getCompany(), city);
            coordinateEntities.addAll(save(temp, ecContactEntity.getId(), force));
        }
        return coordinateEntities;
    }

    /**
     * 插入一个contactId的经纬度
     *
     * @param contactId 未推送表的Id
     */
    private List<CompanyCoordinateEntity> save(List<CoordinateResultData> resultDatas, Long contactId, Boolean force) {
        List<CompanyCoordinateEntity> coordinateEntities = coordinateRepository.findByContactId(contactId);
        if (CollectionUtil.isNotEmpty(coordinateEntities) && !force) {
            return coordinateEntities;
        }
        for (CoordinateResultData resultData : resultDatas) {
            resultData.setContactId(contactId);
            resultData.setCreateTime(CommonUtil.getNow());
        }
        coordinateRepository.deleteByContactId(contactId);
        return coordinateRepository.save(resultDatas.stream().map(this::convert).collect(Collectors.toList()));
    }

    /**
     * 转换为数据库实体
     *
     * @param resultData 参数
     * @return 结果
     */
    private CompanyCoordinateEntity convert(CoordinateResultData resultData) {
        CompanyCoordinateEntity coordinateEntity = new CompanyCoordinateEntity();
        BeanUtils.copyProperties(resultData, coordinateEntity);
        return coordinateEntity;
    }

    /**
     * 查询不太靠谱或者没有坐标的数据
     *
     * @param pageable 分页
     * @return 结果
     */
    public Page<CompanyCoordinateEntity> findByStatusOrAccuracy(Pageable pageable) {
        return coordinateRepository.findByStatusOrAccuracy(NONE_ADDRESS, NORELIABLE_ACCURAY, pageable);
    }


}
