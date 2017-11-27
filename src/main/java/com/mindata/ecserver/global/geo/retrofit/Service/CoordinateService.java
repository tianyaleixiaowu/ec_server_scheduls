package com.mindata.ecserver.global.geo.retrofit.Service;


import com.mindata.ecserver.global.geo.retrofit.model.response.CoordinateResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * @author hanliqiang wrote on 2017/11/24
 */
public interface CoordinateService {

    @GET("geocoder/v2/")
    Call<CoordinateResult> getCoordinateByAddress(@Query("address") String address, @Query("output") String output,
                                                  @Query("ak") String ak, @Query("sn") String sn);


    @GET("place/search/")
    Call<CoordinateResult> getCoordinateByCompany(@Query("query") String query, @Query("region") String region, @Query("output") String output,
                                                  @Query("ak") String ak);
}
