package com.mindata.ecserver.global.http;

import com.mindata.ecserver.global.geo.service.BaiduCoordinateService;
import com.mindata.ecserver.global.geo.service.GaodeCoordinateService;
import com.mindata.ecserver.retrofit.service.FetchPhoneHistoryService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author wuweifeng wrote on 2017/11/27.
 * 网络请求工具类
 */
@Component
public class RetrofitServiceBuilder {

    public FetchPhoneHistoryService getFetchPhoneHistoryService(RequestProperty requestProperty) {
        return generateRetrofit(requestProperty).create(FetchPhoneHistoryService.class);
    }

    public BaiduCoordinateService getBaiduCoordinateService(RequestProperty requestProperty) {
        return generateRetrofit(requestProperty).create(BaiduCoordinateService.class);
    }

    public GaodeCoordinateService getGaodeCoordinateService(RequestProperty requestProperty) {
        return generateRetrofit(requestProperty).create(GaodeCoordinateService.class);
    }

    /**
     * 创建retrofit
     *
     * @param requestProperty 参数
     * @return retrofit客户端
     */

    private Retrofit generateRetrofit(RequestProperty requestProperty) {
        return new Retrofit.Builder()
                .baseUrl(requestProperty.baseUrl())
                .client(generateClient(requestProperty))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private OkHttpClient generateClient(RequestProperty requestProperty) {
        return new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request.Builder builder = chain.request()
                            .newBuilder()
                            .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                            //.addHeader("Accept-Encoding", "gzip, deflate")
                            .addHeader("Connection", "keep-alive")
                            .addHeader("Accept", "*/*")
                            .addHeader("Cookie", "add cookies here");
                    if (requestProperty.headers() != null) {
                        for (String key : requestProperty.headers().keySet()) {
                            builder.addHeader(key, requestProperty.headers().get(key).toString());
                        }
                    }

                    return chain.proceed(builder.build());
                }).connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES).build();
    }
}
