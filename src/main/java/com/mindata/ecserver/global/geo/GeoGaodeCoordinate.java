package com.mindata.ecserver.global.geo;

import com.mindata.ecserver.global.geo.retrofit.CallBackManager;
import com.mindata.ecserver.global.geo.retrofit.ServiceBuilde;
import com.mindata.ecserver.global.geo.retrofit.model.response.CoordinateResult;
import com.mindata.ecserver.global.geo.retrofit.service.GaodeCoordinateService;
import com.xiaoleilu.hutool.util.ObjectUtil;
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
    private ServiceBuilde serviceBuilde;
    @Resource
    private CallBackManager callBackManager;

    @Override
    public CoordinateResult getCoordinateByAddress(String address) throws IOException {
        GaodeCoordinateService gaodeCoordinateService = serviceBuilde.getGaodeCoordinateService();
        CoordinateResult coordinateResult = (CoordinateResult) callBackManager.execute(
                gaodeCoordinateService.getCoordinateByAddress(address, OUTPUT_TYPE, GAODE_MAP_KEY));
        if (ObjectUtil.isNull(coordinateResult)) {
            return null;
        }
        return coordinateResult;
    }

    @Override
    public CoordinateResult getCoordinateByCompanyName(String companyName, String city) throws IOException {
        GaodeCoordinateService gaodeCoordinateService = serviceBuilde.getGaodeCoordinateService();
        CoordinateResult coordinateResult = (CoordinateResult) callBackManager.execute(
                gaodeCoordinateService.getCoordinateByCompany(companyName, city, OUTPUT_TYPE, GAODE_MAP_KEY));
        if (ObjectUtil.isNull(coordinateResult)) {
            return null;
        }
        return coordinateResult;
    }
}
