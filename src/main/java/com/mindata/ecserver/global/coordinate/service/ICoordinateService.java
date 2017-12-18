package com.mindata.ecserver.global.coordinate.service;

import com.mindata.ecserver.global.coordinate.http.response.base.CoordinateResultData;

/**
 * @author hanliqiang wrote on 2017/12/14
 */
public interface ICoordinateService {
    /**
     * 根据地址获取经纬度
     *
     * @param city 城市
     * @return 结果
     */
    CoordinateResultData getCoordinateByAddress(String address, String city, boolean inner);

    /**
     *
     * @param company 公司
     * @param city 城市
     * @return 结果
     */
    CoordinateResultData getCoordinateByCompany(String company, String city);

}
