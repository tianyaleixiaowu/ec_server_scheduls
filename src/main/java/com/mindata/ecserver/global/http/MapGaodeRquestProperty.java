package com.mindata.ecserver.global.http;

import java.util.HashMap;

/**
 * @author hanliqiang wrote on 2017/11/29
 */
public class MapGaodeRquestProperty implements RequestProperty {
    private String gaodeUrl;

    public MapGaodeRquestProperty(String gaodeUrl) {
        this.gaodeUrl = gaodeUrl;
    }

    @Override
    public String baseUrl() {
        return gaodeUrl;
    }

    @Override
    public HashMap<String, Object> headers() {
        return null;
    }
}
