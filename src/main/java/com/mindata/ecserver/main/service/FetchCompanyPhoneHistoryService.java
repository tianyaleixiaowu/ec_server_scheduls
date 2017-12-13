package com.mindata.ecserver.main.service;

import com.mindata.ecserver.global.http.request.EcServerRequestProperty;
import com.mindata.ecserver.global.http.request.base.RequestProperty;
import com.mindata.ecserver.global.http.RetrofitServiceBuilder;
import com.mindata.ecserver.global.http.response.BaseData;
import com.mindata.ecserver.main.manager.PtUserManager;
import com.mindata.ecserver.main.manager.PtUserRoleManager;
import com.mindata.ecserver.main.model.secondary.PtPhoneHistoryCompany;
import com.mindata.ecserver.main.model.secondary.PtUserRole;
import com.mindata.ecserver.main.repository.secondary.PtPhoneHistoryCompanyRepository;
import com.mindata.ecserver.global.http.CallManager;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.util.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author wuweifeng wrote on 2017/11/7.
 */
@Service
public class FetchCompanyPhoneHistoryService {
    @Resource
    private CallManager callManager;
    @Resource
    private PtUserRoleManager ptUserRoleManager;
    @Resource
    private PtUserManager ptUserManager;
    @Resource
    private PtPhoneHistoryCompanyRepository ptPhoneHistoryCompanyRepository;

    @Value("${main.server-url}")
    private String serverUrl;
    @Resource
    private RetrofitServiceBuilder retrofitServiceBuilder;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public BaseData fetch() throws IOException {
        //先去redis校验该公司的manager缓存在不在，如果不在则去生成一个，完毕后清除掉
        List<PtUserRole> userRoles = ptUserRoleManager.findByRoleId(2L);

        //查最新的一条
        Pageable pageable = new PageRequest(0, 1);
        //有多个公司，就去访问多次
        for (PtUserRole userRole : userRoles) {
            //获取管理员的userId
            Long userId = userRole.getUserId();
            String token = ptUserManager.findTokenByUserId(userId);
            Long companyId = ptUserManager.findCompanyIdByUserId(userId);

            List<PtPhoneHistoryCompany> historyCompanyList = ptPhoneHistoryCompanyRepository
                    .findByCompanyIdOrderByStartTimeDesc(companyId, pageable);

            String beginTime;
            //如果该公司一条记录都没有
            if (CollectionUtil.isEmpty(historyCompanyList)) {
                beginTime = DateUtil.formatDate(DateUtil.lastWeek());
            } else {
                PtPhoneHistoryCompany historyCompany = historyCompanyList.get(0);
                //最后一条记录的时间
                Date beginDate = historyCompany.getStartTime();
                beginTime = DateUtil.formatDate(DateUtil.offsetDay(beginDate, 1));
            }
            String endTime = DateUtil.formatDate(DateUtil.yesterday());

            RequestProperty requestProperty = new EcServerRequestProperty(serverUrl, token);

            //得到返回值
            logger.info("公司id为" + companyId + "。开始获取通话历史，开始时间为" + beginTime + ",截止时间为" + endTime);
            BaseData baseData = (BaseData) callManager.execute(retrofitServiceBuilder.getFetchPhoneHistoryService(requestProperty).fetchHistory
                    (beginTime,
                    endTime));
            logger.info("返回的code" + baseData.getCode() + "----返回的message" + baseData.getMessage());
        }
        return null;
    }

}
