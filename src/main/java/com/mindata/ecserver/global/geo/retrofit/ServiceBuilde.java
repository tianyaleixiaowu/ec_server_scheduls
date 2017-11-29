package com.mindata.ecserver.global.geo.retrofit;

import com.mindata.ecserver.global.geo.retrofit.service.BaiduCoordinateService;
import com.mindata.ecserver.global.geo.retrofit.service.GaodeCoordinateService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author hanliqiang wrote on 2017/11/24
 */
@Component
public class ServiceBuilde {
    @Resource
    private RetrofitBuilde retrofitBuilde;

    public BaiduCoordinateService getCoordinateService() {
        return retrofitBuilde.getBaiduRetrofit().create(BaiduCoordinateService.class);
    }

    public GaodeCoordinateService getGaodeCoordinateService() {
        return retrofitBuilde.getGaodeRetrofit().create(GaodeCoordinateService.class);
    }

}
