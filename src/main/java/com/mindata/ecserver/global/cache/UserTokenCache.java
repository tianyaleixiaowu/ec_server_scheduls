package com.mindata.ecserver.global.cache;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

import static com.mindata.ecserver.global.cache.CacheConstant.CACHE_USER_HEADER_TOKEN_EXPIE;
import static com.mindata.ecserver.global.cache.CacheConstant.CACHE_USER_TOKEN_ID_KEY;
import static com.mindata.ecserver.global.cache.CacheConstant.CACHE_USER_TOKEN_KEY;


/**
 * @author wuweifeng wrote on 2017/10/30.
 * 用户登录token的缓存管理
 */
@Component
public class UserTokenCache {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public void setBothTokenByUserId(String token, Long userId) {
        stringRedisTemplate.opsForValue().set(keyOfUserId(userId), token, CACHE_USER_HEADER_TOKEN_EXPIE, TimeUnit
                .SECONDS);
        stringRedisTemplate.opsForValue().set(keyOfToken(token), userId + "", CACHE_USER_HEADER_TOKEN_EXPIE, TimeUnit
                .SECONDS);
    }

    public String getTokenByUserId(Long userId) {
        return stringRedisTemplate.opsForValue().get(keyOfUserId(userId));
    }

    public String getUserIdByToken(String token) {
        return stringRedisTemplate.opsForValue().get(keyOfToken(token));
    }

    public void deleteBothByUserId(Long userId) {
        stringRedisTemplate.delete(keyOfUserId(userId));
        stringRedisTemplate.delete(keyOfToken(getTokenByUserId(userId)));
    }

    public Long getExpire(Long userId) {
        return stringRedisTemplate.getExpire(keyOfUserId(userId));
    }

    private String keyOfUserId(Long userId) {
        return CACHE_USER_TOKEN_ID_KEY + "_" + userId;
    }

    private String keyOfToken(String token) {
        return CACHE_USER_TOKEN_KEY + "_" + token;
    }
}
