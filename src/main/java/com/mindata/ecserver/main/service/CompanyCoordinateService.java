package com.mindata.ecserver.main.service;

import com.mindata.ecserver.global.geo.GeoCoordinateService;
import com.mindata.ecserver.main.manager.CompanyCoordinateManager;
import com.mindata.ecserver.main.manager.ContactManager;
import com.mindata.ecserver.main.manager.EsCompanyCoordinateManager;
import com.mindata.ecserver.main.model.primary.EcContactEntity;
import com.mindata.ecserver.main.model.secondary.CompanyCoordinateEntity;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private EsCompanyCoordinateManager esCompanyCoordinateManager;

    /**
     * 新增所有的
     *
     * @throws IOException 异常
     */
    public void saveCompanyCoordinate(Boolean force) throws IOException {
        Pageable pageable = new PageRequest(0, 1, Sort.Direction.ASC, "id");
        Page<EcContactEntity> page = contactManager.findByState(pageable);
        for (int i = 0; i < page.getTotalElements() / PAGE_SIZE + 1; i++) {
            pageable = new PageRequest(i, PAGE_SIZE, Sort.Direction.ASC, "id");
            Page<EcContactEntity> entities = contactManager.findByState(pageable);
            //保存数据库
            List<CompanyCoordinateEntity> coordinateEntities = coordinateManager.saveByContacts(entities.getContent(), force);
            //保存es
            esCompanyCoordinateManager.bulkIndexCompany(coordinateEntities, force);
        }
    }

    /**
     * 修补id范围内的数据
     *
     * @param beginId 开始id
     * @param endId   结束id
     * @throws IOException 异常
     */
    @Transactional(rollbackFor = Exception.class)
    public void partInsertIdBetween(Long beginId, Long endId, Boolean force) throws IOException {
        Pageable pageable = new PageRequest(0, 1, Sort.Direction.ASC, "id");
        Page<EcContactEntity> page = contactManager.findByIdBetween(beginId, endId, pageable);
        //没有新数据
        if (page.getTotalElements() == 0) {
            return;
        }
        for (int i = 0; i < page.getTotalElements() / PAGE_SIZE + 1; i++) {
            pageable = new PageRequest(i, PAGE_SIZE, Sort.Direction.ASC, "id");
            List<EcContactEntity> contactEntities = contactManager.findByIdBetween(beginId, endId, pageable).getContent();
            List<CompanyCoordinateEntity> coordinateEntities = coordinateManager.saveByContacts(contactEntities, force);
            esCompanyCoordinateManager.bulkIndexCompany(coordinateEntities, force);
        }
    }

    /**
     * 修补一段时间内的数据
     *
     * @param begin 开始时间
     * @param end   结束时间
     * @throws IOException 异常
     */
    public void partInsertDateBetween(String begin, String end, Boolean force) throws IOException {
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
            List<CompanyCoordinateEntity> coordinateEntities = coordinateManager.saveByContacts(contactEntities, force);
            esCompanyCoordinateManager.bulkIndexCompany(coordinateEntities, force);
        }
    }

    /**
     * 定时修改不太靠谱或者没有坐标的数据
     *
     * @throws IOException 异常
     */
    public void timingUpdateCoordinate() throws IOException {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(0, 1, sort);
        Page<CompanyCoordinateEntity> page = coordinateManager.findByStatusOrAccuracy(pageable);
        if (page.getTotalElements() == 0) {
            return;
        }
        List<EcContactEntity> contactEntities = new ArrayList<>();
        for (int i = 0; i < page.getTotalElements() / PAGE_SIZE + 1; i++) {
            pageable = new PageRequest(i, PAGE_SIZE, sort);
            List<CompanyCoordinateEntity> coordinateEntities = coordinateManager.findByStatusOrAccuracy(pageable).getContent();
            for (CompanyCoordinateEntity companyCoordinateEntity : coordinateEntities) {
                EcContactEntity ecContactEntity = contactManager.findOne(companyCoordinateEntity.getContactId());
                contactEntities.add(ecContactEntity);
            }
            List<CompanyCoordinateEntity> entityList = coordinateManager.saveByContacts(contactEntities, null);
            esCompanyCoordinateManager.bulkIndexCompany(entityList, null);
        }
    }


    /**
     * 对外提供获取经纬度
     *
     * @param companyName 公司名称
     * @param city        城市
     * @return 结果
     * @throws IOException 异常
     */
    public Map<String, Object> getOutLocationByCompany(String companyName, String city) throws IOException {
        Map<String, Object> map = new HashMap<>(1);
        if (StrUtil.isEmpty(city)) {
            map.put("message", "城市为空！");
            return map;
        }
        if (StrUtil.isEmpty(companyName)) {
            map.put("message", "公司为空！");
            return map;
        }
        List<String> coordinateEntities = geoCoordinateService.getOutLocationByParamater(companyName, city);
        map.put("coordinate", coordinateEntities);
        return map;
    }

    /**
     * 根据地址查经纬度
     *
     * @param address 地址
     * @return 结果
     * @throws IOException 异常
     */
    public Map<String, Object> getOutLocationByAddress(String address, String city) throws IOException {
        Map<String, Object> map = new HashMap<>(1);
        if (StrUtil.isEmpty(address)) {
            map.put("message", "地址为空！");
            return map;
        }
        if (StrUtil.isEmpty(city)) {
            map.put("message", "城市为空！");
            return map;
        }
        List<String> coordinateEntities = geoCoordinateService.getOutLocationByParamater(address, city);
        if (coordinateEntities.size() > 0) {
            map.put("coordinate", coordinateEntities.get(0));
        } else {
            map.put("coordinate", null);
        }
        return map;
    }
}
