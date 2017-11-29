package com.mindata.ecserver.global.geo;

import com.mindata.ecserver.global.geo.retrofit.CallBackManager;
import com.mindata.ecserver.global.geo.retrofit.ServiceBuilde;
import com.mindata.ecserver.global.geo.retrofit.model.response.CoordinateResult;
import com.mindata.ecserver.global.geo.retrofit.service.BaiduCoordinateService;
import com.mindata.ecserver.global.geo.util.Sncal;
import com.xiaoleilu.hutool.util.ObjectUtil;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

import static com.mindata.ecserver.global.Constant.BAIDU_MAP_AK;
import static com.mindata.ecserver.global.Constant.OUTPUT_TYPE;

/**
 * @author hanliqiang wrote on 2017/11/27
 */
@Order(0)
@Service
public class GeoBaiduCoordinate implements GeoCoordinate {
    @Resource
    private ServiceBuilde serviceBuilde;
    @Resource
    private CallBackManager callBackManager;

    @Override
    public CoordinateResult getCoordinateByAddress(String address) throws IOException {
        String snValue = Sncal.getSnValue(address);
        BaiduCoordinateService baiduCoordinateService = serviceBuilde.getCoordinateService();
        CoordinateResult coordinateResult = (CoordinateResult) callBackManager.execute(
                baiduCoordinateService.getCoordinateByAddress(address, OUTPUT_TYPE, BAIDU_MAP_AK, snValue));
        if (ObjectUtil.isNull(coordinateResult)) {
            return null;
        }
        return coordinateResult;
    }

    @Override
    public CoordinateResult getCoordinateByCompanyName(String companyName, String city) throws IOException {
        BaiduCoordinateService baiduCoordinateService = serviceBuilde.getCoordinateService();
        CoordinateResult coordinateResult = (CoordinateResult) callBackManager.execute(
                baiduCoordinateService.getCoordinateByCompany(companyName, city, OUTPUT_TYPE, BAIDU_MAP_AK));
        if (ObjectUtil.isNull(coordinateResult)) {
            return null;
        }
        return coordinateResult;
    }
}
