package com.mindata.ecserver.global.geo.service;

import com.mindata.ecserver.global.geo.util.ConvertBaiduCoordinateUtil;
import com.mindata.ecserver.global.http.response.BaiduResponseData;
import com.mindata.ecserver.global.http.response.GaodeResponseData;
import com.mindata.ecserver.global.http.response.base.CoordinateResultData;
import com.mindata.ecserver.global.http.response.base.ResponseValue;

import java.util.Random;

import static com.mindata.ecserver.global.GeoConstant.*;

/**
 * @author wuweifeng wrote on 2017/12/13.
 */
public class BaseGeoCoordinateService  {

    /**
     * 根据Address获取到的单个坐标
     *
     * @param responseValue
     *         参数
     * @param address
     *         地址
     * @return 结果
     */
    protected CoordinateResultData singleCoordinate(ResponseValue responseValue, String address) {
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
     * 获得通用的返回结果
     *
     * @param parameter
     *         地址或公司名
     * @param single
     *         布尔值
     * @return 结果
     */
    protected CoordinateResultData parseCoordinateResultData(String parameter, boolean single) {
        CoordinateResultData resultData = new CoordinateResultData();
        if (single) {
            resultData.setStatus(NORMAL_ADDRESS);
            resultData.setAccuracy(CONFIRM_ACCURAY);
        } else {
            resultData.setStatus(MORE_ADDRESS);
            resultData.setAccuracy(MAYBE_ACCURAY);
        }
        resultData.setQueryCondition(QUERY_COMPANYNAME);
        resultData.setLevel(null);
        resultData.setQueryConditionValue(parameter);
        return resultData;
    }

    protected String getAK(String ak) {
        String[] aks = ak.split(",");
        return aks[new Random().nextInt(aks.length)];
    }
}
