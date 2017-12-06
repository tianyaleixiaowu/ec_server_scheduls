package com.mindata.ecserver.global.geo;

import com.mindata.ecserver.global.http.response.ResponseValue;

import java.io.IOException;

/**
 * @author hanliqiang wrote on 2017/11/27
 */

public interface GeoCoordinateServiceImpl {

    /**
     * 根据地址获取经纬度
     *
     * @param address 地址
     * @return 结果
     * @throws IOException 异常
     */
    ResponseValue getCoordinateByAddress(String address) throws IOException;

    /**
     * 根据公司名称获取经纬度
     *
     * @param companyName 公司名字
     * @param city        城市
     * @return 结果
     * @throws IOException 异常
     */
    ResponseValue getCoordinateByCompanyName(String companyName, String city, Integer pageSize, Integer page) throws IOException;

}
