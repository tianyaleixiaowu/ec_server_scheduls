package com.mindata.ecserver.main.service;

import com.mindata.ecserver.global.geo.GeoCoordinateService;
import com.mindata.ecserver.main.manager.CompanyCoordinateManager;
import com.mindata.ecserver.main.manager.ContactManager;
import com.mindata.ecserver.main.manager.EcCodeAreaManager;
import com.mindata.ecserver.main.model.primary.EcContactEntity;
import com.mindata.ecserver.main.model.secondary.CompanyCoordinateEntity;
import com.xiaoleilu.hutool.date.DateUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;


/**
 * @author hanliqiang wrote on 2017/11/24
 */
@Service
public class CompanyCoordinateService {
    private static final int PAGE_SIZE = 500;
    @Resource
    private GeoCoordinateService geoCoordinateService;
    @Resource
    private ContactManager contactManager;
    @Resource
    private CompanyCoordinateManager coordinateManager;
    @Resource
    private EcCodeAreaManager ecCodeAreaManager;

    /**
     * 新增所有的
     *
     * @throws IOException
     */
//    @PostConstruct
    public void saveCompanyCoordinate() throws IOException {
        Pageable pageable = new PageRequest(0, 1, Sort.Direction.ASC, "id");
        Page<EcContactEntity> page = contactManager.findByState(pageable);
        for (int i = 0; i < page.getTotalElements() / PAGE_SIZE + 1; i++) {
            pageable = new PageRequest(i, PAGE_SIZE, Sort.Direction.ASC, "id");
            Page<EcContactEntity> entities = contactManager.findByState(pageable);
            for (EcContactEntity ecContactEntity : entities.getContent()) {
                String city = ecCodeAreaManager.findNmaeById(ecContactEntity.getCity(), ecContactEntity.getProvince());
                List<CompanyCoordinateEntity> coordinateEntities = geoCoordinateService.getLocation(ecContactEntity.getAddress(), ecContactEntity.getCompany(), city);
                coordinateManager.saveCoordinate(coordinateEntities, ecContactEntity.getId());
            }
        }
    }

    /**
     * 修补id范围内的数据
     *
     * @param beginId 开始id
     * @param endId   结束id
     * @throws IOException
     */
    public void partInsertIdBetween(Long beginId, Long endId) throws IOException {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(0, 1, sort);
        Page<EcContactEntity> page = contactManager.findByIdBetween(beginId, endId, pageable);
        //没有新数据
        if (page.getTotalElements() == 0) {
            return;
        }
        for (int i = 0; i < page.getTotalElements() / PAGE_SIZE + 1; i++) {
            pageable = new PageRequest(i, PAGE_SIZE, sort);
            List<EcContactEntity> contactEntities = contactManager.findByIdBetween(beginId, endId, pageable).getContent();
            for (EcContactEntity ecContactEntity : contactEntities) {
                String city = ecCodeAreaManager.findNmaeById(ecContactEntity.getCity(), ecContactEntity.getProvince());
                List<CompanyCoordinateEntity> coordinateEntities = geoCoordinateService.getLocation(ecContactEntity.getAddress(), ecContactEntity.getCompany(), city);
                coordinateManager.saveCoordinate(coordinateEntities, ecContactEntity.getId());
            }
        }
    }

    /**
     * 修补一段时间内的数据
     *
     * @param begin
     * @param end
     * @throws IOException
     */
    public void partInsertDateBetween(String begin, String end) throws IOException {
        Date beginTime = DateUtil.beginOfDay(DateUtil.parseDate(begin));
        Date endTime = DateUtil.endOfDay(DateUtil.parseDate(end));
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(0, 1, sort);
        Page<EcContactEntity> page = contactManager.findByDateBetween(beginTime, endTime, pageable);
        if (page.getTotalElements() == 0) {
            return;
        }
        for (int i = 0; i < page.getTotalElements() / PAGE_SIZE + 1; i++) {
            pageable = new PageRequest(i, PAGE_SIZE, sort);
            List<EcContactEntity> contactEntities = contactManager.findByDateBetween(beginTime, endTime, pageable).getContent();
            for (EcContactEntity ecContactEntity : contactEntities) {
                String city = ecCodeAreaManager.findNmaeById(ecContactEntity.getCity(), ecContactEntity.getProvince());
                List<CompanyCoordinateEntity> coordinateEntities = geoCoordinateService.getLocation(ecContactEntity.getAddress(), ecContactEntity.getCompany(), city);
                coordinateManager.saveCoordinate(coordinateEntities, ecContactEntity.getId());
            }
        }
    }

    /**
     * 对外提供获取经纬度
     *
     * @param address
     * @param companyName
     * @param city
     * @return
     * @throws IOException
     */
    public List<Map<String, String>> findCoordinate(String address, String companyName, String city) throws IOException {
        List<Map<String, String>> mapList = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        List<CompanyCoordinateEntity> coordinateEntities = geoCoordinateService.getLocation(address, companyName, city);
        for (CompanyCoordinateEntity companyCoordinateEntity : coordinateEntities) {
            map.put("coordinate", companyCoordinateEntity.getBaiduCoordinate());
            mapList.add(map);
        }
        return mapList;
    }

    /**
     * 定时修改不太靠谱或者没有坐标的数据
     *
     * @throws IOException
     */
    public void timingUpdateCoordinate() throws IOException {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(0, 1, sort);
        Page<CompanyCoordinateEntity> page = coordinateManager.findByStatusOrAccuracy(pageable);
        if (page.getTotalElements() == 0) {
            return;
        }
        for (int i = 0; i < page.getTotalElements() / PAGE_SIZE + 1; i++) {
            pageable = new PageRequest(i, PAGE_SIZE, sort);
            List<CompanyCoordinateEntity> coordinateEntities = coordinateManager.findByStatusOrAccuracy(pageable).getContent();
            for (CompanyCoordinateEntity companyCoordinateEntity : coordinateEntities) {
                EcContactEntity ecContactEntity = contactManager.findOne(companyCoordinateEntity.getContactId());
                String city = ecCodeAreaManager.findNmaeById(ecContactEntity.getCity(), ecContactEntity.getProvince());
                List<CompanyCoordinateEntity> companyCoordinateEntities = geoCoordinateService.getLocation(ecContactEntity.getAddress(), ecContactEntity.getCompany(), city);
                coordinateManager.saveCoordinate(companyCoordinateEntities, ecContactEntity.getId());
            }
        }
    }

}
