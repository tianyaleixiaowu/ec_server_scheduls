package com.mindata.ecserver.main.service;

import com.mindata.ecserver.global.geo.GeoBaiduCoordinate;
import com.mindata.ecserver.global.geo.retrofit.model.response.CoordinateResult;
import com.mindata.ecserver.main.manager.CompanyCoordinateManager;
import com.mindata.ecserver.main.manager.ContactManager;
import com.mindata.ecserver.main.model.primary.EcContactEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;


/**
 * @author hanliqiang wrote on 2017/11/24
 */
@Service
public class CompanyCoordinateService {
    @Resource
    private GeoBaiduCoordinate geoBaiduCoordinate;
    @Resource
    private ContactManager contactManager;
    @Resource
    private CompanyCoordinateManager coordinateManager;

    public void saveCompanyCoordinate() throws IOException {
        Pageable pageable = new PageRequest(0, 500, Sort.Direction.ASC, "id");
        Page<EcContactEntity> ecContactEntities = contactManager.findByState(pageable);
        for (int i = 0; i < ecContactEntities.getTotalPages(); i++) {
            Page<EcContactEntity> entities = contactManager.findByState(pageable);
            for(EcContactEntity ecContactEntity : entities.getContent()){
                CoordinateResult coordinateResult = geoBaiduCoordinate.getCoordinateByAddress(ecContactEntity.getAddress());
                coordinateManager.saveCoordinate(coordinateResult,ecContactEntity.getId(),ecContactEntity.getAddress());
            }
        }
    }




}
