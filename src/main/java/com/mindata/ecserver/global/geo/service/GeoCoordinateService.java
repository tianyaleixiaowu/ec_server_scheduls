package com.mindata.ecserver.global.geo.service;

import com.mindata.ecserver.global.geo.GeoCoordinateServiceImpl;
import com.mindata.ecserver.global.http.response.BaiduMutilResponseData;
import com.mindata.ecserver.global.http.response.BaiduResponseData;
import com.mindata.ecserver.global.http.response.GaodeResponseData;
import com.mindata.ecserver.main.manager.CompanyCoordinateManager;
import com.mindata.ecserver.main.model.secondary.CompanyCoordinateEntity;
import com.xiaoleilu.hutool.util.CollectionUtil;
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
    public List<CompanyCoordinateEntity> getLocation(String address, String companyName, String city) throws IOException {
        if (StrUtil.isNotEmpty(address)) {
            //判断地址里是否包含 层、号、楼、厦 如果包含认为是准确地址
            if (address.contains(ADDRESS_ARRANGEMENT) || address.contains(ADDRESS_NUMBER) ||
                    address.contains(ADDRESS_FLOOR) || address.contains(ADDRESS_MANSION)) {
                BaiduResponseData baiduResult = (BaiduResponseData) geoCoordinates.get(0).getCoordinateByAddress(address);
                if (baiduResult.getResult() != null) {
                    return coordinateManager.getSingleBaiduCoordinate(baiduResult, address);
                }
                GaodeResponseData gaodeAddressResult = (GaodeResponseData) geoCoordinates.get(1).getCoordinateByAddress(address);
                if (CollectionUtil.isNotEmpty(gaodeAddressResult.getGeocodes())) {
                    return coordinateManager.getSingleGaodeCoordinate(gaodeAddressResult, address);
                }
            }
        }
        GaodeResponseData gaodeCompanyResult = new GaodeResponseData();
        if (StrUtil.isNotEmpty(companyName)) {
            BaiduMutilResponseData baiduMutilData = (BaiduMutilResponseData) geoCoordinates.get(0).getCoordinateByCompanyName(companyName, city, PAGE_SIZE, PAGE);
            if (CollectionUtil.isNotEmpty(baiduMutilData.getResults())) {
                List<BaiduMutilResponseData> list = new ArrayList<>();
                for (int i = 0; i < baiduMutilData.getTotal() / PAGE_SIZE + 1; i++) {
                    //分页去百度地图查询数据
                    baiduMutilData = (BaiduMutilResponseData) geoCoordinates.get(0).getCoordinateByCompanyName(companyName, city, PAGE_SIZE, i);
                    list.add(baiduMutilData);
                }
                return coordinateManager.getMutilBaiduCoordinate(list, companyName);
            }
            gaodeCompanyResult = (GaodeResponseData) geoCoordinates.get(1).getCoordinateByAddress(companyName);
            return coordinateManager.getMutilGaodeCoordinate(gaodeCompanyResult, companyName);
        }
        return coordinateManager.getMutilGaodeCoordinate(gaodeCompanyResult, companyName);
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
    public List<CompanyCoordinateEntity> getOutLocation(String address, String companyName, String city) throws IOException {
        if (StrUtil.isNotEmpty(address)) {
            BaiduResponseData baiduResult = (BaiduResponseData) geoCoordinates.get(0).getCoordinateByAddress(address);
            if (baiduResult.getResult() != null) {
                return coordinateManager.getSingleBaiduCoordinate(baiduResult, address);
            }
            GaodeResponseData gaodeAddressResult = (GaodeResponseData) geoCoordinates.get(1).getCoordinateByAddress(address);
            if (CollectionUtil.isNotEmpty(gaodeAddressResult.getGeocodes())) {
                return coordinateManager.getSingleGaodeCoordinate(gaodeAddressResult, address);
            }
        }
        GaodeResponseData gaodeCompanyResult = new GaodeResponseData();
        if (StrUtil.isNotEmpty(companyName)) {
            BaiduMutilResponseData baiduMutilData = (BaiduMutilResponseData) geoCoordinates.get(0).getCoordinateByCompanyName(companyName, city, PAGE_SIZE, PAGE);
            if (CollectionUtil.isNotEmpty(baiduMutilData.getResults())) {
                List<BaiduMutilResponseData> list = new ArrayList<>();
                for (int i = 0; i < baiduMutilData.getTotal() / PAGE_SIZE + 1; i++) {
                    //分页去百度地图查询数据
                    baiduMutilData = (BaiduMutilResponseData) geoCoordinates.get(0).getCoordinateByCompanyName(companyName, city, PAGE_SIZE, i);
                    list.add(baiduMutilData);
                }
                return coordinateManager.getMutilBaiduCoordinate(list, companyName);
            }
            gaodeCompanyResult = (GaodeResponseData) geoCoordinates.get(1).getCoordinateByAddress(companyName);
            return coordinateManager.getMutilGaodeCoordinate(gaodeCompanyResult, companyName);
        }
        return coordinateManager.getMutilGaodeCoordinate(gaodeCompanyResult, companyName);
    }
}
