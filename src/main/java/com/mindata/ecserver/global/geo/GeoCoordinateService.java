package com.mindata.ecserver.global.geo;

import com.mindata.ecserver.global.geo.retrofit.model.BaseResult;
import com.mindata.ecserver.global.geo.retrofit.model.response.CoordinateResult;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

import static com.mindata.ecserver.global.Constant.*;

/**
 * @author hanliqiang wrote on 2017/11/27
 */
@Component
public class GeoCoordinateService {
    @Resource
    List<GeoCoordinate> geoCoordinates;

    public BaseResult getLocation(String address, String companyName, String city) throws IOException {
        CoordinateResult baiduResult;
        CoordinateResult gaodeResult;
        if (address.contains(ADDRESS_ARRANGEMENT) || address.contains(ADDRESS_NUMBER) || address.contains(ADDRESS_FLOOR) || address.contains(ADDRESS_MANSION)) {
            baiduResult = (CoordinateResult) geoCoordinates.get(0).getCoordinateByAddress(address);
            if (baiduResult.getResult() != null) {
                baiduResult.setState(NORMAL_ADDRESS);
                baiduResult.setQueryCondition(QUERY_ADDRESS);
                baiduResult.setQueryConditionValue(address);
                baiduResult.setSource(BAIDU_SOURCE);
                baiduResult.setAccuracy(CONFIRM_ACCURAY);
                baiduResult.setLevel(baiduResult.getResult().getLevel());
                baiduResult.setCoordinate(baiduResult.getResult().getLocation().getLng() + "," + baiduResult.getResult().getLocation().getLat());
                return baiduResult;
            }
            gaodeResult = (CoordinateResult) geoCoordinates.get(1).getCoordinateByAddress(address);
            if (gaodeResult.getGeocodes() != null) {
                gaodeResult.setState(NORMAL_ADDRESS);
                gaodeResult.setQueryCondition(QUERY_ADDRESS);
                gaodeResult.setQueryConditionValue(address);
                //2 高德
                gaodeResult.setSource(GAODE_SOURCE);
                gaodeResult.setAccuracy(CONFIRM_ACCURAY);
                gaodeResult.setLevel(gaodeResult.getGeocodes().get(0).getLevel());
                gaodeResult.setCoordinate(gaodeResult.getGeocodes().get(0).getLocation());
                return gaodeResult;
            }
        }
        //根据公司名称去百度查询
        baiduResult = (CoordinateResult) geoCoordinates.get(0).getCoordinateByCompanyName(companyName, city);
        if (baiduResult.getResults() != null && baiduResult.getResults().size() > 0) {
            if (baiduResult.getResults().size() > 1) {
                baiduResult.setState(MORE_ADDRESS);
                baiduResult.setAccuracy(MAYBE_ACCURAY);
            }
            baiduResult.setState(NORMAL_ADDRESS);
            //2公司名字
            baiduResult.setQueryCondition(QUERY_COMPANYNAME);
            baiduResult.setQueryConditionValue(companyName);
            //1 百度
            baiduResult.setSource(BAIDU_SOURCE);
            baiduResult.setAccuracy(CONFIRM_ACCURAY);
            return baiduResult;
        }
        gaodeResult = (CoordinateResult) geoCoordinates.get(1).getCoordinateByAddress(companyName);
        if (gaodeResult.getGeocodes() != null && gaodeResult.getGeocodes().size() > 0) {
            if (gaodeResult.getGeocodes().size() > 1) {
                gaodeResult.setState(MORE_ADDRESS);
                //如果是多条数据 1 可能对
                gaodeResult.setAccuracy(MAYBE_ACCURAY);
            }
            gaodeResult.setState(NORMAL_ADDRESS);
            gaodeResult.setQueryCondition(QUERY_COMPANYNAME);
            gaodeResult.setQueryConditionValue(companyName);
            gaodeResult.setSource(GAODE_SOURCE);
            //如果只是一条数据 确认
            gaodeResult.setAccuracy(CONFIRM_ACCURAY);
            gaodeResult.setLevel(gaodeResult.getGeocodes().get(0).getLevel());
            return gaodeResult;
        }
        gaodeResult.setQueryCondition(QUERY_COMPANYNAME);
        gaodeResult.setQueryConditionValue(companyName);
        gaodeResult.setSource(GAODE_SOURCE);
        //没有获取到数据 则是无地址2
        gaodeResult.setState(NONE_ADDRESS);
        gaodeResult.setAccuracy(NORELIABLE_ACCURAY);
        gaodeResult.setLevel(null);
        return gaodeResult;
    }
}
