package com.mindata.ecserver.global.geo;

import com.mindata.ecserver.global.geo.retrofit.model.BaseResult;
import com.mindata.ecserver.global.geo.retrofit.model.response.CoordinateResult;
import com.xiaoleilu.hutool.util.CollectionUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author hanliqiang wrote on 2017/11/27
 */
@Component
public class InvokerClass {
    @Resource
    List<GeoCoordinate> geoCoordinates;

    public BaseResult getLocation(String address) throws IOException {
        CoordinateResult baiduResult = (CoordinateResult) geoCoordinates.get(0).getCoordinateByAddress(address);
        if (baiduResult.getResult() != null) {

            return baiduResult;
        }
        return null;
    }
}
