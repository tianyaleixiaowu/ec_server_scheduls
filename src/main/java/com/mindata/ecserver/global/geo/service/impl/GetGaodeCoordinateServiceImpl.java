package com.mindata.ecserver.global.geo.service.impl;

import com.mindata.ecserver.global.geo.service.IGeoCoordinateService;
import com.mindata.ecserver.global.http.MapGaodeRquestProperty;
import com.mindata.ecserver.global.http.RequestProperty;
import com.mindata.ecserver.global.http.RetrofitServiceBuilder;
import com.mindata.ecserver.global.http.response.GaodeMultipleResponseData;
import com.mindata.ecserver.global.http.response.GaodeResponseData;
import com.mindata.ecserver.global.http.service.GaodeCoordinateService;
import com.mindata.ecserver.retrofit.CallManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

import static com.mindata.ecserver.global.Constant.GAODE_MAP_KEY;
import static com.mindata.ecserver.global.Constant.OUTPUT_TYPE;

/**
 * 获取高德地图的经纬度数据
 *
 * @author hanliqiang wrote on 2017/11/27
 */
@Order(1)
@Service
public class GetGaodeCoordinateServiceImpl implements IGeoCoordinateService {
    @Resource
    private RetrofitServiceBuilder retrofitServiceBuilder;
    @Resource
    private CallManager callManager;
    @Value("${main.gaode-url}")
    private String gaodeUrl;

    /**
     * 根据地址获取高德地图的经纬度
     *
     * @param address 地址
     * @return 结果
     * @throws IOException 异常
     */
    @Override
    public GaodeResponseData getCoordinateByAddress(String address) throws IOException {
        RequestProperty requestProperty = new MapGaodeRquestProperty(gaodeUrl);
        GaodeCoordinateService gaodeCoordinateService = retrofitServiceBuilder.getGaodeCoordinateService(requestProperty);
        GaodeResponseData gaodeResponseData = (GaodeResponseData) callManager.execute(
                gaodeCoordinateService.getCoordinateByAddress(address, OUTPUT_TYPE, GAODE_MAP_KEY));
        return gaodeResponseData;
    }

    /**
     * 根据公司名称获取经纬度名称
     *
     * @param companyName 公司名字
     * @param city        城市
     * @return 结果
     * @throws IOException 异常
     */
    @Override
    public GaodeMultipleResponseData getCoordinateByCompanyName(String companyName, String city, Integer pageSize, Integer page) throws IOException {
        RequestProperty requestProperty = new MapGaodeRquestProperty(gaodeUrl);
        GaodeCoordinateService gaodeCoordinateService = retrofitServiceBuilder.getGaodeCoordinateService(requestProperty);
        GaodeMultipleResponseData multipleResponseData = (GaodeMultipleResponseData) callManager.execute(
                gaodeCoordinateService.getCoordinateByCompany(companyName, city, pageSize, page, true, OUTPUT_TYPE, GAODE_MAP_KEY));
        return multipleResponseData;
    }
}
