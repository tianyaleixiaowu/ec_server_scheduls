package com.mindata.ecserver.global.geo;

import com.mindata.ecserver.global.geo.service.BaiduCoordinateService;
import com.mindata.ecserver.global.http.MapBaiduRequestProperty;
import com.mindata.ecserver.global.http.RequestProperty;
import com.mindata.ecserver.global.http.RetrofitServiceBuilder;
import com.mindata.ecserver.global.http.response.BaiduMutilResponseData;
import com.mindata.ecserver.global.http.response.BaiduResponseData;
import com.mindata.ecserver.retrofit.CallManager;
import com.xiaoleilu.hutool.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static com.mindata.ecserver.global.Constant.BAIDU_MAP_AK;
import static com.mindata.ecserver.global.Constant.OUTPUT_TYPE;

/**
 * @author hanliqiang wrote on 2017/11/27
 */
@Order(0)
@Component
public class GeoBaiduCoordinate implements GeoCoordinate {
    @Resource
    private RetrofitServiceBuilder retrofitServiceBuilder;
    @Resource
    private CallManager callManager;
    @Value("${main.baidu-url}")
    private String baiduUrl;

    @Override
    public BaiduResponseData getCoordinateByAddress(String address) throws IOException {
        RequestProperty requestProperty = new MapBaiduRequestProperty(baiduUrl);
        BaiduCoordinateService baiduCoordinateService = retrofitServiceBuilder.getBaiduCoordinateService(requestProperty);
        BaiduResponseData baiduResponseData = (BaiduResponseData) callManager.execute(
                baiduCoordinateService.getCoordinateByAddress(address, OUTPUT_TYPE, BAIDU_MAP_AK));
        if (ObjectUtil.isNull(baiduResponseData)) {
            return null;
        }
        return baiduResponseData;
    }

    @Override
    public BaiduMutilResponseData getCoordinateByCompanyName(String companyName, String city) throws IOException {
        RequestProperty requestProperty = new MapBaiduRequestProperty(baiduUrl);
        BaiduCoordinateService baiduCoordinateService = retrofitServiceBuilder.getBaiduCoordinateService(requestProperty);
        BaiduMutilResponseData mutilResponseData = (BaiduMutilResponseData) callManager.execute(
                baiduCoordinateService.getCoordinateByCompany(companyName, city, OUTPUT_TYPE, BAIDU_MAP_AK));
        if (ObjectUtil.isNull(mutilResponseData)) {
            return null;
        }
        return mutilResponseData;
    }
}
