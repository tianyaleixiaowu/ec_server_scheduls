package com.mindata.ecserver.global.http;

import java.util.HashMap;

/**
 * @author wuweifeng wrote on 2017/11/27.
 */
public interface RequestProperty {
    /**
     * 基础URL
     * @return
     * 基URL
     */
    String baseUrl();

    /**
     * 额外的headers
     * @return
     * headers
     */
    HashMap<String, Object> headers();
}
