package com.mindata.ecserver.global.geo.manager;

import com.mindata.ecserver.global.geo.util.ConvertBaiduCoordinateUtil;
import com.mindata.ecserver.global.http.response.*;
import com.mindata.ecserver.global.util.CommonUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.mindata.ecserver.global.Constant.*;

/**
 * @author hanliqiang wrote on 2017/12/11
 */
@Service
public class GeoCoordinateManager {
    /**
     * 获取单个百度坐标
     *
     * @param baiduResult 参数
     * @param address     地址
     * @return 结果
     */
    public CoordinateResultData getSingleBaiduCoordinate(BaiduResponseData baiduResult, String address) {
        CoordinateResultData resultData = new CoordinateResultData();
        resultData.setStatus(NORMAL_ADDRESS);
        resultData.setQueryCondition(QUERY_ADDRESS);
        resultData.setQueryConditionValue(address);
        resultData.setSource(BAIDU_SOURCE);
        resultData.setAccuracy(CONFIRM_ACCURAY);
        resultData.setLevel(baiduResult.getResult().getLevel());
        resultData.setBaiduCoordinate(baiduResult.getResult().getLocation().getCoordinate());
        resultData.setCreateTime(CommonUtil.getNow());
        return resultData;
    }

    /**
     * 获取单个高德坐标
     *
     * @param gaodeResult 参数
     * @param address     地址
     * @return 结果
     */
    public CoordinateResultData getSingleGaodeCoordinate(GaodeResponseData gaodeResult, String address) {
        CoordinateResultData resultData = new CoordinateResultData();
        resultData.setQueryCondition(QUERY_ADDRESS);
        resultData.setQueryConditionValue(address);
        resultData.setStatus(NORMAL_ADDRESS);
        resultData.setAccuracy(CONFIRM_ACCURAY);
        resultData.setSource(GAODE_SOURCE);
        resultData.setGaodeCoordinate(gaodeResult.getGeocodes().get(0).getLocation());
        String location = ConvertBaiduCoordinateUtil.convertBaiduCoordinate(resultData.getGaodeCoordinate());
        resultData.setBaiduCoordinate(location);
        return resultData;
    }

    /**
     * 获取多个百度坐标
     *
     * @param companyName companyName
     * @return 结果
     */
    public List<CoordinateResultData> getMultipleBaiduCoordinate(List<BaiduLocationResultBean> baiduMultipleDatas, String companyName) {
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
    public List<CoordinateResultData> getMultipleGaodeCoordinate(List<GaodeMultipleResponseBean> multipleResponseBeans, String companyName) {
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
    public List<CoordinateResultData> getNoneCoordinate(String companyName) {
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
