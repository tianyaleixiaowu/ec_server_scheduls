package com.mindata.ecserver.global.coordinate.service;

import com.mindata.ecserver.global.coordinate.CoordinateFactory;
import com.mindata.ecserver.global.coordinate.http.response.base.CoordinateResultData;
import com.xiaoleilu.hutool.util.ObjectUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.mindata.ecserver.global.GeoConstant.OCTOTHORPE;

/**
 * @author hanliqiang wrote on 2017/12/14
 */
@Service
public class CoordinateService {
    @Resource
    private CoordinateFactory coordinateFactory;
    @Resource
    private ResponseDataService responseDataService;

    /**
     * @param address 参数(地址或公司名称)
     * @param city    城市
     * @return 结果
     * @throws IllegalAccessException 异常
     * @throws InstantiationException 异常
     * @throws ClassNotFoundException 异常
     */
    public CoordinateResultData getLocation(String address, String companyName, String city) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        List<ICoordinateService> coordinateServices = coordinateFactory.getInstances();
        // 根据地址去查  如果地址包含# 会报101 AK参数不存在
        if (StrUtil.isNotEmpty(address) && !address.contains(OCTOTHORPE)) {
            for (ICoordinateService coordinateService : coordinateServices) {
                CoordinateResultData resultData = coordinateService.getCoordinateByAddress(address, city, true);
                if (ObjectUtil.isNotNull(resultData)) {
                    return resultData;
                }
            }
        }
        // 根据公司去查
        if (StrUtil.isNotEmpty(companyName)) {
            for (ICoordinateService coordinateService : coordinateServices) {
                CoordinateResultData resultData = coordinateService.getCoordinateByCompany(companyName, city);
                if (ObjectUtil.isNotNull(resultData)) {
                    return resultData;
                }
            }
        }
        return responseDataService.getNoneCoordinate(companyName);
    }

    /**
     * 根据地址获取经纬度
     *
     * @param address 参数(地址)
     * @param city    城市
     * @return 结果
     */
    public String getOutLocationByAddress(String address, String city) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        List<ICoordinateService> coordinateServices = coordinateFactory.getInstances();
        // 根据地址去查
        for (ICoordinateService coordinateService : coordinateServices) {
            CoordinateResultData resultData = coordinateService.getCoordinateByAddress(address, city, false);
            if (ObjectUtil.isNotNull(resultData)) {
                return resultData.getBaiduCoordinate();
            }
        }
        return null;
    }

    /**
     * 根据公司获取经纬度
     *
     * @param company 参数(公司)
     * @param city    城市
     * @return 结果
     */
    public String getOutLocationByCompany(String company, String city) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        List<ICoordinateService> coordinateServices = coordinateFactory.getInstances();
        // 根据公司去查
        for (ICoordinateService coordinateService : coordinateServices) {
            CoordinateResultData resultData = coordinateService.getCoordinateByCompany(company, city);
            if (ObjectUtil.isNotNull(resultData)) {
                return resultData.getBaiduCoordinate();
            }
        }
        return null;
    }
}
