package com.mindata.ecserver.global.geo;

import com.mindata.ecserver.global.geo.retrofit.ServiceBuilde;
import com.mindata.ecserver.global.geo.retrofit.service.BaiduCoordinateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author hanliqiang wrote on 2017/11/29
 */
@Service
public class ConvertBaiduCoordinate {
    @Resource
    private ServiceBuilde serviceBuilde;

    public void convertBaiduCoordinate(Float longitude, Float latitude) {
        BaiduCoordinateService coordinateService = serviceBuilde.getCoordinateService();

    }
}
