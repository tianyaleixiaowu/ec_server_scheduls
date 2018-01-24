package com.mindata.ecserver.global.http.service;

import com.mindata.ecserver.global.http.response.BaseData;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author wuweifeng wrote on 2017/10/23.
 */
public interface CompanyStateRefreshService {
    /**
     * 获取公司历史通话统计
     */
    @GET("company/refreshState")
    Call<BaseData> refreshState();
}
