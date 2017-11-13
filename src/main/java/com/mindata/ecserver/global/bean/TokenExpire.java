package com.mindata.ecserver.global.bean;

/**
 * @author wuweifeng wrote on 2017/10/30.
 */
public class TokenExpire {
    private String token;
    /**
     * 超时时间
     */
    private Long expire;

    public TokenExpire() {
    }

    public TokenExpire(String token, Long expire) {
        this.token = token;
        this.expire = expire;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getExpire() {
        return expire;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
    }
}
