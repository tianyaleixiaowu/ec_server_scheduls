package com.mindata.ecserver.retrofit.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author wuweifeng wrote on 2017/10/23.
 */
public interface LoginService {
    /**
     * 获取token
     *
     * @return token
     */
    @POST("auth/accesstoken")
    Call<ResponseBody> accessToken(@Query("account") String account, @Query("password") String password);
}
