package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.global.cache.UserTokenCache;
import com.mindata.ecserver.global.util.CommonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2017/11/7.
 */
@Service
public class PtUserManager {
    @Resource
    private UserTokenCache userTokenCache;

    /**
     * 根据userId查询token
     * @param userId
     * userId
     * @return
     * token
     */
    public String findTokenByUserId(Integer userId) {
        String token = userTokenCache.getTokenByUserId(userId);
        if (token == null) {
            token = CommonUtil.token();
            userTokenCache.setBothTokenByUserId(token, userId);
        }
        return token;
    }

}
