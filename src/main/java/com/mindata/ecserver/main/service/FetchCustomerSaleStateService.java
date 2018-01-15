package com.mindata.ecserver.main.service;

import com.mindata.ecserver.global.http.CallManager;
import com.mindata.ecserver.global.http.RetrofitServiceBuilder;
import com.mindata.ecserver.global.http.request.EcServerRequestProperty;
import com.mindata.ecserver.global.http.request.base.RequestProperty;
import com.mindata.ecserver.global.util.CommonUtil;
import com.mindata.ecserver.main.manager.PtUserManager;
import com.mindata.ecserver.main.model.secondary.PtUser;
import com.xiaoleilu.hutool.date.DateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;

/**
 * @author wuweifeng wrote on 2018/1/15.
 */
@Service
public class FetchCustomerSaleStateService {
    @Value("${main.server-url}")
    private String serverUrl;
    @Resource
    private RetrofitServiceBuilder retrofitServiceBuilder;
    @Resource
    private CallManager callManager;
    @Resource
    private PtUserManager ptUserManager;

    public void fetch() throws IOException {
        PtUser ptUser = ptUserManager.findFirstByCompanyId(1L);
        String token = ptUserManager.findTokenByUserId(ptUser.getId());
        RequestProperty requestProperty = new EcServerRequestProperty(serverUrl, token);
        Date monthBegin = DateUtil.beginOfMonth(CommonUtil.getNow());
        Date yesterday = DateUtil.yesterday();
        if (yesterday.before(monthBegin)) {
            yesterday = monthBegin;
        }
        callManager.execute(retrofitServiceBuilder.getFetchSaleStateService
                (requestProperty).saleState(DateUtil.formatDate(monthBegin), DateUtil.formatDate(yesterday)));
    }
}
