package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.global.geo.service.GeoCoordinateService;
import com.mindata.ecserver.global.geo.util.ConvertBaiduCoordinateUtil;
import com.mindata.ecserver.global.http.response.*;
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
        List<CompanyCoordinateEntity> coordinateEntities = coordinateRepository.findByContactId(contactId);
        if (CollectionUtil.isNotEmpty(coordinateEntities) && !force) {
            return coordinateEntities;
        }
        resultDatas.forEach(resultData -> resultData.setContactId(contactId));
        coordinateRepository.deleteByContactId(contactId);
        return coordinateRepository.save(resultDatas.stream().map(this::convert).collect(Collectors.toList()));
    }

    /**
     * 转换为数据库实体
     *
     * @param resultData
     *         参数
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
     * @param pageable
     *         分页
     * @return 结果
     */
    public Page<CompanyCoordinateEntity> findByStatusOrAccuracy(Pageable pageable) {
        return coordinateRepository.findByStatusOrAccuracy(NONE_ADDRESS, NORELIABLE_ACCURAY, pageable);
    }

    /**
     * 获取单个百度坐标
     *
     * @param baiduResult
     *         参数
     * @param address
     *         地址
     * @return 结果
     */
    public List<CoordinateResultData> getSingleBaiduCoordinate(BaiduResponseData baiduResult, String address) {
        List<CoordinateResultData> coordinateEntities = new ArrayList<>();
        CoordinateResultData resultData = new CoordinateResultData();
        resultData.setStatus(NORMAL_ADDRESS);
        resultData.setQueryCondition(QUERY_ADDRESS);
        resultData.setQueryConditionValue(address);
        resultData.setSource(BAIDU_SOURCE);
        resultData.setAccuracy(CONFIRM_ACCURAY);
        resultData.setLevel(baiduResult.getResult().getLevel());
        resultData.setBaiduCoordinate(baiduResult.getResult().getLocation().getCoordinate());
        resultData.setCreateTime(CommonUtil.getNow());
        coordinateEntities.add(resultData);
        return coordinateEntities;
    }

    /**
     * 获取单个高德坐标
     *
     * @param gaodeResult
     *         参数
     * @param address
     *         地址
     * @return 结果
     * @throws IOException
     *         异常
     */
    public List<CoordinateResultData> getSingleGaodeCoordinate(GaodeResponseData gaodeResult, String address) throws
            IOException {
        List<CoordinateResultData> coordinateEntities = new ArrayList<>();
        CoordinateResultData resultData = new CoordinateResultData();
        //百度根据地址没查到则根据地址去查询高德
        resultData.setQueryCondition(QUERY_ADDRESS);
        resultData.setQueryConditionValue(address);
        resultData.setStatus(NORMAL_ADDRESS);
        resultData.setAccuracy(CONFIRM_ACCURAY);
        resultData.setSource(GAODE_SOURCE);
        resultData.setGaodeCoordinate(gaodeResult.getGeocodes().get(0).getLocation());
        //将高德坐标转换为百度坐标
        String location = ConvertBaiduCoordinateUtil.convertBaiduCoordinate(resultData.getGaodeCoordinate());
        resultData.setBaiduCoordinate(location);
        resultData.setCreateTime(CommonUtil.getNow());
        coordinateEntities.add(resultData);
        return coordinateEntities;
    }

    /**
     * 获取多个百度坐标
     *
     * @param companyName
     *         companyName
     * @return 结果
     */
    public List<CoordinateResultData> getMultipleBaiduCoordinate(List<BaiduLocationResultBean> baiduMultipleDatas,
                                                                 String companyName) {
        List<CoordinateResultData> coordinateEntities = new ArrayList<>();

        if (baiduMultipleDatas.size() == 1) {
            CoordinateResultData resultData = parseBaiduLocationResultBean(companyName, true);
            return new ArrayList<CoordinateResultData>() {
                @Override
                public boolean add(CoordinateResultData coordinateResultData) {
                    return add(resultData);
                }
            };
        }

        for (BaiduLocationResultBean baiduLocationResultBean : baiduMultipleDatas) {
            CoordinateResultData resultData = parseBaiduLocationResultBean(companyName, false);
            resultData.setBaiduCoordinate(baiduLocationResultBean.getCoordinate());
            coordinateEntities.add(resultData);
        }
        return coordinateEntities;
    }

    private CoordinateResultData parseBaiduLocationResultBean(String companyName, boolean single) {
        CoordinateResultData resultData = new CoordinateResultData();
        if (single) {
            resultData.setStatus(NORMAL_ADDRESS);
            resultData.setAccuracy(CONFIRM_ACCURAY);
        } else {
            resultData.setStatus(MORE_ADDRESS);
            resultData.setAccuracy(MAYBE_ACCURAY);
        }
        resultData.setQueryCondition(QUERY_COMPANYNAME);
        resultData.setQueryConditionValue(companyName);
        resultData.setSource(BAIDU_SOURCE);
        return resultData;
    }

    /**
     * 获取多个高德坐标或者没有
     *
     * @param companyName
     *         公司名
     * @return 结果
     * @throws IOException
     *         异常
     */
    public List<CoordinateResultData> getMultipleGaodeCoordinate(List<GaodeMultipleResponseBean>
                                                                         multipleResponseBeans, String companyName)
            throws IOException {
        List<CoordinateResultData> coordinateEntities = new ArrayList<>();
        for (GaodeMultipleResponseBean gaodeMultipleResponseBean : multipleResponseBeans) {
            CoordinateResultData resultData = new CoordinateResultData();
            //如果多条会有多个地址
            if (multipleResponseBeans.size() > 1) {
                resultData.setStatus(MORE_ADDRESS);
                resultData.setAccuracy(MAYBE_ACCURAY);
                //反之单个 则正常可以确认
            } else {
                resultData.setStatus(NORMAL_ADDRESS);
                resultData.setAccuracy(CONFIRM_ACCURAY);
            }
            resultData.setQueryCondition(QUERY_COMPANYNAME);
            resultData.setQueryConditionValue(companyName);
            resultData.setGaodeCoordinate(gaodeMultipleResponseBean.getLocation());
            //将高德坐标转换为百度坐标
            String location = ConvertBaiduCoordinateUtil.convertBaiduCoordinate(resultData.getGaodeCoordinate());
            resultData.setBaiduCoordinate(location);
            resultData.setCreateTime(CommonUtil.getNow());
            coordinateEntities.add(resultData);
        }
        return coordinateEntities;
    }

    /**
     * 没有查到经纬度
     *
     * @param companyName
     *         公司名称
     * @return 结果
     * @throws IOException
     *         异常
     */
    public List<CoordinateResultData> getNoneCoordinate(String companyName) throws IOException {
        List<CoordinateResultData> coordinateEntities = new ArrayList<>();
        CoordinateResultData resultData = new CoordinateResultData();
        resultData.setQueryCondition(QUERY_COMPANYNAME);
        resultData.setQueryConditionValue(companyName);
        resultData.setSource(GAODE_SOURCE);
        resultData.setStatus(NONE_ADDRESS);
        resultData.setAccuracy(NORELIABLE_ACCURAY);
        resultData.setLevel(null);
        resultData.setCreateTime(CommonUtil.getNow());
        coordinateEntities.add(resultData);
        return coordinateEntities;
    }
}
