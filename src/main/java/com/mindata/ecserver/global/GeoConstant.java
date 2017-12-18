package com.mindata.ecserver.global;

/**
 * @author wuweifeng wrote on 2017/12/12.
 */
public interface GeoConstant extends Constant {

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

    String ADDRESS_ARRANGEMENT = "层";

    String ADDRESS_FLOOR = "楼";

    String ADDRESS_NUMBER = "号";

    String ADDRESS_BUILDING = "幢";

    String ADDRESS_MANSION = "厦";

    String OCTOTHORPE = "#";
}
