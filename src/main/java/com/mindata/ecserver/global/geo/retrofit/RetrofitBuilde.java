package com.mindata.ecserver.global.geo.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author wuweifeng wrote on 2017/10/20.
 */
@Configuration
public class RetrofitBuilde {

    private String baiduUri = "http://api.map.baidu.com/";

    private String gaodeUrl = "http://restapi.amap.com";

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    public Retrofit getBaiduRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(baiduUri)
                .client(generClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public Retrofit getGaodeRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(gaodeUrl)
                .client(generClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    private OkHttpClient generClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request()
                            .newBuilder()
                            .addHeader("Connection", "keep-alive")
                            .addHeader("Accept", "*/*")
                            .addHeader("Cookie", "add cookies here")
                            .build();
                    return chain.proceed(request);
                }).build();
    }

}
