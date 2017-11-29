package com.mindata.ecserver.main.service;

import com.mindata.ecserver.global.geo.GeoCoordinateService;
import com.mindata.ecserver.global.geo.retrofit.model.BaseResult;
import com.mindata.ecserver.global.util.CommonUtil;
import com.mindata.ecserver.main.manager.CompanyCoordinateManager;
import com.mindata.ecserver.main.manager.ContactManager;
import com.mindata.ecserver.main.manager.EcCodeAreaManager;
import com.mindata.ecserver.main.model.primary.EcContactEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Objects;


/**
 * @author hanliqiang wrote on 2017/11/24
 */
@Service
public class CompanyCoordinateService {
    @Resource
    private GeoCoordinateService geoCoordinateService;
    @Resource
    private ContactManager contactManager;
    @Resource
    private CompanyCoordinateManager coordinateManager;
    @Resource
    private EcCodeAreaManager ecCodeAreaManager;

    public void saveCompanyCoordinate() throws IOException {
        Pageable pageable = new PageRequest(0, 500, Sort.Direction.ASC, "id");
        Page<EcContactEntity> ecContactEntities = contactManager.findByState(pageable);
        for (int i = 0; i < ecContactEntities.getTotalPages(); i++) {
            pageable = new PageRequest(i, 500, Sort.Direction.ASC, "id");
            Page<EcContactEntity> entities = contactManager.findByState(pageable);
            for (EcContactEntity ecContactEntity : entities.getContent()) {
                String city;
                if (!Objects.equals(ecContactEntity.getCity(), "0")) {
                    String number = CommonUtil.getEncrypt(ecContactEntity.getCity());
                    city = ecCodeAreaManager.findById(number);
                } else {
                    //获取到省或者直辖市
                    city = ecCodeAreaManager.findById(ecContactEntity.getProvince());
                }
                BaseResult baseResult = geoCoordinateService.getLocation(ecContactEntity.getAddress(), ecContactEntity.getCompany(), city);
                coordinateManager.saveCoordinate(baseResult, ecContactEntity.getId());
            }
        }
    }


}
