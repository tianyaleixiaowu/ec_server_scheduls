package com.mindata.ecserver.global.http;

import com.mindata.ecserver.global.http.request.base.RequestProperty;
import com.mindata.ecserver.global.http.service.FetchPhoneHistoryService;
import com.mindata.ecserver.global.http.service.FetchSaleStateService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(getClass());

    public FetchPhoneHistoryService getFetchPhoneHistoryService(RequestProperty requestProperty) {
        return generateRetrofit(requestProperty).create(FetchPhoneHistoryService.class);
    }

    public FetchSaleStateService getFetchSaleStateService(RequestProperty requestProperty) {
        return generateRetrofit(requestProperty).create(FetchSaleStateService.class);
    }

    public Retrofit getRetrofit(RequestProperty requestProperty) {
        return generateRetrofit(requestProperty);
    }

    /**
     * 创建retrofit
     *
     * @param requestProperty 参数
     * @return retrofit客户端
     */

    private Retrofit generateRetrofit(RequestProperty requestProperty) {
        logger.info("发起retrofit请求，baseUrl是:" + requestProperty.baseUrl());
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
                            //设置connection为一次即关闭，
                            .addHeader("Connection", "close")
                            //.addHeader("Connection", "keep-alive")
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
                .writeTimeout(5, TimeUnit.MINUTES)
                //设置okhttp的连接池保活时间为1秒，它默认是5分钟，如果在5分钟内创建超过2000个连接就报内存溢出
                //.connectionPool(new ConnectionPool(5, 1, TimeUnit.SECONDS))
                .build();
    }
}
