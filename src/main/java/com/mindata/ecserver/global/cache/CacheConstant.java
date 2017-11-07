package com.mindata.ecserver.global.cache;

/**
 * @author wuweifeng wrote on 2017/11/3.
 */
public interface CacheConstant {
    /**
     * 记录登录密码输入错误次数
     */
    String CACHE_SHIRO_PASS_COUNT_KEY = "shiro_pass_count";
    /**
     * 密码错误状态保存10分钟，10分钟后就可以重新输入密码了
     */
    int CACHE_SHIRO_PASS_COUNT_EXPIE = 10;
    /**
     * shiro保存用户权限
     */
    String CACHE_SHIRO_USER_PERMISSION_KEY = "shiro_user_permission";
    /**
     * 权限保存10个小时，redis存储的user权限
     */
    int CACHE_SHIRO_USER_PERMISSION_EXPIE = 10;
    /**
     * token的key名，key为token
     */
    String CACHE_USER_TOKEN_KEY = "user_token_key";
    /**
     * token的key名，key为userId
     */
    String CACHE_USER_TOKEN_ID_KEY = "user_token_id_key";
    /**
     * 用户登录的token缓存1个小时
     */
    int CACHE_USER_HEADER_TOKEN_EXPIE = 3600;
    /**
     * role对应的菜单缓存的key
     */
    String CACHE_ROLE_MENU_KEY = "role_menu_key";

    /**
     * ec的token的key
     */
    String CACHE_EC_TOKEN_KEY = "ec_user_token_key";

    String CACHE_EC_HEADER_CROP_ID = "CORP_ID";
}
