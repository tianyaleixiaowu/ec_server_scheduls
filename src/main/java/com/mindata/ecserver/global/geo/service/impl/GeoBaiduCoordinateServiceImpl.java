package com.mindata.ecserver.global.geo.service.impl;

import com.mindata.ecserver.global.geo.service.IGeoCoordinateService;
import com.mindata.ecserver.global.http.CallManager;
import com.mindata.ecserver.global.http.RetrofitServiceBuilder;
import com.mindata.ecserver.global.http.request.MapBaiduRequestProperty;
import com.mindata.ecserver.global.http.request.base.RequestProperty;
import com.mindata.ecserver.global.http.response.BaiduMultipleResponseData;
import com.mindata.ecserver.global.http.response.BaiduResponseData;
import com.mindata.ecserver.global.http.service.BaiduCoordinateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 获取百度地图api的数据
 *
 * @author hanliqiang wrote on 2017/11/27
 */
@Order(0)
@Service
public class GeoBaiduCoordinateServiceImpl implements IGeoCoordinateService {
    @Resource
    private RetrofitServiceBuilder retrofitServiceBuilder;
    @Resource
    private CallManager callManager;
    @Value("${geo.baidu-url}")
    private String baiduUrl;
    @Value("${geo.baidu-ak}")
    private String baiduAK;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 根据地址去百度地图获取经纬度
     *
     * @param address
     *         地址
     * @return 结果
     * @throws IOException
     *         异常
     */
    @Override
    public BaiduResponseData getCoordinateByAddress(String address) throws IOException {
        RequestProperty requestProperty = new MapBaiduRequestProperty(baiduUrl);
        BaiduCoordinateService baiduCoordinateService = retrofitServiceBuilder.getBaiduCoordinateService
                (requestProperty);
        BaiduResponseData baiduResponseData = (BaiduResponseData) callManager.execute(
                baiduCoordinateService.getCoordinateByAddress(address, "json", baiduAK));
        logger.info("获取到百度返回的地址信息：" + baiduResponseData);
        return baiduResponseData;
    }

    /**
     * 根据公司名称去百度地图获取经纬度
     *
     * @param companyName
     *         公司名字
     * @param city
     *         城市
     * @return 结果
     * @throws IOException
     *         异常
     */
    @Override
    public BaiduMultipleResponseData getCoordinateByCompanyName(String companyName, String city, Integer pageSize,
                                                                Integer page) throws IOException {
        RequestProperty requestProperty = new MapBaiduRequestProperty(baiduUrl);
        BaiduCoordinateService baiduCoordinateService = retrofitServiceBuilder.getBaiduCoordinateService
                (requestProperty);
        BaiduMultipleResponseData baiduMultipleResponseData = (BaiduMultipleResponseData) callManager.execute(
                baiduCoordinateService.getCoordinateByCompany(companyName, city, pageSize, page, true, "json",
                        baiduAK));
        logger.info("获取到百度返回的地址信息：" + baiduMultipleResponseData);
        return baiduMultipleResponseData;
    }
}
