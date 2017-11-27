package com.mindata.ecserver.global.geo.retrofit;

import com.mindata.ecserver.global.geo.retrofit.Service.CoordinateService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author hanliqiang wrote on 2017/11/24
 */
@Component
public class ServiceBuilde {
    @Resource
    private RetrofitBuilde retrofitBuilde;

    public CoordinateService getCoordinateService() {
        return retrofitBuilde.getRetrofit().create(CoordinateService.class);
    }
}
