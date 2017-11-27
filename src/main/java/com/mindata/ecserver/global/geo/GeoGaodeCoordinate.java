package com.mindata.ecserver.global.geo;

import com.mindata.ecserver.global.geo.retrofit.model.response.CoordinateResult;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author hanliqiang wrote on 2017/11/27
 */
@Order(1)
@Service
public class GeoGaodeCoordinate implements GeoCoordinate {

    @Override
    public CoordinateResult getCoordinateByAddress(String address) {

        return null;
    }

    @Override
    public CoordinateResult getCoordinateByCompanyName(String companyName, String city) {
        return null;
    }
}
