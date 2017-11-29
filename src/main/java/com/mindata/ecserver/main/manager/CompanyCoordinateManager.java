package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.global.geo.retrofit.model.BaseResult;
import com.mindata.ecserver.global.geo.retrofit.model.response.CoordinateResult;
import com.mindata.ecserver.main.model.es.EsCompanyCoordinate;
import com.mindata.ecserver.main.model.primary.CompanyCoordinateEntity;
import com.mindata.ecserver.main.repository.primary.CompanyCoordinateRepository;
import com.mindata.ecserver.main.service.EsCompanyCoordinateService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.mindata.ecserver.global.Constant.*;

/**
 * @author hanliqiang wrote on 2017/11/24
 */
@Service
public class CompanyCoordinateManager {
    @Resource
    private CompanyCoordinateRepository coordinateRepository;
    @Resource
    private EsCompanyCoordinateManager esCompanyCoordinateManager;

    public void saveCoordinate(BaseResult baseResult, Long contactId) {
        CoordinateResult coordinateResult = (CoordinateResult) baseResult;
        List<CompanyCoordinateEntity> companyCoordinateEntities = coordinateRepository.findByContactId(contactId);
        if (companyCoordinateEntities.size() > 0) {
            return;
        }
        CompanyCoordinateEntity companyCoordinateEntity = new CompanyCoordinateEntity();
        companyCoordinateEntity.setStatus(baseResult.getState());
        companyCoordinateEntity.setAccuracy(baseResult.getAccuracy());
        companyCoordinateEntity.setContactId(contactId);
        companyCoordinateEntity.setSource(baseResult.getSource());
        if (baseResult.getSource() == BAIDU_SOURCE && baseResult.getQueryCondition() == QUERY_ADDRESS) {
            companyCoordinateEntity.setBaiduCoordinate(coordinateResult.getResult().getLocation().getLng() + "," + coordinateResult.getResult().getLocation().getLat());
        }
        if (baseResult.getSource() == GAODE_SOURCE && baseResult.getQueryCondition() == QUERY_ADDRESS) {
            companyCoordinateEntity.setGaodeCoordinate(coordinateResult.getGeocodes().get(0).getLocation());
        }
        companyCoordinateEntity.setQueryCondition(baseResult.getQueryCondition());
        companyCoordinateEntity.setQueryConditionValue(baseResult.getQueryConditionValue());
        companyCoordinateEntity.setLevel(baseResult.getLevel());
        CompanyCoordinateEntity coordinateEntity = coordinateRepository.save(companyCoordinateEntity);
        //es插值
        EsCompanyCoordinate coordinate = new EsCompanyCoordinate();
        BeanUtils.copyProperties(companyCoordinateEntity, coordinate);
       if (coordinate.getSource() == BAIDU_SOURCE && coordinate.getQueryCondition() == QUERY_ADDRESS) {
           coordinate.setBaiduCoordinate(coordinateResult.getResult().getLocation().getLat() + "," + coordinateResult.getResult().getLocation().getLng());
           esCompanyCoordinateManager.index(coordinate);
        }
        if (coordinateEntity.getSource() == GAODE_SOURCE && coordinateEntity.getQueryCondition() == QUERY_ADDRESS) {
            String[] coordinateArr = companyCoordinateEntity.getGaodeCoordinate().split(",");
            StringBuffer stringBuffer = new StringBuffer();
            coordinate.setGaodeCoordinate(stringBuffer.append(coordinateArr[1]+",").append(coordinateArr[0]).toString());
            esCompanyCoordinateManager.index(coordinate);
        }
        //查询条件如果是公司的话可能会有多条 百度
        if (baseResult.getQueryCondition() == QUERY_COMPANYNAME && baseResult.getSource() == BAIDU_SOURCE && coordinateResult.getResults() != null) {
            for (int i = 0; i < coordinateResult.getResults().size(); i++) {
                companyCoordinateEntity = new CompanyCoordinateEntity();
                companyCoordinateEntity.setStatus(baseResult.getState());
                //准确度
                companyCoordinateEntity.setAccuracy(baseResult.getAccuracy());
                companyCoordinateEntity.setContactId(contactId);
                companyCoordinateEntity.setSource(baseResult.getSource());
                companyCoordinateEntity.setQueryCondition(baseResult.getQueryCondition());
                companyCoordinateEntity.setQueryConditionValue(baseResult.getQueryConditionValue());
                companyCoordinateEntity.setLevel(baseResult.getLevel());
                companyCoordinateEntity.setBaiduCoordinate(coordinateResult.getResults().get(i).getLocation().getLng() + "," + coordinateResult.getResults().get(i).getLocation().getLat());
                coordinateRepository.save(companyCoordinateEntity);
                //es插值
                coordinate.setBaiduCoordinate(coordinateResult.getResults().get(i).getLocation().getLat() + "," + coordinateResult.getResults().get(i).getLocation().getLng());
                esCompanyCoordinateManager.index(coordinate);
            }
        }
        // 查询条件如果是公司的话可能会有多条  高德
        if (baseResult.getQueryCondition() == QUERY_COMPANYNAME && baseResult.getSource() == GAODE_SOURCE && coordinateResult.getGeocodes() != null) {
            for (int i = 0; i < coordinateResult.getGeocodes().size(); i++) {
                companyCoordinateEntity = new CompanyCoordinateEntity();
                companyCoordinateEntity.setStatus(baseResult.getState());
                //准确度
                companyCoordinateEntity.setAccuracy(baseResult.getAccuracy());
                companyCoordinateEntity.setContactId(contactId);
                companyCoordinateEntity.setSource(baseResult.getSource());
                companyCoordinateEntity.setQueryCondition(baseResult.getQueryCondition());
                companyCoordinateEntity.setQueryConditionValue(baseResult.getQueryConditionValue());
                companyCoordinateEntity.setLevel(baseResult.getLevel());
                companyCoordinateEntity.setGaodeCoordinate(coordinateResult.getGeocodes().get(i).getLocation());
                coordinateRepository.save(companyCoordinateEntity);
                ////////////////////
                String[] coordinateArr = companyCoordinateEntity.getGaodeCoordinate().split(",");
                StringBuffer stringBuffer = new StringBuffer();
                coordinate.setGaodeCoordinate(stringBuffer.append(coordinateArr[1]+",").append(coordinateArr[0]).toString());
                esCompanyCoordinateManager.index(coordinate);
            }
        }

    }
}
