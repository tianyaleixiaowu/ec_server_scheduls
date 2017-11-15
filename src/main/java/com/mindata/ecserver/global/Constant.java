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

    String DOUHAO = ",";
    int PAGE_SIZE = 10;
    int PAGE_NUM = 0;

    Sort.Direction DIRECTION = Sort.Direction.DESC;
}
