package com.mindata.ecserver.global.geo;

import com.mindata.ecserver.global.http.response.BaiduMutilResponseData;
import com.mindata.ecserver.global.http.response.BaiduResponseData;
import com.mindata.ecserver.global.http.response.GaodeResponseData;
import com.mindata.ecserver.main.manager.CompanyCoordinateManager;
import com.mindata.ecserver.main.model.secondary.CompanyCoordinateEntity;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static com.mindata.ecserver.global.Constant.*;

/**
 * @author hanliqiang wrote on 2017/11/27
 */
@Component
public class GeoCoordinateService {
    @Resource
    private List<GeoCoordinate> geoCoordinates;
    @Resource
    private CompanyCoordinateManager coordinateManager;

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
        GaodeResponseData gaodeAddressResult;
        GaodeResponseData gaodeCompanyResult;
        BaiduResponseData baiduResult;
        BaiduMutilResponseData baiduMutilData;
        if (address.contains(ADDRESS_ARRANGEMENT) || address.contains(ADDRESS_NUMBER) ||
                address.contains(ADDRESS_FLOOR) || address.contains(ADDRESS_MANSION)) {
            baiduResult = (BaiduResponseData) geoCoordinates.get(0).getCoordinateByAddress(address);
            if (baiduResult.getResult() != null) {
                return coordinateManager.getSingleBaiduCoordinate(baiduResult, address);
            }
            gaodeAddressResult = (GaodeResponseData) geoCoordinates.get(1).getCoordinateByAddress(address);
            if (gaodeAddressResult.getGeocodes() != null) {
                return coordinateManager.getSingleGaodeCoordinate(gaodeAddressResult, address);
            }
        }
        baiduMutilData = (BaiduMutilResponseData) geoCoordinates.get(0).getCoordinateByCompanyName(companyName, city);
        if (baiduMutilData.getResults() != null && baiduMutilData.getResults().size() > 0) {
            return coordinateManager.getMutilBaiduCoordinate(baiduMutilData, companyName);
        }
        gaodeCompanyResult = (GaodeResponseData) geoCoordinates.get(1).getCoordinateByAddress(companyName);
        return coordinateManager.getMutilGaodeCoordinate(gaodeCompanyResult, companyName);
    }
}
