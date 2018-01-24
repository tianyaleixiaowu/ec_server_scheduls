package com.mindata.ecserver.main.service;

import com.mindata.ecserver.global.http.CallManager;
import com.mindata.ecserver.global.http.RetrofitServiceBuilder;
import com.mindata.ecserver.global.http.request.EcServerRequestProperty;
import com.mindata.ecserver.global.http.request.base.RequestProperty;
import com.mindata.ecserver.main.manager.PtUserManager;
import com.mindata.ecserver.main.model.secondary.PtUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author wuweifeng wrote on 2018/1/15.
 */
@Service
public class CompanyStateRefreshService {
    @Value("${main.server-url}")
    private String serverUrl;
    @Resource
    private RetrofitServiceBuilder retrofitServiceBuilder;
    @Resource
    private CallManager callManager;
    @Resource
    private PtUserManager ptUserManager;

    public void refresh() throws IOException {
        PtUser ptUser = ptUserManager.findFirstByCompanyId(1L);
        String token = ptUserManager.findTokenByUserId(ptUser.getId());
        RequestProperty requestProperty = new EcServerRequestProperty(serverUrl, token);
        callManager.execute(retrofitServiceBuilder.getCompanyStateRefreshService
                (requestProperty).refreshState());
    }
}
