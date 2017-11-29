package com.mindata.ecserver.global.geo.retrofit.service;

import com.mindata.ecserver.global.geo.retrofit.model.response.CoordinateResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author hanliqiang wrote on 2017/11/27
 */
public interface GaodeCoordinateService {

    /**
     * 根据地址去高德查经纬度
     * @param address
     * @param output
     * @param key
     * @return
     */
    @GET("v3/geocode/geo")
    Call<CoordinateResult> getCoordinateByAddress(@Query("address") String address, @Query("output") String output,
                                                  @Query("key") String key);

    /**
     * 根据公司名称去高德查经纬度
     * @param companyName
     * @param city
     * @param output
     * @param key
     * @return
     */
    @GET("v3/geocode/geo")
    Call<CoordinateResult> getCoordinateByCompany(@Query("address") String companyName, @Query("city") String city, @Query("output") String output,
                                                  @Query("key") String key);


}
