package com.mindata.ecserver.global.coordinate.service.impl;

import com.mindata.ecserver.global.coordinate.http.response.BaiduLocationResultBean;
import com.mindata.ecserver.global.coordinate.http.response.BaiduMultipleResponseData;
import com.mindata.ecserver.global.coordinate.http.response.base.CoordinateResultData;
import com.mindata.ecserver.global.coordinate.http.util.RestTemplateUtil;
import com.mindata.ecserver.global.coordinate.service.ICoordinateService;
import com.mindata.ecserver.global.coordinate.service.ResponseDataService;
import com.xiaoleilu.hutool.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.mindata.ecserver.global.GeoConstant.*;

/**
 * @author hanliqiang wrote on 2017/12/14
 */
@Service
public class BaiduMapServiceImpl implements ICoordinateService {
    private static final int PAGE_SIZE = 2;
    private static final int PAGE = 0;
    private static final String AK = "uDWUhXo4WDUecxSGD97bE1ztHtZKn2sW";
    private static final String URL = "http://api.map.baidu.com/place/v2/search";
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 根据地址获取坐标
     *
     * @param address 地址
     * @param city    城市
     * @param inner   true 数据库访问  false 外部接口访问
     * @return 结果
     */
    @Override
    public CoordinateResultData getCoordinateByAddress(String address, String city, boolean inner) {
        ResponseDataService responseDataService = new ResponseDataService();
        List<BaiduLocationResultBean> locationResultBeans = new ArrayList<>();
        BaiduMultipleResponseData baiduMultipleResponseData = null;
        // true的话 是数据库访问 判断地址是否是准确地址
        if (inner) {
            if (address.contains(ADDRESS_ARRANGEMENT) || address.contains(ADDRESS_FLOOR) || address.contains(ADDRESS_NUMBER) ||
                    address.contains(ADDRESS_BUILDING) || address.contains(ADDRESS_MANSION)) {
                baiduMultipleResponseData = getCoordinateByParameter(address, city);
            }
            // false的话 是外部接口访问不需要判断是否为准确地址
        } else {
            baiduMultipleResponseData = getCoordinateByParameter(address, city);
        }
        // 判断拿到的数据是否为空
        if (baiduMultipleResponseData != null && baiduMultipleResponseData.getTotal() > 0
                && ObjectUtil.isNotNull(baiduMultipleResponseData.getResults().get(0).getLocation())) {
            logger.info("根据地址去查百度经纬度为" + baiduMultipleResponseData.toString());
            locationResultBeans.add(baiduMultipleResponseData.getResults().get(0).getLocation());
            return responseDataService.getBaiduCoordinate(locationResultBeans, address);
        }
        return null;
    }

    /**
     * 根据公司获取坐标
     *
     * @param company 公司
     * @param city    城市
     * @return 结果
     */
    @Override
    public CoordinateResultData getCoordinateByCompany(String company, String city) {
        ResponseDataService responseDataService = new ResponseDataService();
        BaiduMultipleResponseData baiduMultipleResponseData = getCoordinateByParameter(company, city);
        logger.info("根据公司去查百度经纬度为" + baiduMultipleResponseData.toString());
        List<BaiduLocationResultBean> locationResultBeans = new ArrayList<>();
        if (ObjectUtil.isNotNull(baiduMultipleResponseData) && baiduMultipleResponseData.getTotal() > 0
                && ObjectUtil.isNotNull(baiduMultipleResponseData.getResults().get(0).getLocation())) {
            locationResultBeans.add(baiduMultipleResponseData.getResults().get(0).getLocation());
            return responseDataService.getBaiduCoordinate(locationResultBeans, company);
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
    public BaiduMultipleResponseData getCoordinateByParameter(String parameter, String city) {
        String param = "?query=" + parameter + "&region=" + city + "&page_size=" + PAGE_SIZE + "&page_num=" + PAGE +
                " &city_limit=" + true + "&output=" + "json" + "&ak=" + AK;
        return RestTemplateUtil.restTemplate().getForObject(URL + param, BaiduMultipleResponseData.class);
    }
}


