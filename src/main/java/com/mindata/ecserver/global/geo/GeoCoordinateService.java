package com.mindata.ecserver.global.geo;

import com.mindata.ecserver.global.http.response.BaiduConvertResponseData;
import com.mindata.ecserver.global.http.response.BaiduMutilResponseData;
import com.mindata.ecserver.global.http.response.BaiduResponseData;
import com.mindata.ecserver.global.http.response.GaodeResponseData;
import com.mindata.ecserver.main.model.secondary.CompanyCoordinateEntity;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static com.mindata.ecserver.global.Constant.*;

/**
 * @author hanliqiang wrote on 2017/11/27
 */
@Component
public class GeoCoordinateService {
    @Resource
    private ConvertBaiduCoordinate convertBaiduCoordinate;
    @Resource
    private List<GeoCoordinate> geoCoordinates;

    /**
     * 获取高德、百度返回的数据
     *
     * @param address     地址
     * @param companyName 公司名字
     * @param city        城市
     * @return 结果
     * @throws IOException 异常
     */
    public List<CompanyCoordinateEntity> getLocation(String address, String companyName, String city) throws IOException, NoSuchAlgorithmException {
        GaodeResponseData gaodeResult;
        List<CompanyCoordinateEntity> coordinateEntities = new ArrayList<>();
        CompanyCoordinateEntity coordinateEntity = new CompanyCoordinateEntity();
        if (address.contains(ADDRESS_ARRANGEMENT) || address.contains(ADDRESS_NUMBER) || address.contains(ADDRESS_FLOOR) || address.contains(ADDRESS_MANSION)) {
            BaiduResponseData baiduResult = (BaiduResponseData) geoCoordinates.get(0).getCoordinateByAddress(address);
            if (baiduResult.getResult() != null) {
                coordinateEntity.setStatus(NORMAL_ADDRESS);
                coordinateEntity.setQueryCondition(QUERY_ADDRESS);
                coordinateEntity.setQueryConditionValue(address);
                coordinateEntity.setSource(BAIDU_SOURCE);
                coordinateEntity.setAccuracy(CONFIRM_ACCURAY);
                coordinateEntity.setLevel(baiduResult.getResult().getLevel());
                coordinateEntity.setBaiduCoordinate(baiduResult.getResult().getLocation().getLng() + "," + baiduResult.getResult().getLocation().getLat());
                coordinateEntities.add(coordinateEntity);
                return coordinateEntities;
            }
            gaodeResult = (GaodeResponseData) geoCoordinates.get(1).getCoordinateByAddress(address);
            if (gaodeResult.getGeocodes() != null) {
                coordinateEntity.setStatus(NORMAL_ADDRESS);
                coordinateEntity.setQueryCondition(QUERY_ADDRESS);
                coordinateEntity.setQueryConditionValue(address);
                coordinateEntity.setSource(GAODE_SOURCE);
                coordinateEntity.setAccuracy(CONFIRM_ACCURAY);
                coordinateEntity.setLevel(gaodeResult.getGeocodes().get(0).getLevel());
                coordinateEntity.setGaodeCoordinate(gaodeResult.getGeocodes().get(0).getLocation());
                //将高德坐标转换为百度坐标
                BaiduConvertResponseData convertResponseData = convertBaiduCoordinate.convertBaiduCoordinate(coordinateEntity.getGaodeCoordinate());
                coordinateEntity.setBaiduCoordinate(convertResponseData.getResult().get(0).getX() + "," + convertResponseData.getResult().get(0).getY());
                coordinateEntities.add(coordinateEntity);
                return coordinateEntities;
            }
        }
        //根据公司名称去百度查询
        BaiduMutilResponseData baiduMutilData = (BaiduMutilResponseData) geoCoordinates.get(0).getCoordinateByCompanyName(companyName, city);
        if (baiduMutilData.getResults() != null && baiduMutilData.getResults().size() > 0) {
            for (int i = 0; i < baiduMutilData.getResults().size(); i++) {
                coordinateEntity = new CompanyCoordinateEntity();
                //如果多条会有多个地址
                if (baiduMutilData.getResults().size() > 1) {
                    coordinateEntity.setStatus(MORE_ADDRESS);
                    coordinateEntity.setAccuracy(MAYBE_ACCURAY);
                    //反之单个 则正常可以确认
                } else {
                    coordinateEntity.setStatus(NORMAL_ADDRESS);
                    coordinateEntity.setAccuracy(CONFIRM_ACCURAY);
                }
                coordinateEntity.setQueryCondition(QUERY_COMPANYNAME);
                coordinateEntity.setQueryConditionValue(companyName);
                coordinateEntity.setSource(BAIDU_SOURCE);
                coordinateEntity.setBaiduCoordinate(baiduMutilData.getResults().get(i).getLocation().getLng() + "," + baiduMutilData.getResults().get(i).getLocation().getLat());
                coordinateEntity.setLevel(baiduMutilData.getResults().get(i).getTag());
                coordinateEntities.add(coordinateEntity);
            }
            return coordinateEntities;
        }
        gaodeResult = (GaodeResponseData) geoCoordinates.get(1).getCoordinateByAddress(companyName);
        if (gaodeResult.getGeocodes() != null && gaodeResult.getGeocodes().size() > 0) {
            for (int i = 0; i < gaodeResult.getGeocodes().size(); i++) {
                coordinateEntity = new CompanyCoordinateEntity();
                if (gaodeResult.getGeocodes().size() > 1) {
                    coordinateEntity.setStatus(MORE_ADDRESS);
                    coordinateEntity.setAccuracy(MAYBE_ACCURAY);
                } else {
                    coordinateEntity.setStatus(NORMAL_ADDRESS);
                    coordinateEntity.setAccuracy(CONFIRM_ACCURAY);
                }
                coordinateEntity.setQueryCondition(QUERY_COMPANYNAME);
                coordinateEntity.setQueryConditionValue(companyName);
                coordinateEntity.setSource(GAODE_SOURCE);
                coordinateEntity.setGaodeCoordinate(gaodeResult.getGeocodes().get(0).getLocation());
                coordinateEntity.setLevel(gaodeResult.getGeocodes().get(i).getLevel());
                //将高德坐标转换为百度坐标
                BaiduConvertResponseData convertResponseData = convertBaiduCoordinate.convertBaiduCoordinate(coordinateEntity.getGaodeCoordinate());
                coordinateEntity.setBaiduCoordinate(convertResponseData.getResult().get(0).getX() + "," + convertResponseData.getResult().get(0).getY());
                coordinateEntities.add(coordinateEntity);
            }
            return coordinateEntities;
        }
        coordinateEntity.setQueryCondition(QUERY_COMPANYNAME);
        coordinateEntity.setQueryConditionValue(companyName);
        coordinateEntity.setSource(GAODE_SOURCE);
        coordinateEntity.setStatus(NONE_ADDRESS);
        coordinateEntity.setAccuracy(NORELIABLE_ACCURAY);
        coordinateEntity.setLevel(null);
        coordinateEntities.add(coordinateEntity);
        return coordinateEntities;
    }
}
