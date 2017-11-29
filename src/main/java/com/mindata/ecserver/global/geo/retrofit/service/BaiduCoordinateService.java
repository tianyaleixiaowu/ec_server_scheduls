package com.mindata.ecserver.global.geo.retrofit.service;


import com.mindata.ecserver.global.geo.retrofit.model.response.CoordinateResult;
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
     * @param address
     * @param output
     * @param ak
     * @param sn
     * @return
     */

    @GET("geocoder/v2/")
    Call<CoordinateResult> getCoordinateByAddress(@Query("address") String address, @Query("output") String output,
                                                  @Query("ak") String ak, @Query("sn") String sn);

    /**
     * 根据公司名称去百度查询经纬度
     *
     * @param query
     * @param region
     * @param output
     * @param ak
     * @return
     */
    @GET("place/search?")
    Call<CoordinateResult> getCoordinateByCompany(@Query("query") String query, @Query("region") String region, @Query("output") String output,
                                                  @Query("ak") String ak);

    /**
     * 转换成百度坐标
     * @param coords  源坐标
     * @param from
     * @param to
     * @param ak
     * @param sn
     * @return
     */
    @GET("geoconv/v1/")
    Call<CoordinateResult> getBaiduCoordinate(@Query("coords") String coords, @Query("from") String from,
                                              @Query("to") String to, @Query("ak") String ak, @Query("sn") String sn);
}
