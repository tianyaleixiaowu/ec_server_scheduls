package com.mindata.ecserver.global;

import org.springframework.data.domain.Sort;

/**
 * @author wuweifeng wrote on 2017/11/9.
 */
public interface Constant {

    String ES_INDEX_NAME = "ec-server";
    String ES_TYPE_NAME = "contact";
    String ES_VOCATION_TYPE_NAME = "vocation";

    String ES_COORDINATE_TYPE_NAME = "coordinate";

    String ES_TYPE_AREA = "code-area";

    /**
     * 正常态
     */
    int STATE_NORMAL = 0;

    String DOUHAO = ",";

    Sort.Direction DIRECTION = Sort.Direction.DESC;

    String BAIDU_MAP_AK = "uDWUhXo4WDUecxSGD97bE1ztHtZKn2sW";

    String BAIDU_MAP_SK = "ksQopZsTHGGd6g6j6X8FQGRcxHYqvoQG";

    String BAIDU_MAP_API_URL = "/geocoder/v2/?";


    String ADDRESS_ARRANGEMENT = "层";

    String ADDRESS_FLOOR = "楼";

    String ADDRESS_NUMBER = "号";

    String ADDRESS_MANSION = "厦";
    /**
     * 1 查询条件是地址
     */
    int QUERY_ADDRESS = 1;
    /**
     * 2 查询条件是公司名称
     */
    int QUERY_COMPANYNAME = 2;
    /**
     * 0 正常
     */
    int NORMAL_ADDRESS = 0;
    /**
     * 1 多个地址
     */
    int MORE_ADDRESS = 1;
    /**
     * 2 无地址
     */
    int NONE_ADDRESS = 2;
    /**
     * 1 来源是百度
     */
    int BAIDU_SOURCE = 1;
    /**
     * 2 来源是高德
     */
    int GAODE_SOURCE = 2;
    /**
     * 1 准确度 确认
     */
    int CONFIRM_ACCURAY = 0;
    /**
     * 2 准确度 可能对
     */
    int MAYBE_ACCURAY = 1;
    /**
     * 3 准确度 不太靠谱
     */
    int NORELIABLE_ACCURAY = 2;
    /**
     * gcj02坐标
     */
    int BAIDU_FROM=3;
    /**
     * 百度地图采用的经纬度坐标;
     */
    int BAIDU_TO=5;

    String GAODE_MAP_KEY = "30a02f8c411cc8a6b828a1c4581e68d6";

    String OUTPUT_TYPE = "json";
}
