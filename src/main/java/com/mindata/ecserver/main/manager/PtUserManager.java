package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.global.cache.UserTokenCache;
import com.mindata.ecserver.global.util.CommonUtil;
import com.mindata.ecserver.main.repository.secondary.PtUserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2017/11/7.
 */
@Service
public class PtUserManager {
    @Resource
    private UserTokenCache userTokenCache;
    @Resource
    private PtUserRepository ptUserRepository;

    /**
     * 根据userId查询token
     * @param userId
     * userId
     * @return
     * token
     */
    public String findTokenByUserId(Long userId) {
        String token = userTokenCache.getTokenByUserId(userId);
        if (token == null) {
            token = CommonUtil.token();
            userTokenCache.setBothTokenByUserId(token, userId);
        }
        return token;
    }

    public Long findCompanyIdByUserId(Long userId) {
         return ptUserRepository.findOne(userId).getCompanyId();
    }

}
