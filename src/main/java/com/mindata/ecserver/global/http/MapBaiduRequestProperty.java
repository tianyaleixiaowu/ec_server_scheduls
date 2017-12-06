package com.mindata.ecserver.global.http;

import java.util.HashMap;

/**
 * @author wuweifeng wrote on 2017/11/27.
 */
public class MapBaiduRequestProperty implements RequestProperty {
    private String baiduUri;

    public MapBaiduRequestProperty(String baiduUri) {
        this.baiduUri = baiduUri;
    }

    @Override
    public String baseUrl() {
        return baiduUri;
    }

    @Override
    public HashMap<String, Object> headers() {
        return null;
    }
}
