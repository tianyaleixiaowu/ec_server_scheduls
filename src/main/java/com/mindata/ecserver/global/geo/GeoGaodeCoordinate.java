package com.mindata.ecserver.global.geo;

import com.mindata.ecserver.global.geo.service.GaodeCoordinateService;
import com.mindata.ecserver.global.http.MapGaodeRquestProperty;
import com.mindata.ecserver.global.http.RequestProperty;
import com.mindata.ecserver.global.http.RetrofitServiceBuilder;
import com.mindata.ecserver.global.http.response.GaodeResponseData;
import com.mindata.ecserver.retrofit.CallManager;
import com.xiaoleilu.hutool.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

import static com.mindata.ecserver.global.Constant.GAODE_MAP_KEY;
import static com.mindata.ecserver.global.Constant.OUTPUT_TYPE;

/**
 * @author hanliqiang wrote on 2017/11/27
 */
@Order(1)
@Service
public class GeoGaodeCoordinate implements GeoCoordinate {
    @Resource
    private RetrofitServiceBuilder retrofitServiceBuilder;
    @Resource
    private CallManager callManager;
    @Value("${main.gaode-url}")
    private String gaodeUrl;

    @Override
    public GaodeResponseData getCoordinateByAddress(String address) throws IOException {
        RequestProperty requestProperty = new MapGaodeRquestProperty(gaodeUrl);
        GaodeCoordinateService gaodeCoordinateService = retrofitServiceBuilder.getGaodeCoordinateService(requestProperty);
        GaodeResponseData gaodeResponseData = (GaodeResponseData) callManager.execute(
                gaodeCoordinateService.getCoordinateByAddress(address, OUTPUT_TYPE, GAODE_MAP_KEY));
        if (ObjectUtil.isNull(gaodeResponseData)) {
            return null;
        }
        return gaodeResponseData;
    }

    @Override
    public GaodeResponseData getCoordinateByCompanyName(String companyName, String city) throws IOException {
        RequestProperty requestProperty = new MapGaodeRquestProperty(gaodeUrl);
        GaodeCoordinateService gaodeCoordinateService = retrofitServiceBuilder.getGaodeCoordinateService(requestProperty);
        GaodeResponseData gaodeResponseData = (GaodeResponseData) callManager.execute(
                gaodeCoordinateService.getCoordinateByCompany(companyName, city, OUTPUT_TYPE, GAODE_MAP_KEY));
        if (ObjectUtil.isNull(gaodeResponseData)) {
            return null;
        }
        return gaodeResponseData;
    }
}
