package com.mindata.ecserver.global.http;


import com.mindata.ecserver.global.exception.EcException;
import com.mindata.ecserver.global.http.response.base.ResponseValue;
import org.springframework.stereotype.Component;
import retrofit2.Call;

import java.io.IOException;

/**
 * @author wuweifeng wrote on 2017/10/23.
 */
@Component
public class CallManager {
    private final static int SUCCESS = 200;

    public <T extends ResponseValue> ResponseValue execute(Call<T> call) throws IOException {
        return doExecute(call);
    }

    private <T extends ResponseValue> ResponseValue doExecute(Call<T> call) throws IOException {
        retrofit2.Response response = call.execute();
        //返回值
        int code = response.code();

        //网络请求失败直接抛异常
        if (SUCCESS != code) {
            throw new EcException(response.message());
        }

        return (T) response.body();
    }

}
