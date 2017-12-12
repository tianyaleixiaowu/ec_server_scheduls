package com.mindata.ecserver.global.geo;

import com.mindata.ecserver.global.geo.manager.GeoCoordinateManager;
import com.mindata.ecserver.global.geo.service.IGeoCoordinateService;
import com.mindata.ecserver.global.geo.service.impl.GetBaiduCoordinateServiceImpl;
import com.mindata.ecserver.global.geo.service.impl.GetGaodeCoordinateServiceImpl;
import com.mindata.ecserver.global.geo.util.ConvertBaiduCoordinateUtil;
import com.mindata.ecserver.global.http.response.*;
import com.xiaoleilu.hutool.util.CollectionUtil;
import com.xiaoleilu.hutool.util.ObjectUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mindata.ecserver.global.Constant.*;

/**
 * @author hanliqiang wrote on 2017/11/27
 */
@Service
public class GeoCoordinateService {
    private static final int PAGE_SIZE = 20;
    private static final int PAGE = 0;
    @Resource
    private List<IGeoCoordinateService> geoCoordinates;
    @Resource
    private GeoCoordinateManager coordinateManager;

    /**
     * 获取高德、百度返回的数据
     *
     * @param address     地址
     * @param companyName 公司名字
     * @param city        城市
     * @return 结果
     * @throws IOException 异常
     */
    public List<CoordinateResultData> getLocation(String address, String companyName, String city) throws IOException {
        List<CoordinateResultData> dataList = new ArrayList<>();
        GetBaiduCoordinateServiceImpl baiduCoordinateService = (GetBaiduCoordinateServiceImpl) geoCoordinates.get(0);
        GetGaodeCoordinateServiceImpl gaodeCoordinateService = (GetGaodeCoordinateServiceImpl) geoCoordinates.get(1);
        if (StrUtil.isNotEmpty(address)) {
            //判断地址里是否包含 层、号、幢、楼、厦 如果包含认为是准确地址
            if (address.contains(ADDRESS_ARRANGEMENT) || address.contains(ADDRESS_NUMBER) || address.contains(ADDRESS_BUILDING) ||
                    address.contains(ADDRESS_FLOOR) || address.contains(ADDRESS_MANSION)) {
                BaiduResponseData baiduResult = baiduCoordinateService.getCoordinateByAddress(address);
                if (ObjectUtil.isNotNull(baiduResult) && ObjectUtil.isNotNull(baiduResult.getResult())) {
                    dataList.add(coordinateManager.getSingleBaiduCoordinate(baiduResult, address));
                    return dataList;
                }
                GaodeResponseData gaodeResult = gaodeCoordinateService.getCoordinateByAddress(address);
                if (ObjectUtil.isNotNull(gaodeResult) && CollectionUtil.isNotEmpty(gaodeResult.getGeocodes())) {
                    dataList.add(coordinateManager.getSingleGaodeCoordinate(gaodeResult, address));
                    return dataList;
                }
            }
        }
        BaiduMultipleResponseData baiduMultipleData = baiduCoordinateService.getCoordinateByCompanyName(companyName, city, PAGE_SIZE, PAGE);
        if (ObjectUtil.isNotNull(baiduMultipleData) && baiduMultipleData.getTotal() > 0 && baiduMultipleData.getResults().get(0).getLocation() != null) {
            List<BaiduLocationResultBean> locationResultBeans = new ArrayList<>();
            baiduMultipleData.getResults().forEach(multipleResponseBean -> locationResultBeans.add(multipleResponseBean.getLocation()));
            return coordinateManager.getMultipleBaiduCoordinate(locationResultBeans, companyName);
        }
        GaodeMultipleResponseData gaodeMultipleResponseData = gaodeCoordinateService.getCoordinateByCompanyName(companyName, city, PAGE_SIZE, PAGE + 1);
        if (ObjectUtil.isNotNull(gaodeMultipleResponseData) && CollectionUtil.isNotEmpty(gaodeMultipleResponseData.getPois())) {
            List<GaodeMultipleResponseBean> multipleResponseBeans = new ArrayList<>(gaodeMultipleResponseData.getPois());
            return coordinateManager.getMultipleGaodeCoordinate(multipleResponseBeans, companyName);
        }
        return coordinateManager.getNoneCoordinate(companyName);
    }

    /**
     * 根据地址获取经纬度
     *
     * @param address 地址
     * @return 结果
     * @throws IOException 异常
     */
    public List<String> getOutLocationByAddress(String address) throws IOException {
        List<String> list = new ArrayList<>();
        GetBaiduCoordinateServiceImpl baiduCoordinateService = (GetBaiduCoordinateServiceImpl) geoCoordinates.get(0);
        GetGaodeCoordinateServiceImpl gaodeCoordinateService = (GetGaodeCoordinateServiceImpl) geoCoordinates.get(1);
        BaiduResponseData baiduResult = baiduCoordinateService.getCoordinateByAddress(address);
        if (ObjectUtil.isNotNull(baiduResult) && ObjectUtil.isNotNull(baiduResult.getResult())) {
            list.add(baiduResult.getResult().getLocation().getCoordinate());
            return list;
        }
        GaodeResponseData gaodeResult = gaodeCoordinateService.getCoordinateByAddress(address);
        if (ObjectUtil.isNotNull(gaodeResult) && CollectionUtil.isNotEmpty(gaodeResult.getGeocodes())) {
            list.add(gaodeResult.getGeocodes().get(0).getLocation());
            return list;
        }
        return list;
    }

    /**
     * 根据公司获取经纬度
     *
     * @param companyName 公司名称
     * @param city        城市
     * @return 结果
     * @throws IOException 异常
     */
    public List<String> getOutLocationByCompany(String companyName, String city) throws IOException {
        List<String> list = new ArrayList<>();
        GetBaiduCoordinateServiceImpl baiduCoordinateService = (GetBaiduCoordinateServiceImpl) geoCoordinates.get(0);
        GetGaodeCoordinateServiceImpl gaodeCoordinateService = (GetGaodeCoordinateServiceImpl) geoCoordinates.get(1);
        BaiduMultipleResponseData baiduMultipleData = baiduCoordinateService.getCoordinateByCompanyName(companyName, city, PAGE_SIZE, PAGE);
        if (ObjectUtil.isNotNull(baiduMultipleData) && baiduMultipleData.getTotal() > 0 && baiduMultipleData.getResults().get(0).getLocation() != null) {
            baiduMultipleData.getResults().forEach(multipleResponseBean -> list.add(multipleResponseBean.getLocation().getCoordinate()));
            return list;
        }
        GaodeMultipleResponseData gaodeMultipleData = gaodeCoordinateService.getCoordinateByCompanyName(companyName, city, PAGE_SIZE, PAGE + 1);
        if (ObjectUtil.isNotNull(gaodeMultipleData) && CollectionUtil.isNotEmpty(gaodeMultipleData.getPois())) {
            gaodeMultipleData.getPois().forEach(multipleResponseBean -> list.add(ConvertBaiduCoordinateUtil.convertBaiduCoordinate(multipleResponseBean.getLocation())));
            return list;
        }
        return list;
    }
}
