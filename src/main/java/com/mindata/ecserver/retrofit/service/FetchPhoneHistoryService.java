package com.mindata.ecserver.retrofit.service;

import com.mindata.ecserver.main.BaseData;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author wuweifeng wrote on 2017/10/23.
 */
public interface FetchPhoneHistoryService {
    /**
     * 获取公司历史通话统计
     */
    @POST("historyStatis/company")
    Call<BaseData> fetchHistory(@Query("begin") String begin, @Query("end") String end);
}
