package com.mindata.ecserver.main.service;

import com.mindata.ecserver.global.bean.ResultGenerator;
import com.mindata.ecserver.global.coordinate.service.CoordinateService;
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

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author hanliqiang wrote on 2017/11/24
 */
@Service
public class CompanyCoordinateService {
    private static final int PAGE_SIZE = 500;
    @Resource
    private ContactManager contactManager;
    @Resource
    private CompanyCoordinateManager coordinateManager;
    @Resource
    private EsCompanyCoordinateManager esCompanyCoordinateManager;
    @Resource
    private CoordinateService coordinateService;

    /**
     * 补充所有的公司经纬度信息
     *
     * @throws IOException 异常
     */
    public void completeAllCompanyCoordinate(Boolean force) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        Pageable pageable = new PageRequest(0, 1, Sort.Direction.ASC, "id");
        Page<EcContactEntity> page = contactManager.findAll(pageable);
        for (int i = 0; i < page.getTotalElements() / PAGE_SIZE + 1; i++) {
            pageable = new PageRequest(i, PAGE_SIZE, Sort.Direction.ASC, "id");
            Page<EcContactEntity> entities = contactManager.findAll(pageable);
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
    public void partInsertIdBetween(Long beginId, Long endId, Boolean force) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        if (endId == null) {
            endId = contactManager.findLastOne().getId();
        }
        if (beginId == null) {
            beginId = contactManager.findFirstOne().getId();
        }
        Pageable pageable = new PageRequest(0, 1, Sort.Direction.ASC, "id");
        Page<EcContactEntity> page = contactManager.findByIdBetween(beginId, endId, pageable);
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
    public void partInsertDateBetween(String begin, String end, Boolean force) throws  IllegalAccessException, InstantiationException, ClassNotFoundException {
        Date beginTime = DateUtil.beginOfDay(DateUtil.parseDate(begin));
        Date endTime = DateUtil.endOfDay(DateUtil.parseDate(end));
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(0, 1, sort);
        Page<EcContactEntity> page = contactManager.findByDateBetween(beginTime, endTime, pageable);
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
    public void timingUpdateCoordinate() throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(0, 1, sort);
        Page<CompanyCoordinateEntity> page = coordinateManager.findByStatusOrAccuracy(pageable);
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
     * 公司获取经纬度
     */
    public Object getOutLocationByCompany(String company, String city) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        if (StrUtil.isEmpty(company) || StrUtil.isEmpty(city)) {
            return ResultGenerator.genFailResult("城市和公司不能为空");
        }
        String coordinate = coordinateService.getOutLocationByCompany(company, city);
        if (StrUtil.isNotEmpty(coordinate)) {
            return ResultGenerator.genSuccessResult(coordinate);
        }
        return ResultGenerator.genFailResult("没有查到该公司的经纬度");
    }
    /**
     * 地址获取经纬度
     */
    public Object getOutLocationByAddress(String address, String city) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        if (StrUtil.isEmpty(address) || StrUtil.isEmpty(city)) {
            return ResultGenerator.genFailResult("城市和地址不能为空");
        }
        String coordinate = coordinateService.getOutLocationByAddress(address, city);
        if (StrUtil.isNotEmpty(coordinate)) {
            return ResultGenerator.genSuccessResult(coordinate);
        }
        return ResultGenerator.genFailResult("没有查到该地址的经纬度");
    }
}
