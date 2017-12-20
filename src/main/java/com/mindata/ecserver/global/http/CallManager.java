package com.mindata.ecserver.global.http;


import com.mindata.ecserver.global.exception.EcException;
import com.mindata.ecserver.global.http.response.base.ResponseValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

/**
 * @author wuweifeng wrote on 2017/10/23.
 */
@Component
public class CallManager {
    private final static int SUCCESS = 200;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public <T extends ResponseValue> ResponseValue execute(Call<T> call) throws IOException {
        Response response = call.execute();
        //返回值
        int code = response.code();

        //网络请求失败直接抛异常
        if (SUCCESS != code) {
            throw new EcException(response.message());
        }

        return (T) response.body();
    }

    /**
     * 异步执行
     */
    public <T extends ResponseValue> void executeAsync(Call<T> call, GetDataBack<T> getDataBack) throws IOException {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.isSuccessful()) {
                    getDataBack.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable throwable) {
                logger.info("网络请求故障：" + throwable.getMessage());
            }
        });

    }
}
