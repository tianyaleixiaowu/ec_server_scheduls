package com.mindata.ecserver.main.service;

import com.mindata.ecserver.async.AsyncTask;
import com.mindata.ecserver.global.bean.ResultGenerator;
import com.mindata.ecserver.global.geo.GeoCoordinateService;
import com.mindata.ecserver.main.manager.CompanyCoordinateManager;
import com.mindata.ecserver.main.manager.ContactManager;
import com.mindata.ecserver.main.manager.EsCompanyCoordinateManager;
import com.mindata.ecserver.main.model.primary.EcContactEntity;
import com.mindata.ecserver.main.model.secondary.PtCompanyCoordinate;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.util.CollectionUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private GeoCoordinateService geoCoordinateService;
    @Resource
    private ContactManager contactManager;
    @Resource
    private CompanyCoordinateManager coordinateManager;
    @Resource
    private EsCompanyCoordinateManager esCompanyCoordinateManager;
    @Resource
    private AsyncTask asyncTask;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 补充所有的公司经纬度信息
     *
     * @throws IOException
     *         异常
     */
    public void completeAllCompanyCoordinate(Boolean force) throws IOException {
        Long beginId = contactManager.findFirstOne().getId();
        Long endId = contactManager.findLastOne().getId();
        partInsertIdBetween(beginId, endId, force);
    }

    /**
     * 修补id范围内的数据
     *
     * @param beginId
     *         开始id
     * @param endId
     *         结束id
     * @throws IOException
     *         异常
     */
    public void partInsertIdBetween(Long beginId, Long endId, Boolean force) throws IOException {
        if (force == null) {
            force = false;
        }
        if (endId == null) {
            endId = contactManager.findLastOne().getId();
        }
        if (beginId == null) {
            beginId = contactManager.findFirstOne().getId();
        }

        Long count = contactManager.countIdBetween(beginId, endId);
        Long size = count / 3000 + 1;

        //一个线程处理3千条
        for (int i = 0; i < size; i++) {
            Long tempBeginId = beginId + 3000 * i;
            Long tempEndId = tempBeginId + 2999;
            if (tempEndId > endId) {
                tempEndId = endId;
            }
            Long finalTempEndId = tempEndId;
            Boolean finalForce = force;
            asyncTask.doTask(s -> dealPartInsert(tempBeginId, finalTempEndId, finalForce));
        }
    }

    /**
     * 多线程异步执行
     *
     * @param beginId
     *         起始id
     * @param endId
     *         结束id
     * @param force
     *         是否强制更新
     */
    private void dealPartInsert(Long beginId, Long endId, Boolean force) {
        for (int i = 0; i < (endId - beginId) / PAGE_SIZE + 1; i++) {
            Pageable pageable = new PageRequest(i, PAGE_SIZE, Sort.Direction.ASC, "id");
            List<EcContactEntity> contactEntities = contactManager.findByIdBetween(beginId, endId, pageable)
                    .getContent();
            if (contactEntities.size() == 0) {
                 continue;
            }
            List<PtCompanyCoordinate> coordinateEntities;
            try {
                coordinateEntities = coordinateManager.saveByContacts(contactEntities, force);
                esCompanyCoordinateManager.bulkIndexCompany(coordinateEntities, force);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * 修补一段时间内的数据
     *
     * @param begin
     *         开始时间
     * @param end
     *         结束时间
     * @throws IOException
     *         异常
     */
    public void partInsertDateBetween(String begin, String end, Boolean force) throws IOException {
        Date beginTime = DateUtil.beginOfDay(DateUtil.parseDate(begin));
        Date endTime = DateUtil.endOfDay(DateUtil.parseDate(end));

        logger.info("开始获取开始日期为" + begin + "，结束日期为" + end + "的经纬度");

        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(0, 1, sort);
        //找到第一个
        Page<EcContactEntity> page = contactManager.findByDateBetween(beginTime, endTime, pageable);
        if (page.getContent().size() == 0) {
            logger.info("该时间范围内没有数据");
            return;
        }

        Long beginId = page.getContent().get(0).getId();
        sort = new Sort(Sort.Direction.DESC, "id");
        pageable = new PageRequest(0, 1, sort);
        //找到最后一个
        page = contactManager.findByDateBetween(beginTime, endTime, pageable);
        Long endId = page.getContent().get(0).getId();
        partInsertIdBetween(beginId, endId, force);
    }

    /**
     * 定时修改不太靠谱或者没有坐标的数据
     *
     * @throws IOException
     *         异常
     */
    public void timingUpdateCoordinate() throws IOException {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(0, 1, sort);
        Page<PtCompanyCoordinate> page = coordinateManager.findByStatusOrAccuracy(pageable);
        List<EcContactEntity> contactEntities = new ArrayList<>();
        for (int i = 0; i < page.getTotalElements() / PAGE_SIZE + 1; i++) {
            pageable = new PageRequest(i, PAGE_SIZE, sort);
            List<PtCompanyCoordinate> coordinateEntities = coordinateManager.findByStatusOrAccuracy(pageable)
                    .getContent();
            for (PtCompanyCoordinate ptCompanyCoordinate : coordinateEntities) {
                EcContactEntity ecContactEntity = contactManager.findOne(ptCompanyCoordinate.getContactId());
                contactEntities.add(ecContactEntity);
            }
            List<PtCompanyCoordinate> entityList = coordinateManager.saveByContacts(contactEntities, null);
            esCompanyCoordinateManager.bulkIndexCompany(entityList, null);
        }
    }

    /**
     * 根据地址查经纬度
     *
     * @param address
     *         地址
     * @return 结果
     * @throws IOException
     *         异常
     */
    public Object getOutLocation(String address, String city) throws IOException {
        if (StrUtil.isEmpty(address) || StrUtil.isEmpty(city)) {
            return ResultGenerator.genFailResult("城市和地址不能为空");
        }
        List<String> coordinateEntities = geoCoordinateService.getOutLocationByParameter(address, city);
        if (CollectionUtil.isNotEmpty(coordinateEntities)) {
            return ResultGenerator.genSuccessResult(coordinateEntities);
        }
        return ResultGenerator.genFailResult("没有查到该地址的经纬度");
    }
}
