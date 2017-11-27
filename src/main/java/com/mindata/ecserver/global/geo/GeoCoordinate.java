package com.mindata.ecserver.global.geo;

import com.mindata.ecserver.global.geo.retrofit.model.BaseResult;

import java.io.IOException;

/**
 * @author hanliqiang wrote on 2017/11/27
 */

public interface GeoCoordinate {

    /**
     * 根据地址获取经纬度
     *
     * @param address
     * @return
     */
    BaseResult getCoordinateByAddress(String address) throws IOException;

    /**
     * 根据公司名称获取经纬度
     *
     * @param companyName
     * @return
     */
    BaseResult getCoordinateByCompanyName(String companyName, String city) throws IOException;

}
