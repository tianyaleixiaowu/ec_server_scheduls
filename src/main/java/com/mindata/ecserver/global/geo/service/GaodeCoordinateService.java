package com.mindata.ecserver.global.geo.service;

import com.mindata.ecserver.global.http.response.GaodeResponseData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author hanliqiang wrote on 2017/11/27
 */
public interface GaodeCoordinateService {

    /**
     * 根据地址去高德查经纬度
     *
     * @param address 地址
     * @param output  输出格式
     * @param key     高德key
     * @return 结果
     */
    @GET("v3/geocode/geo")
    Call<GaodeResponseData> getCoordinateByAddress(@Query("address") String address, @Query("output") String output,
                                                   @Query("key") String key);

    /**
     * 根据公司名称去高德查经纬度
     *
     * @param companyName 公司名称
     * @param city        城市
     * @param output      输出格式
     * @param key         高德key
     * @return 结果
     */
    @GET("v3/geocode/geo")
    Call<GaodeResponseData> getCoordinateByCompany(@Query("address") String companyName, @Query("city") String city, @Query("output") String output,
                                                   @Query("key") String key);


}
