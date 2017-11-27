package com.mindata.ecserver.global;

import org.springframework.data.domain.Sort;

/**
 * @author wuweifeng wrote on 2017/11/9.
 */
public interface Constant {

    String ES_INDEX_NAME = "ec-server";
    String ES_TYPE_NAME = "contact";
    String ES_VOCATION_TYPE_NAME = "vocation";

    String ES_TYPE_AREA = "code-area";

    /**
     * 正常态
     */
    int STATE_NORMAL = 0;

    String DOUHAO = ",";

    Sort.Direction DIRECTION = Sort.Direction.DESC;

    String BAIDU_MAP_AK = "uDWUhXo4WDUecxSGD97bE1ztHtZKn2sW";

    String BAIDU_MAP_SK = "ksQopZsTHGGd6g6j6X8FQGRcxHYqvoQG";

    String BAIDU_MAP_API_URL="/geocoder/v2/?";

    String OUTPUT_TYPE = "json";
}
