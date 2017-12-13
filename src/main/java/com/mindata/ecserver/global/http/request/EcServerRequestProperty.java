package com.mindata.ecserver.global.http.request;

import com.mindata.ecserver.global.http.request.base.RequestProperty;

import java.util.HashMap;

/**
 * @author wuweifeng wrote on 2017/11/27.
 */
public class EcServerRequestProperty implements RequestProperty {

    private String token;
    private String serverUrl;

    public EcServerRequestProperty(String serverUrl, String token) {
        this.token = token;
        this.serverUrl = serverUrl;
    }

    @Override
    public String baseUrl() {
        return serverUrl;
    }

    @Override
    public HashMap<String, Object> headers() {
        HashMap<String, Object> map = new HashMap<>(1);
        map.put("token", token);
        return map;
    }
}
