package com.mindata.ecserver.global.http.service;


import com.mindata.ecserver.global.http.response.BaiduMultipleResponseData;
import com.mindata.ecserver.global.http.response.BaiduResponseData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * @author hanliqiang wrote on 2017/11/24
 */
public interface BaiduCoordinateService {
    /**
     * 根据地址去百度map查询经纬度
     *
     * @param address 地址
     * @param output  输出格式
     * @param ak      百度ak
     * @return 结果
     */

    @GET("geocoder/v2/")
    Call<BaiduResponseData> getCoordinateByAddress(@Query("address") String address, @Query("output") String output,
                                                   @Query("ak") String ak);

    /**
     * 根据公司名称去百度查询经纬度
     *
     * @param query  这传的是公司名称
     * @param region 城市
     * @param output 输出格式
     * @param ak     百度ak
     * @return 结果
     */
    @GET("place/v2/search?")
    Call<BaiduMultipleResponseData> getCoordinateByCompany(@Query("query") String query, @Query("region") String region, @Query("page_size") Integer page_size,
                                                           @Query("page_num") Integer page_num, @Query("city_limit") boolean city_limit, @Query("output") String output, @Query("ak") String ak);
}
