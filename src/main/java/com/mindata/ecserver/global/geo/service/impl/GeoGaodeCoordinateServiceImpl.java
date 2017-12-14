package com.mindata.ecserver.global.geo.service.impl;

import com.mindata.ecserver.global.geo.service.BaseGeoCoordinateService;
import com.mindata.ecserver.global.geo.service.IGeoCoordinateService;
import com.mindata.ecserver.global.geo.util.ConvertBaiduCoordinateUtil;
import com.mindata.ecserver.global.http.CallManager;
import com.mindata.ecserver.global.http.RetrofitServiceBuilder;
import com.mindata.ecserver.global.http.request.MapGaodeRquestProperty;
import com.mindata.ecserver.global.http.request.base.RequestProperty;
import com.mindata.ecserver.global.http.response.GaodeMultipleResponseBean;
import com.mindata.ecserver.global.http.response.GaodeMultipleResponseData;
import com.mindata.ecserver.global.http.response.GaodeResponseData;
import com.mindata.ecserver.global.http.response.base.CoordinateResultData;
import com.mindata.ecserver.global.http.service.GaodeCoordinateService;
import com.xiaoleilu.hutool.util.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mindata.ecserver.global.GeoConstant.GAODE_SOURCE;

/**
 * 获取高德地图的经纬度数据
 *
 * @author hanliqiang wrote on 2017/11/27
 */
@Order(1)
@Service
public class GeoGaodeCoordinateServiceImpl extends BaseGeoCoordinateService implements IGeoCoordinateService {
    @Resource
    private RetrofitServiceBuilder retrofitServiceBuilder;
    @Resource
    private CallManager callManager;
    @Value("${geo.gaode-url}")
    private String gaodeUrl;
    @Value("${geo.gaode-ak}")
    private String gaodeAK;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 根据地址获取高德地图的经纬度
     *
     * @param address 地址
     * @return 结果
     * @throws IOException 异常
     */
    @Override
    public CoordinateResultData getCoordinateByAddress(String address) throws IOException {
        RequestProperty requestProperty = new MapGaodeRquestProperty(gaodeUrl);
        GaodeCoordinateService gaodeCoordinateService = retrofitServiceBuilder.getGaodeCoordinateService(requestProperty);
        GaodeResponseData gaodeResponseData = (GaodeResponseData) callManager.execute(
                gaodeCoordinateService.getCoordinateByAddress(address, "json", getAK(gaodeAK)));

        logger.info("获取到高德返回的地址信息：" + gaodeResponseData);
        return singleCoordinate(gaodeResponseData, address);
    }

    /**
     * 根据公司名称或地址获取经纬度名称
     *
     * @param parameter 公司名字或地址
     * @param city        城市
     * @return 结果
     * @throws IOException 异常
     */
    @Override
    public List<CoordinateResultData> getCoordinateByParameter(String parameter, String city, Integer pageSize, Integer page) throws IOException {
        RequestProperty requestProperty = new MapGaodeRquestProperty(gaodeUrl);
        GaodeCoordinateService gaodeCoordinateService = retrofitServiceBuilder.getGaodeCoordinateService(requestProperty);
        GaodeMultipleResponseData gaodeMultipleResponseData = (GaodeMultipleResponseData) callManager.execute(
                gaodeCoordinateService.getCoordinateByParameter(parameter, city, pageSize, page + 1, true, "json",
                        getAK(gaodeAK)));

        logger.info("获取到高德返回的地址信息：" + gaodeMultipleResponseData);
        return getMultipleGaodeCoordinate(gaodeMultipleResponseData.getPois(), parameter);
    }

    /**
     * 获取多个高德坐标
     *
     * @param parameter
     *         地址或公司名
     * @return 结果
     */
    private List<CoordinateResultData> getMultipleGaodeCoordinate(List<GaodeMultipleResponseBean> multipleResponseBeans, String parameter) {
        List<CoordinateResultData> coordinateEntities = new ArrayList<>();
        if (CollectionUtil.isEmpty(multipleResponseBeans)) {
            return coordinateEntities;
        }
        if (multipleResponseBeans.size() == 1) {
            CoordinateResultData resultData = parseCoordinateResultData(parameter, true);
            resultData.setSource(GAODE_SOURCE);
            resultData.setGaodeCoordinate(multipleResponseBeans.get(0).getLocation());
            String location = ConvertBaiduCoordinateUtil.convertBaiduCoordinate(resultData.getGaodeCoordinate());
            resultData.setBaiduCoordinate(location);
            coordinateEntities.add(resultData);
            return coordinateEntities;
        }
        for (GaodeMultipleResponseBean gaodeMultipleResponseBean : multipleResponseBeans) {
            CoordinateResultData resultData = parseCoordinateResultData(parameter, false);
            resultData.setSource(GAODE_SOURCE);
            resultData.setGaodeCoordinate(gaodeMultipleResponseBean.getLocation());
            String location = ConvertBaiduCoordinateUtil.convertBaiduCoordinate(resultData.getGaodeCoordinate());
            resultData.setBaiduCoordinate(location);
            coordinateEntities.add(resultData);
        }
        return coordinateEntities;
    }
}
