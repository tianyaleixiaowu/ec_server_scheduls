package com.mindata.ecserver.retrofit.service;

import com.mindata.ecserver.main.BaseData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author wuweifeng wrote on 2017/10/23.
 */
public interface FetchPhoneHistoryService {
    /**
     * 获取公司历史通话统计
     */
    @GET("historyStatis/company")
    Call<BaseData> fetchHistory(@Query("begin") String begin, @Query("end") String end);
}
