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
     * 根据地址或公司名称去百度map查询经纬度
     *
     * @param query     这传的是公司名称或地址
     * @param region    城市
     * @param pageSize  pageSize
     * @param pageNum   pageNum
     * @param cityLimit true返回区域内的数据
     * @param output    输出格式
     * @param ak        ak
     * @return 结果
     */
    @GET("place/v2/search?")
    Call<BaiduMultipleResponseData> getCoordinateByParameter(@Query("query") String query, @Query("region") String region, @Query("page_size") Integer pageSize,
                                                             @Query("page_num") Integer pageNum, @Query("city_limit") boolean cityLimit, @Query("output") String output, @Query("ak") String ak);


}
