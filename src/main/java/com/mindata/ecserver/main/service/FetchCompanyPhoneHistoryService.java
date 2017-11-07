package com.mindata.ecserver.main.service;

import com.mindata.ecserver.main.BaseData;
import com.mindata.ecserver.main.manager.PtUserManager;
import com.mindata.ecserver.main.manager.PtUserRoleManager;
import com.mindata.ecserver.main.model.secondary.PtUserRole;
import com.mindata.ecserver.retrofit.CallManager;
import com.mindata.ecserver.retrofit.RetrofitBuilder;
import com.xiaoleilu.hutool.date.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author wuweifeng wrote on 2017/11/7.
 */
@Service
public class FetchCompanyPhoneHistoryService {
    @Resource
    private CallManager callManager;
    @Resource
    private RetrofitBuilder retrofitBuilder;
    @Resource
    private PtUserRoleManager ptUserRoleManager;
    @Resource
    private PtUserManager ptUserManager;

    public BaseData fetch() throws IOException {
        //先去redis校验该公司的manager缓存在不在，如果不在则去生成一个，完毕后清除掉
        List<PtUserRole> userRoles = ptUserRoleManager.findByRoleId(2);
        //有多个公司，就去访问多次
        for (PtUserRole userRole : userRoles) {
            //获取管理员的userId
            Integer userId = userRole.getUserId();
            String token = ptUserManager.findTokenByUserId(userId);
            String yesterday = DateUtil.formatDate(DateUtil.yesterday());

            //得到返回值
            return   callManager.execute(retrofitBuilder.getFetchPhoneHistoryService(token).fetchHistory
                    (yesterday, yesterday));
        }
        return null;
    }
}
