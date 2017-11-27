package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.global.geo.retrofit.model.response.CoordinateResult;
import com.mindata.ecserver.global.geo.retrofit.model.response.CoordinateResultBean;
import com.mindata.ecserver.main.model.primary.CompanyCoordinate;
import com.mindata.ecserver.main.repository.primary.CompanyCoordinateRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.mindata.ecserver.global.Constant.*;

/**
 * @author hanliqiang wrote on 2017/11/24
 */
@Service
public class CompanyCoordinateManager {
    @Resource
    private CompanyCoordinateRepository coordinateRepository;



    public void saveCoordinate(CoordinateResult coordinateResult,Long contactId,String queryConditionValue) {
        CoordinateResultBean coordinateResultBean = coordinateResult.getResult();
        CompanyCoordinate companyCoordinate = new CompanyCoordinate();
        companyCoordinate.setStatus(0);
        //准确度
        companyCoordinate.setAccuracy(0);
        companyCoordinate.setContactId(contactId);
        companyCoordinate.setBaiduCoordinate(coordinateResultBean.getLocation().getLng()+","+coordinateResultBean.getLocation().getLat());
        companyCoordinate.setQueryCondition(1);
        companyCoordinate.setQueryConditionValue(queryConditionValue);
        companyCoordinate.setLevel(coordinateResultBean.getLevel());
        coordinateRepository.save(companyCoordinate);
    }
}
