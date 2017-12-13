package com.mindata.ecserver.global.geo;

import com.mindata.ecserver.global.geo.service.IGeoCoordinateService;
import com.mindata.ecserver.global.geo.service.impl.GeoBaiduCoordinateServiceImpl;
import com.mindata.ecserver.global.geo.service.impl.GeoGaodeCoordinateServiceImpl;
import com.mindata.ecserver.global.geo.util.ConvertBaiduCoordinateUtil;
import com.mindata.ecserver.global.http.response.*;
import com.mindata.ecserver.global.http.response.base.ResponseValue;
import com.xiaoleilu.hutool.util.CollectionUtil;
import com.xiaoleilu.hutool.util.ObjectUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mindata.ecserver.global.GeoConstant.*;

/**
 * @author hanliqiang wrote on 2017/11/27
 */
@Service
public class GeoCoordinateService {
    private static final int PAGE_SIZE = 20;
    private static final int PAGE = 0;
    @Resource
    private List<IGeoCoordinateService> geoCoordinates;

    /**
     * 获取高德、百度返回的数据。该方法用于给Contact表的线索信息获取经纬度
     *
     * @param address     地址
     * @param companyName 公司名字
     * @param city        城市
     * @return 结果
     * @throws IOException 异常
     */
    public List<CoordinateResultData> getLocation(String address, String companyName, String city) throws IOException {
        List<CoordinateResultData> dataList = new ArrayList<>();
        GeoBaiduCoordinateServiceImpl baiduCoordinateService = (GeoBaiduCoordinateServiceImpl) geoCoordinates.get(0);
        GeoGaodeCoordinateServiceImpl gaodeCoordinateService = (GeoGaodeCoordinateServiceImpl) geoCoordinates.get(1);
        //如果是用Address查询，只返回一条数据
        if (StrUtil.isNotEmpty(address)) {
            //判断地址里是否包含 层、号、幢、楼、厦 如果包含认为是准确地址
            if (address.contains("层") || address.contains("楼") || address.contains("号") ||
                    address.contains("幢") || address.contains("厦")) {
                BaiduResponseData baiduResult = baiduCoordinateService.getCoordinateByAddress(address);
                if (ObjectUtil.isNotNull(baiduResult) && ObjectUtil.isNotNull(baiduResult.getResult())) {
                    dataList.add(singleCoordinate(baiduResult, address));
                    return dataList;
                }
                GaodeResponseData gaodeResult = gaodeCoordinateService.getCoordinateByAddress(address);
                if (ObjectUtil.isNotNull(gaodeResult) && CollectionUtil.isNotEmpty(gaodeResult.getGeocodes())) {
                    dataList.add(singleCoordinate(gaodeResult, address));
                    return dataList;
                }
            }
        }
        //使用城市和公司名查询时，返回1或者多条数据
        BaiduMultipleResponseData baiduMultipleData = baiduCoordinateService.getCoordinateByCompanyName(companyName, city, PAGE_SIZE, PAGE);
        if (ObjectUtil.isNotNull(baiduMultipleData) && baiduMultipleData.getTotal() > 0 && baiduMultipleData.getResults().get(0).getLocation() != null) {
            List<BaiduLocationResultBean> locationResultBeans = new ArrayList<>();
            baiduMultipleData.getResults().forEach(multipleResponseBean -> locationResultBeans.add(multipleResponseBean.getLocation()));
            return getMultipleBaiduCoordinate(locationResultBeans, companyName);
        }
        //高德是从第一页开始，真奇葩
        GaodeMultipleResponseData gaodeMultipleResponseData = gaodeCoordinateService.getCoordinateByCompanyName(companyName, city, PAGE_SIZE, PAGE + 1);
        if (ObjectUtil.isNotNull(gaodeMultipleResponseData) && CollectionUtil.isNotEmpty(gaodeMultipleResponseData.getPois())) {
            List<GaodeMultipleResponseBean> multipleResponseBeans = new ArrayList<>(gaodeMultipleResponseData.getPois());
            return getMultipleGaodeCoordinate(multipleResponseBeans, companyName);
        }
        return getNoneCoordinate(companyName);
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
        GeoBaiduCoordinateServiceImpl baiduCoordinateService = (GeoBaiduCoordinateServiceImpl) geoCoordinates.get(0);
        BaiduResponseData baiduResult = baiduCoordinateService.getCoordinateByAddress(address);
        if (ObjectUtil.isNotNull(baiduResult) && ObjectUtil.isNotNull(baiduResult.getResult())) {
            list.add(baiduResult.getResult().getLocation().getCoordinate());
            return list;
        }
        GeoGaodeCoordinateServiceImpl gaodeCoordinateService = (GeoGaodeCoordinateServiceImpl) geoCoordinates.get(1);
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
        GeoBaiduCoordinateServiceImpl baiduCoordinateService = (GeoBaiduCoordinateServiceImpl) geoCoordinates.get(0);
        BaiduMultipleResponseData baiduMultipleData = baiduCoordinateService.getCoordinateByCompanyName(companyName, city, PAGE_SIZE, PAGE);
        if (ObjectUtil.isNotNull(baiduMultipleData) && baiduMultipleData.getTotal() > 0 && baiduMultipleData.getResults().get(0).getLocation() != null) {
            baiduMultipleData.getResults().forEach(multipleResponseBean -> list.add(multipleResponseBean.getLocation().getCoordinate()));
            return list;
        }
        GeoGaodeCoordinateServiceImpl gaodeCoordinateService = (GeoGaodeCoordinateServiceImpl) geoCoordinates.get(1);
        GaodeMultipleResponseData gaodeMultipleData = gaodeCoordinateService.getCoordinateByCompanyName(companyName, city, PAGE_SIZE, PAGE + 1);
        if (ObjectUtil.isNotNull(gaodeMultipleData) && CollectionUtil.isNotEmpty(gaodeMultipleData.getPois())) {
            gaodeMultipleData.getPois().forEach(multipleResponseBean -> list.add(ConvertBaiduCoordinateUtil.convertBaiduCoordinate(multipleResponseBean.getLocation())));
            return list;
        }
        return list;
    }


    /**
     * 根据Address获取到的单个坐标
     *
     * @param responseValue 参数
     * @param address     地址
     * @return 结果
     */
    private CoordinateResultData singleCoordinate(ResponseValue responseValue, String address) {
        CoordinateResultData resultData = new CoordinateResultData();
        resultData.setStatus(NORMAL_ADDRESS);
        resultData.setQueryCondition(QUERY_ADDRESS);
        resultData.setQueryConditionValue(address);
        resultData.setAccuracy(CONFIRM_ACCURAY);

        if (responseValue instanceof BaiduResponseData) {
            BaiduResponseData baiduResponseData = (BaiduResponseData) responseValue;
            resultData.setSource(BAIDU_SOURCE);
            resultData.setLevel(baiduResponseData.getResult().getLevel());
            resultData.setBaiduCoordinate(baiduResponseData.getResult().getLocation().getCoordinate());
        } else if (responseValue instanceof GaodeResponseData) {
            GaodeResponseData gaodeResponseData = (GaodeResponseData) responseValue;
            resultData.setSource(GAODE_SOURCE);
            resultData.setGaodeCoordinate(gaodeResponseData.getGeocodes().get(0).getLocation());
            String location = ConvertBaiduCoordinateUtil.convertBaiduCoordinate(resultData.getGaodeCoordinate());
            resultData.setBaiduCoordinate(location);
        }

        return resultData;
    }

    /**
     * 获取多个百度坐标
     *
     * @param companyName companyName
     * @return 结果
     */
    private List<CoordinateResultData> getMultipleBaiduCoordinate(List<BaiduLocationResultBean> baiduMultipleDatas, String companyName) {
        List<CoordinateResultData> coordinateEntities = new ArrayList<>();
        if (baiduMultipleDatas.size() == 1) {
            CoordinateResultData resultData = parseCoordinateResultData(companyName, true);
            resultData.setSource(BAIDU_SOURCE);
            resultData.setBaiduCoordinate(baiduMultipleDatas.get(0).getCoordinate());
            coordinateEntities.add(resultData);
            return coordinateEntities;
        }
        for (BaiduLocationResultBean baiduLocationResultBean : baiduMultipleDatas) {
            CoordinateResultData resultData = parseCoordinateResultData(companyName, false);
            resultData.setSource(BAIDU_SOURCE);
            resultData.setBaiduCoordinate(baiduLocationResultBean.getCoordinate());
            coordinateEntities.add(resultData);
        }
        return coordinateEntities;
    }

    /**
     * 获得通用的返回结果
     *
     * @param companyName 公司名字
     * @param single      布尔值
     * @return 结果
     */
    private CoordinateResultData parseCoordinateResultData(String companyName, boolean single) {
        CoordinateResultData resultData = new CoordinateResultData();
        if (single) {
            resultData.setStatus(NORMAL_ADDRESS);
            resultData.setAccuracy(MAYBE_ACCURAY);
        } else {
            resultData.setStatus(MORE_ADDRESS);
            resultData.setAccuracy(CONFIRM_ACCURAY);
        }
        resultData.setQueryCondition(QUERY_COMPANYNAME);
        resultData.setLevel(null);
        resultData.setQueryConditionValue(companyName);
        return resultData;
    }

    /**
     * 获取多个高德坐标或者没有
     *
     * @param companyName 公司名
     * @return 结果
     */
    private List<CoordinateResultData> getMultipleGaodeCoordinate(List<GaodeMultipleResponseBean> multipleResponseBeans, String companyName) {
        List<CoordinateResultData> coordinateEntities = new ArrayList<>();

        if (multipleResponseBeans.size() == 1) {
            CoordinateResultData resultData = parseCoordinateResultData(companyName, true);
            resultData.setSource(GAODE_SOURCE);
            resultData.setBaiduCoordinate(multipleResponseBeans.get(0).getLocation());
            coordinateEntities.add(resultData);
            return coordinateEntities;
        }
        for (GaodeMultipleResponseBean gaodeMultipleResponseBean : multipleResponseBeans) {
            CoordinateResultData resultData = parseCoordinateResultData(companyName, false);
            resultData.setSource(GAODE_SOURCE);
            resultData.setBaiduCoordinate(gaodeMultipleResponseBean.getLocation());
            coordinateEntities.add(resultData);
        }
        return coordinateEntities;
    }

    /**
     * 没有查到经纬度
     *
     * @param companyName 公司名称
     * @return 结果
     */
    private List<CoordinateResultData> getNoneCoordinate(String companyName) {
        List<CoordinateResultData> coordinateEntities = new ArrayList<>();
        CoordinateResultData resultData = new CoordinateResultData();
        resultData.setQueryCondition(QUERY_COMPANYNAME);
        resultData.setQueryConditionValue(companyName);
        resultData.setSource(GAODE_SOURCE);
        resultData.setStatus(NONE_ADDRESS);
        resultData.setAccuracy(NORELIABLE_ACCURAY);
        resultData.setLevel(null);
        coordinateEntities.add(resultData);
        return coordinateEntities;
    }
}
