package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.global.geo.ConvertBaiduCoordinate;
import com.mindata.ecserver.global.geo.GeoCoordinateService;
import com.mindata.ecserver.global.http.response.BaiduMutilResponseData;
import com.mindata.ecserver.global.http.response.BaiduResponseData;
import com.mindata.ecserver.global.http.response.GaodeResponseData;
import com.mindata.ecserver.global.util.CommonUtil;
import com.mindata.ecserver.main.model.primary.EcContactEntity;
import com.mindata.ecserver.main.model.secondary.CompanyCoordinateEntity;
import com.mindata.ecserver.main.repository.secondary.CompanyCoordinateRepository;
import com.xiaoleilu.hutool.util.CollectionUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static com.mindata.ecserver.global.Constant.*;

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
    @Resource
    private ConvertBaiduCoordinate convertBaiduCoordinate;

    public List<CompanyCoordinateEntity> saveByContacts(List<EcContactEntity> contactEntities, Boolean force) throws IOException, NoSuchAlgorithmException {
        if (force == null) {
            force = false;
        }
        List<CompanyCoordinateEntity> coordinateEntities = new ArrayList<>();
        for (EcContactEntity ecContactEntity : contactEntities) {
            String city = ecCodeAreaManager.findNameById(ecContactEntity.getCity(), ecContactEntity.getProvince());
            List<CompanyCoordinateEntity> temp = geoCoordinateService.getLocation(ecContactEntity.getAddress(), ecContactEntity.getCompany(), city);
            coordinateEntities.addAll(save(temp, ecContactEntity.getId(), force));
        }
        return coordinateEntities;
    }

    /**
     * 插入一个contactId的经纬度
     *
     * @param companyCoordinateEntities 集合
     * @param contactId                 未推送表的Id
     */
    private List<CompanyCoordinateEntity> save(List<CompanyCoordinateEntity> companyCoordinateEntities, Long contactId, Boolean force) {
        List<CompanyCoordinateEntity> coordinateEntities = coordinateRepository.findByContactId(contactId);
        if (CollectionUtil.isNotEmpty(coordinateEntities) && !force) {
            return coordinateEntities;
        }
        for (CompanyCoordinateEntity coordinateEntity : companyCoordinateEntities) {
            coordinateEntity.setContactId(contactId);
        }

        coordinateRepository.deleteByContactId(contactId);
        return coordinateRepository.save(companyCoordinateEntities);

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

    /**
     * 获取单个百度坐标
     *
     * @param baiduResult 参数
     * @param address     地址
     * @return 结果
     */
    public List<CompanyCoordinateEntity> getSingleBaiduCoordinate(BaiduResponseData baiduResult, String address) {
        List<CompanyCoordinateEntity> coordinateEntities = new ArrayList<>();
        CompanyCoordinateEntity coordinateEntity = new CompanyCoordinateEntity();
        coordinateEntity.setStatus(NORMAL_ADDRESS);
        coordinateEntity.setQueryCondition(QUERY_ADDRESS);
        coordinateEntity.setQueryConditionValue(address);
        coordinateEntity.setSource(BAIDU_SOURCE);
        coordinateEntity.setAccuracy(CONFIRM_ACCURAY);
        coordinateEntity.setLevel(baiduResult.getResult().getLevel());
        coordinateEntity.setBaiduCoordinate(baiduResult.getResult().getLocation().getLng() + "," + baiduResult.getResult().getLocation().getLat());
        coordinateEntity.setCreateTime(CommonUtil.getNow());
        coordinateEntities.add(coordinateEntity);
        return coordinateEntities;
    }

    /**
     * 获取单个高德坐标
     *
     * @param gaodeResult 参数
     * @param address     地址
     * @return 结果
     * @throws IOException 异常
     */
    public List<CompanyCoordinateEntity> getSingleGaodeCoordinate(GaodeResponseData gaodeResult, String address) throws IOException {
        List<CompanyCoordinateEntity> coordinateEntities = new ArrayList<>();
        CompanyCoordinateEntity coordinateEntity = new CompanyCoordinateEntity();
        //百度根据地址没查到则根据地址去查询高德
        coordinateEntity.setQueryCondition(QUERY_ADDRESS);
        coordinateEntity.setQueryConditionValue(address);
        coordinateEntity.setStatus(NORMAL_ADDRESS);
        coordinateEntity.setAccuracy(CONFIRM_ACCURAY);
        coordinateEntity.setSource(GAODE_SOURCE);
        coordinateEntity.setGaodeCoordinate(gaodeResult.getGeocodes().get(0).getLocation());
        //将高德坐标转换为百度坐标
        String location = convertBaiduCoordinate.convertBaiduCoordinate(coordinateEntity.getGaodeCoordinate());
        coordinateEntity.setBaiduCoordinate(location);
        coordinateEntity.setCreateTime(CommonUtil.getNow());
        coordinateEntities.add(coordinateEntity);
        return coordinateEntities;
    }

    /**
     * 获取多个百度坐标
     *
     * @param baiduMutilData baiduMutilData
     * @param companyName    companyName
     * @return 结果
     */
    public List<CompanyCoordinateEntity> getMutilBaiduCoordinate(BaiduMutilResponseData baiduMutilData, String companyName) {
        List<CompanyCoordinateEntity> coordinateEntities = new ArrayList<>();
        if (baiduMutilData.getResults() != null && baiduMutilData.getResults().size() > 0) {
            for (int i = 0; i < baiduMutilData.getResults().size(); i++) {
                CompanyCoordinateEntity coordinateEntity = new CompanyCoordinateEntity();
                //如果多条会有多个地址
                if (baiduMutilData.getResults().size() > 1) {
                    coordinateEntity.setStatus(MORE_ADDRESS);
                    coordinateEntity.setAccuracy(MAYBE_ACCURAY);
                    //反之单个 则正常可以确认
                } else {
                    coordinateEntity.setStatus(NORMAL_ADDRESS);
                    coordinateEntity.setAccuracy(CONFIRM_ACCURAY);
                }
                coordinateEntity.setQueryCondition(QUERY_COMPANYNAME);
                coordinateEntity.setQueryConditionValue(companyName);
                coordinateEntity.setSource(BAIDU_SOURCE);
                coordinateEntity.setCreateTime(CommonUtil.getNow());
                coordinateEntity.setBaiduCoordinate(baiduMutilData.getResults().get(i).getLocation().getLng() + "," + baiduMutilData.getResults().get(i).getLocation().getLat());
                coordinateEntity.setLevel(baiduMutilData.getResults().get(i).getTag());
                coordinateEntities.add(coordinateEntity);
            }
        }
        return coordinateEntities;
    }

    /**
     * 获取多个高德坐标或者没有
     *
     * @param gaodeCompanyResult 参数
     * @param companyName        公司名
     * @return 结果
     * @throws IOException 异常
     */
    public List<CompanyCoordinateEntity> getMutilGaodeCoordinate(GaodeResponseData gaodeCompanyResult, String companyName) throws IOException {
        List<CompanyCoordinateEntity> coordinateEntities = new ArrayList<>();
        CompanyCoordinateEntity coordinateEntity = new CompanyCoordinateEntity();
        if (gaodeCompanyResult.getGeocodes() != null && gaodeCompanyResult.getGeocodes().size() > 0) {
            for (int i = 0; i < gaodeCompanyResult.getGeocodes().size(); i++) {
                coordinateEntity = new CompanyCoordinateEntity();
                //如果多条会有多个地址
                if (gaodeCompanyResult.getGeocodes().size() > 1) {
                    coordinateEntity.setStatus(MORE_ADDRESS);
                    coordinateEntity.setAccuracy(MAYBE_ACCURAY);
                    //反之单个 则正常可以确认
                } else {
                    coordinateEntity.setStatus(NORMAL_ADDRESS);
                    coordinateEntity.setAccuracy(CONFIRM_ACCURAY);
                }
                coordinateEntity.setQueryCondition(QUERY_COMPANYNAME);
                coordinateEntity.setQueryConditionValue(companyName);
                coordinateEntity.setGaodeCoordinate(gaodeCompanyResult.getGeocodes().get(i).getLocation());
                coordinateEntity.setLevel(gaodeCompanyResult.getGeocodes().get(i).getLevel());
                //将高德坐标转换为百度坐标
                String location = convertBaiduCoordinate.convertBaiduCoordinate(coordinateEntity.getGaodeCoordinate());
                coordinateEntity.setBaiduCoordinate(location);
                coordinateEntity.setCreateTime(CommonUtil.getNow());
                coordinateEntities.add(coordinateEntity);
            }
        } else {
            //无地址
            coordinateEntity.setQueryCondition(QUERY_COMPANYNAME);
            coordinateEntity.setQueryConditionValue(companyName);
            coordinateEntity.setSource(GAODE_SOURCE);
            coordinateEntity.setStatus(NONE_ADDRESS);
            coordinateEntity.setAccuracy(NORELIABLE_ACCURAY);
            coordinateEntity.setLevel(null);
            coordinateEntity.setCreateTime(CommonUtil.getNow());
            coordinateEntities.add(coordinateEntity);
        }
        return coordinateEntities;
    }
}
