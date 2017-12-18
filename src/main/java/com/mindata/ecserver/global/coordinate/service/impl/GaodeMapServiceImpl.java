package com.mindata.ecserver.global.coordinate.service.impl;

import com.mindata.ecserver.global.coordinate.http.response.GaodeMultipleResponseBean;
import com.mindata.ecserver.global.coordinate.http.response.GaodeMultipleResponseData;
import com.mindata.ecserver.global.coordinate.http.response.base.CoordinateResultData;
import com.mindata.ecserver.global.coordinate.http.util.RestTemplateUtil;
import com.mindata.ecserver.global.coordinate.service.ICoordinateService;
import com.mindata.ecserver.global.coordinate.service.ResponseDataService;
import com.xiaoleilu.hutool.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mindata.ecserver.global.GeoConstant.*;

/**
 * @author hanliqiang wrote on 2017/12/14
 */
@Service
public class GaodeMapServiceImpl implements ICoordinateService {
    private static final int PAGE_SIZE = 2;
    private static final int PAGE = 1;
    private static final String KEY = "30a02f8c411cc8a6b828a1c4581e68d6";
    private static final String URL = "http://restapi.amap.com/v3/place/text";
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 根据地址获取高德经纬度
     *
     * @param address 参数(地址)
     * @param city    城市
     * @param inner   true 数据库访问  false 外部接口访问
     * @return 结果
     */
    @Override
    public CoordinateResultData getCoordinateByAddress(String address, String city, boolean inner) {
        ResponseDataService responseDataService = new ResponseDataService();
        List<GaodeMultipleResponseBean> multipleResponseBeans;
        GaodeMultipleResponseData gaodeMultipleResponseData = null;
        // true的话 是数据库访问 判断地址是否是准确地址
        if (inner) {
            if (address.contains(ADDRESS_ARRANGEMENT) || address.contains(ADDRESS_FLOOR) || address.contains(ADDRESS_NUMBER) ||
                    address.contains(ADDRESS_BUILDING) || address.contains(ADDRESS_MANSION)) {
                gaodeMultipleResponseData = getCoordinateByParameter(address, city);
            }
            // false的话 是外部接口访问不需要判断是否为准确地址
        } else {
            gaodeMultipleResponseData = getCoordinateByParameter(address, city);
        }
        // 判断拿到的数据是否为空
        if (gaodeMultipleResponseData != null && gaodeMultipleResponseData.getPois().size() > 0) {
            logger.info("根据地址去查高德经纬度为" + gaodeMultipleResponseData.toString());
            multipleResponseBeans = gaodeMultipleResponseData.getPois();
            return responseDataService.getGaodeCoordinate(multipleResponseBeans, address);
        }
        return null;
    }

    /**
     * 根据公司去获得经纬度
     *
     * @param company 公司
     * @param city    城市
     * @return 结果
     */
    @Override
    public CoordinateResultData getCoordinateByCompany(String company, String city) {
        ResponseDataService responseDataService = new ResponseDataService();
        GaodeMultipleResponseData gaodeMultipleResponseData = getCoordinateByParameter(company, city);
        logger.info("根据公司去查高德经纬度为" + gaodeMultipleResponseData.toString());
        List<GaodeMultipleResponseBean> multipleResponseBeans;
        if ((ObjectUtil.isNotNull(gaodeMultipleResponseData) && gaodeMultipleResponseData.getPois().size() > 0)) {
            multipleResponseBeans = gaodeMultipleResponseData.getPois();
            return responseDataService.getGaodeCoordinate(multipleResponseBeans, company);
        }
        return null;
    }

    /**
     * 发送获取经纬度
     *
     * @param parameter 参数(公司或地址)
     * @param city      城市
     * @return 结果
     */
    public GaodeMultipleResponseData getCoordinateByParameter(String parameter, String city) {
        String param = "?keywords=" + parameter + "&city=" + city + "&offset=" + PAGE_SIZE + "&page=" + PAGE +
                "&citylimit=" + true + "&output=" + "json" + "&key=" + KEY;
        return RestTemplateUtil.restTemplate().getForObject(URL + param, GaodeMultipleResponseData.class);
    }
}
