package com.mindata.ecserver.global.geo.service;

import com.mindata.ecserver.global.geo.GeoCoordinateServiceImpl;
import com.mindata.ecserver.global.http.response.*;
import com.mindata.ecserver.global.util.CommonUtil;
import com.mindata.ecserver.main.manager.CompanyCoordinateManager;
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
    private List<GeoCoordinateServiceImpl> geoCoordinates;
    @Resource
    private CompanyCoordinateManager coordinateManager;

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
        if (StrUtil.isNotEmpty(address)) {
            //判断地址里是否包含 层、号、幢、楼、厦 如果包含认为是准确地址
            if (address.contains(ADDRESS_ARRANGEMENT) || address.contains(ADDRESS_NUMBER) || address.contains(ADDRESS_BUILDING) ||
                    address.contains(ADDRESS_FLOOR) || address.contains(ADDRESS_MANSION)) {
                BaiduResponseData baiduResult = (BaiduResponseData) geoCoordinates.get(0).getCoordinateByAddress(address);
                if (ObjectUtil.isNotNull(baiduResult) && ObjectUtil.isNotNull(baiduResult.getResult())) {
                    return coordinateManager.getSingleBaiduCoordinate(baiduResult, address);
                }
                GaodeResponseData gaodeResult = (GaodeResponseData) geoCoordinates.get(1).getCoordinateByAddress(address);
                if (ObjectUtil.isNotNull(gaodeResult) && CollectionUtil.isNotEmpty(gaodeResult.getGeocodes())) {
                    return coordinateManager.getSingleGaodeCoordinate(gaodeResult, address);
                }
            }
        }
        BaiduMultipleResponseData baiduMultipleData = (BaiduMultipleResponseData) geoCoordinates.get(0).getCoordinateByCompanyName(companyName, city, PAGE_SIZE, PAGE);
        if (ObjectUtil.isNotNull(baiduMultipleData) && baiduMultipleData.getTotal() > 0) {
            List<BaiduLocationResultBean> locationResultBeans = new ArrayList<>();
            for (int i = 0; i < CommonUtil.getTotalPages(baiduMultipleData.getTotal(), PAGE_SIZE); i++) {
                baiduMultipleData = (BaiduMultipleResponseData) geoCoordinates.get(0).getCoordinateByCompanyName(companyName, city, PAGE_SIZE, i);
                if (baiduMultipleData == null || baiduMultipleData.getTotal() == 0 || baiduMultipleData.getResults().get(0).getLocation() == null) {
                    break;
                }
                baiduMultipleData.getResults().forEach(multipleResponseBean -> locationResultBeans.add(multipleResponseBean.getLocation()));
            }
            return coordinateManager.getMultipleBaiduCoordinate(locationResultBeans, companyName);
        }
        GaodeMultipleResponseData gaodeMultipleResponseData = (GaodeMultipleResponseData) geoCoordinates.get(1).getCoordinateByCompanyName(companyName, city, PAGE_SIZE, PAGE + 1);
        if (ObjectUtil.isNotNull(gaodeMultipleResponseData) && CollectionUtil.isNotEmpty(gaodeMultipleResponseData.getPois())) {
            List<GaodeMultipleResponseBean> multipleResponseBeans = new ArrayList<>();
            for (int i = 0; i < CommonUtil.getTotalPages(Integer.valueOf(gaodeMultipleResponseData.getCount()), PAGE_SIZE); i++) {
                gaodeMultipleResponseData = (GaodeMultipleResponseData) geoCoordinates.get(1).getCoordinateByCompanyName(companyName, city, PAGE_SIZE, i + 1);
                if (gaodeMultipleResponseData == null || gaodeMultipleResponseData.getPois().get(0).getLocation() == null) {
                    break;
                }
                multipleResponseBeans.addAll(gaodeMultipleResponseData.getPois());
            }
            return coordinateManager.getMultipleGaodeCoordinate(multipleResponseBeans, companyName);
        }
        return coordinateManager.getNoneCoordinate(companyName);
    }

    /**
     * 对外提供经纬度
     *
     * @param address     地址
     * @param companyName 公司名称
     * @param city        城市
     * @return 结果
     * @throws IOException 异常
     */
    public List<String> getOutLocation(String address, String companyName, String city) throws IOException {
        List<String> list = new ArrayList<>();
        BaiduResponseData baiduResult = (BaiduResponseData) geoCoordinates.get(0).getCoordinateByAddress(address);
        if (ObjectUtil.isNotNull(baiduResult) && ObjectUtil.isNotNull(baiduResult.getResult())) {
            list.add(baiduResult.getResult().getLocation().getCoordinate());
            return list;
        }
        GaodeResponseData gaodeResult = (GaodeResponseData) geoCoordinates.get(1).getCoordinateByAddress(address);
        if (ObjectUtil.isNotNull(gaodeResult) && CollectionUtil.isNotEmpty(gaodeResult.getGeocodes())) {
            list.add(gaodeResult.getGeocodes().get(0).getLocation());
            return list;
        }
        BaiduMultipleResponseData baiduMultipleData = (BaiduMultipleResponseData) geoCoordinates.get(0).getCoordinateByCompanyName(companyName, city, PAGE_SIZE, PAGE);
        if (ObjectUtil.isNotNull(baiduMultipleData) && baiduMultipleData.getTotal() > 0) {
            for (int i = 0; i < CommonUtil.getTotalPages(baiduMultipleData.getTotal(), PAGE_SIZE); i++) {
                baiduMultipleData = (BaiduMultipleResponseData) geoCoordinates.get(0).getCoordinateByCompanyName(companyName, city, PAGE_SIZE, i);
                if (ObjectUtil.isNull(baiduMultipleData) || baiduMultipleData.getTotal() == 0 || baiduMultipleData.getResults().get(0).getLocation() == null) {
                    break;
                }
                baiduMultipleData.getResults().forEach(multipleResponseBean -> list.add(multipleResponseBean.getLocation().getCoordinate()));
            }
            return list;
        }
        GaodeMultipleResponseData gaodeMultipleData = (GaodeMultipleResponseData) geoCoordinates.get(1).getCoordinateByCompanyName(companyName, city, PAGE_SIZE, PAGE + 1);
        if (ObjectUtil.isNotNull(gaodeMultipleData) && CollectionUtil.isNotEmpty(gaodeMultipleData.getPois())) {
            for (int i = 0; i < CommonUtil.getTotalPages(Integer.valueOf(gaodeMultipleData.getCount()), PAGE_SIZE); i++) {
                gaodeMultipleData = (GaodeMultipleResponseData) geoCoordinates.get(1).getCoordinateByCompanyName(companyName, city, PAGE_SIZE, i + 1);
                if (ObjectUtil.isNull(gaodeMultipleData) || gaodeMultipleData.getPois().get(0).getLocation() == null) {
                    break;
                }
                gaodeMultipleData.getPois().forEach(multipleResponseBean -> list.add(multipleResponseBean.getLocation()));
            }
            return list;
        }
        return list;
    }
}
