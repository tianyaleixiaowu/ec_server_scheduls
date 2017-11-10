package com.mindata.ecserver.retrofit;

import com.mindata.ecserver.retrofit.service.FetchPhoneHistoryService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author wuweifeng wrote on 2017/10/20.
 * 通过retrofit访问后台服务器
 */
@Configuration
public class RetrofitBuilder {

    @Value("${main.server-url}")
    private String serverUrl;

    public FetchPhoneHistoryService getFetchPhoneHistoryService(String token) {
        return getRetrofit(token).create(FetchPhoneHistoryService.class);
    }

    private Retrofit getRetrofit(String token) {
        return new Retrofit.Builder()
                .baseUrl(serverUrl)
                .client(generateClient(token))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    
    private OkHttpClient generateClient(String token) {
        return new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request()
                            .newBuilder()
                            .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                            //.addHeader("Accept-Encoding", "gzip, deflate")
                            .addHeader("Connection", "keep-alive")
                            .addHeader("Accept", "*/*")
                            .addHeader("Cookie", "add cookies here")
                            .addHeader("token", token)
                            .build();
                    return chain.proceed(request);
                }).connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES).build();
    }

}
