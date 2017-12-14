package com.mindata.ecserver.global.geo;

import com.mindata.ecserver.global.geo.service.IGeoCoordinateService;
import com.mindata.ecserver.global.http.response.base.CoordinateResultData;
import com.xiaoleilu.hutool.util.CollectionUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hanliqiang wrote on 2017/11/27
 */
@Service
public class GeoCoordinateService {
    private static final int PAGE_SIZE = 20;
    private static final int PAGE = 0;
    @Resource
    private List<IGeoCoordinateService> geoCoordinates;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 获取高德、百度返回的数据。该方法用于给Contact表的线索信息获取经纬度
     *
     * @param address
     *         地址
     * @param companyName
     *         公司名字
     * @param city
     *         城市
     * @return 结果
     * @throws IOException
     *         异常
     */
    @SuppressWarnings({"AlibabaUndefineMagicConstant", "unchecked"})
    public List<CoordinateResultData> getLocation(String address, String companyName, String city) throws IOException {
        //如果是用Address查询，只返回一条数据，判断地址里是否包含 层、号、幢、楼、厦 如果包含认为是准确地址
        if (StrUtil.isNotEmpty(address) || address.contains("层") || address.contains("楼") || address.contains("号") ||
                address.contains("幢") || address.contains("厦")) {
            logger.info("开始拿Address字段进行查询，Address为" + address + "，城市为" + city);
            for (IGeoCoordinateService geoCoordinateService : geoCoordinates) {
                List<CoordinateResultData> coordinateResultData = geoCoordinateService.getCoordinateByParameter
                        (address, city, PAGE_SIZE, PAGE);
                if (CollectionUtil.isNotEmpty(coordinateResultData)) {
                    logger.info("根据Address字段查询到的结果是" + coordinateResultData);
                    return coordinateResultData;
                }
            }
        }
        logger.info("开始拿公司名和城市字段进行查询，companyName为" + companyName + "，城市为" + city);
        for (IGeoCoordinateService geoCoordinateService : geoCoordinates) {
            List<CoordinateResultData> coordinateResultData = geoCoordinateService.getCoordinateByParameter
                    (companyName, city, PAGE_SIZE, PAGE);
            if (CollectionUtil.isNotEmpty(coordinateResultData)) {
                logger.info("根据公司名和城市字段查询到的结果是" + coordinateResultData);
                return coordinateResultData;
            }
        }
        logger.info("什么也没找到");
        return null;
    }

    /**
     * 根据公司名称或者地址获取经纬度
     *
     * @param parameter
     *         参数
     * @param city
     *         城市
     * @return 结果
     * @throws IOException
     *         异常
     */
    public List<String> getOutLocationByParameter(String parameter, String city) throws IOException {
        List<CoordinateResultData> coordinateResultData = getLocation(parameter, parameter, city);
        if (coordinateResultData == null) {
            return null;
        }
        return coordinateResultData.stream().map(CoordinateResultData::getBaiduCoordinate).collect(Collectors.toList());
    }

}
