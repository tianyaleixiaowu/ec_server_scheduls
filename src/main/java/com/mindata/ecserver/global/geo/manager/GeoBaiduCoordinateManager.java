package com.mindata.ecserver.global.geo.manager;

import com.mindata.ecserver.global.geo.GeoCoordinateServiceImpl;
import com.mindata.ecserver.global.http.MapBaiduRequestProperty;
import com.mindata.ecserver.global.http.RequestProperty;
import com.mindata.ecserver.global.http.RetrofitServiceBuilder;
import com.mindata.ecserver.global.http.response.BaiduMultipleResponseData;
import com.mindata.ecserver.global.http.response.BaiduResponseData;
import com.mindata.ecserver.global.http.service.BaiduCoordinateService;
import com.mindata.ecserver.retrofit.CallManager;
import com.xiaoleilu.hutool.util.CollectionUtil;
import com.xiaoleilu.hutool.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

import static com.mindata.ecserver.global.Constant.BAIDU_MAP_AK;
import static com.mindata.ecserver.global.Constant.OUTPUT_TYPE;

/**
 * 获取百度地图api的数据
 *
 * @author hanliqiang wrote on 2017/11/27
 */
@Order(0)
@Service
public class GeoBaiduCoordinateManager implements GeoCoordinateServiceImpl {
    @Resource
    private RetrofitServiceBuilder retrofitServiceBuilder;
    @Resource
    private CallManager callManager;
    @Value("${main.baidu-url}")
    private String baiduUrl;

    /**
     * 根据地址去百度地图获取经纬度
     *
     * @param address 地址
     * @return 结果
     * @throws IOException 异常
     */
    @Override
    public BaiduResponseData getCoordinateByAddress(String address) throws IOException {
        RequestProperty requestProperty = new MapBaiduRequestProperty(baiduUrl);
        BaiduCoordinateService baiduCoordinateService = retrofitServiceBuilder.getBaiduCoordinateService(requestProperty);
        BaiduResponseData baiduResponseData = (BaiduResponseData) callManager.execute(
                baiduCoordinateService.getCoordinateByAddress(address, OUTPUT_TYPE, BAIDU_MAP_AK));
        if (ObjectUtil.isNull(baiduResponseData) && ObjectUtil.isNull(baiduResponseData.getResult())) {
            return null;
        }
        return baiduResponseData;
    }

    /**
     * 根据公司名称去百度地图获取经纬度
     *
     * @param companyName 公司名字
     * @param city        城市
     * @return 结果
     * @throws IOException 异常
     */
    @Override
    public BaiduMultipleResponseData getCoordinateByCompanyName(String companyName, String city, Integer pageSize, Integer page) throws IOException {
        RequestProperty requestProperty = new MapBaiduRequestProperty(baiduUrl);
        BaiduCoordinateService baiduCoordinateService = retrofitServiceBuilder.getBaiduCoordinateService(requestProperty);
        BaiduMultipleResponseData multipleResponseData = (BaiduMultipleResponseData) callManager.execute(
                baiduCoordinateService.getCoordinateByCompany(companyName, city, pageSize, page, true, OUTPUT_TYPE, BAIDU_MAP_AK));
        if (ObjectUtil.isNull(multipleResponseData) && CollectionUtil.isEmpty(multipleResponseData.getResults())) {
            return null;
        }
        return multipleResponseData;
    }
}
