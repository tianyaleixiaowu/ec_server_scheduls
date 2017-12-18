package com.mindata.ecserver.global.coordinate.service;

import com.mindata.ecserver.global.coordinate.http.response.BaiduLocationResultBean;
import com.mindata.ecserver.global.coordinate.http.response.GaodeMultipleResponseBean;
import com.mindata.ecserver.global.coordinate.http.response.base.CoordinateResultData;
import com.mindata.ecserver.global.coordinate.http.util.ConvertBaiduCoordinateUtil;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mindata.ecserver.global.GeoConstant.*;

/**
 * @author hanliqiang wrote on 2017/12/14
 */
@Service
public class ResponseDataService {
    /**
     * 获取百度坐标
     *
     * @param parameter parameter
     * @return 结果
     */
    public CoordinateResultData getBaiduCoordinate(List<BaiduLocationResultBean> baiduMultipleDatas, String parameter) {
        if (baiduMultipleDatas.size() == 1) {
            CoordinateResultData resultData = parseCoordinateResultData(parameter, true);
            resultData.setSource(BAIDU_SOURCE);
            resultData.setBaiduCoordinate(baiduMultipleDatas.get(0).getCoordinate());
            return resultData;
        }
        CoordinateResultData resultData = parseCoordinateResultData(parameter, false);
        resultData.setSource(BAIDU_SOURCE);
        resultData.setBaiduCoordinate(baiduMultipleDatas.get(0).getCoordinate());
        return resultData;
    }

    /**
     * 获得通用的返回结果
     *
     * @param parameter 地址或公司名
     * @param single    布尔值
     * @return 结果
     */
    private CoordinateResultData parseCoordinateResultData(String parameter, boolean single) {
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

    /**
     * 获取高德坐标
     *
     * @param parameter 地址或公司名
     * @return 结果
     */
    public CoordinateResultData getGaodeCoordinate(List<GaodeMultipleResponseBean> multipleResponseBeans, String parameter) {
        if (multipleResponseBeans.size() == 1) {
            CoordinateResultData resultData = parseCoordinateResultData(parameter, true);
            resultData.setSource(GAODE_SOURCE);
            resultData.setGaodeCoordinate(multipleResponseBeans.get(0).getLocation());
            String location = ConvertBaiduCoordinateUtil.convertBaiduCoordinate(resultData.getGaodeCoordinate());
            resultData.setBaiduCoordinate(location);
            return resultData;
        }
        CoordinateResultData resultData = parseCoordinateResultData(parameter, false);
        resultData.setSource(GAODE_SOURCE);
        resultData.setGaodeCoordinate(multipleResponseBeans.get(0).getLocation());
        String location = ConvertBaiduCoordinateUtil.convertBaiduCoordinate(resultData.getGaodeCoordinate());
        resultData.setBaiduCoordinate(location);
        return resultData;
    }

    /**
     * 没有获取到数据
     *
     * @param companyName 公司名称
     * @return 结果
     */
    public CoordinateResultData getNoneCoordinate(String companyName) {
        CoordinateResultData resultData = new CoordinateResultData();
        resultData.setQueryCondition(QUERY_COMPANYNAME);
        resultData.setQueryConditionValue(companyName);
        resultData.setSource(GAODE_SOURCE);
        resultData.setStatus(NONE_ADDRESS);
        resultData.setAccuracy(NORELIABLE_ACCURAY);
        resultData.setLevel(null);
        return resultData;
    }
}
