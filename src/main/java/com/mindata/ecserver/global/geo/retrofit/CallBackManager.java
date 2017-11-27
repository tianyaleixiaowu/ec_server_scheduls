package com.mindata.ecserver.global.geo.retrofit;


import com.mindata.ecserver.global.geo.retrofit.model.BaseResult;
import org.springframework.stereotype.Component;
import retrofit2.Call;

import java.io.IOException;

/**
 * @author wuweifeng wrote on 2017/10/23.
 */
@Component
public class CallBackManager {

    public <T extends BaseResult> BaseResult execute(Call<T> call) throws IOException {
        return doExecute(call);
    }

    private <T extends BaseResult> BaseResult doExecute(Call<T> call) throws IOException {
        retrofit2.Response response = call.execute();
        return (T) response.body();
    }
}
