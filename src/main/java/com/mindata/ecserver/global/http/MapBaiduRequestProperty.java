package com.mindata.ecserver.global.http;

import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author wuweifeng wrote on 2017/11/27.
 */
@Component
public class MapBaiduRequestProperty implements RequestProperty {
    @Override
    public String baseUrl() {
        return null;
    }

    @Override
    public HashMap<String, Object> headers() {
        return null;
    }
}
