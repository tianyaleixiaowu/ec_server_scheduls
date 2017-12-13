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

    String ES_GEO_INDEX_NAME = "geo";

    String ES_COORDINATE_TYPE_NAME = "coordinate";

    /**
     * 正常态
     */
    int STATE_NORMAL = 0;

    String DOUHAO = ",";

    Sort.Direction DIRECTION = Sort.Direction.DESC;
}
