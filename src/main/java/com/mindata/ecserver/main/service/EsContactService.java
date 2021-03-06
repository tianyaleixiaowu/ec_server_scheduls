package com.mindata.ecserver.main.service;

import com.mindata.ecserver.global.util.CommonUtil;
import com.mindata.ecserver.main.manager.CompanyIndustryInfoManager;
import com.mindata.ecserver.main.manager.CompanyJobInfoManager;
import com.mindata.ecserver.main.manager.ContactManager;
import com.mindata.ecserver.main.manager.EsContactManager;
import com.mindata.ecserver.main.model.es.EsContact;
import com.mindata.ecserver.main.model.primary.EcContactEntity;
import com.xiaoleilu.hutool.convert.Convert;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 从db读取数据到elasticsearch中
 *
 * @author wuweifeng wrote on 2017/11/8.
 */
@Service
public class EsContactService {
    private static final int PAGE_SIZE = 500;
    @Resource
    private CompanyJobInfoManager companyJobInfoManager;
    @Resource
    private ContactManager contactManager;
    @Resource
    private EsContactManager esContactManager;
    @Resource
    private CompanyIndustryInfoManager industryInfoManager;

    /**
     * 将EcContact信息导入ES
     *
     * @param limitTime
     *         是否显示只导入昨天的
     */
    public void dbToEs(boolean limitTime) {
        EsContact esContact = esContactManager.findTheLastEsContact();
        //ES一条数据都没有，说明是新库
        if (esContact == null) {
            //全量插入
            totalInsert();
            return;
        }

        //该记录在数据库的id
        Long id = esContact.getId();
        //如果只导入昨天的
        if (limitTime) {
            partInsertIdAfterAndTimeBefore(id);
        } else {
            partInsertAfter(id);
        }
    }

    /**
     * 导入全部数据到es
     */
    public void forceTotal() {
        totalInsert();
    }

    private void totalInsert() {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(0, 1, sort);
        Page<EcContactEntity> page = contactManager.findContact(pageable);
        //判断要查多少页
        for (int i = 0; i < page.getTotalElements() / PAGE_SIZE + 1; i++) {
            System.out.println("当前是第几页" + i);
            pageable = new PageRequest(i, PAGE_SIZE, sort);
            List<EcContactEntity> contactEntities = contactManager.findContact(pageable).getContent();
            esContactManager.bulkIndex(contactEntities.stream().map(this::convert).collect(Collectors.toList()));
        }
    }

    /**
     * 导入id比当前ES的大的所有数据
     *
     * @param id
     *         ES最新的id
     */
    private void partInsertAfter(Long id) {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        //去数据库判断比该记录晚的，增量插入ES
        Pageable pageable = new PageRequest(0, 1, sort);
        Page<EcContactEntity> page = contactManager.findByIdGreaterThan(id, pageable);
        //没有新数据
        if (page.getTotalElements() == 0) {
            return;
        }
        //判断要查多少页
        for (int i = 0; i < page.getTotalElements() / PAGE_SIZE + 1; i++) {
            pageable = new PageRequest(i, PAGE_SIZE, sort);
            List<EcContactEntity> contactEntities = contactManager.findByIdGreaterThan
                    (id, pageable).getContent();
            esContactManager.bulkIndex(contactEntities.stream().map(this::convert).collect(Collectors.toList()));
        }
    }

    /**
     * 删除某些ids
     *
     * @param ids
     *         ids
     */
    public void deleteByIds(String ids) {
        if (ids.endsWith(",")) {
            ids = ids.substring(0, ids.length() - 1);
        }
        String[] array = ids.split(",");
        for (String id : array) {
            EsContact esContact = esContactManager.findById(Long.valueOf(id));
            esContact.setState(3);
            esContactManager.save(esContact);
        }
    }

    /**
     * 更新一部分id的值到ES
     *
     * @param beginId
     *         开始id
     * @param endId
     *         结束id
     */
    public void partInsertBetween(Long beginId, Long endId) {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        //去数据库查询该范围的集合，插入到ES
        Pageable pageable = new PageRequest(0, 1, sort);
        Page<EcContactEntity> page = contactManager.findByIdBetween(beginId, endId, pageable);
        //没有新数据
        if (page.getTotalElements() == 0) {
            return;
        }
        //判断要查多少页
        for (int i = 0; i < page.getTotalElements() / PAGE_SIZE + 1; i++) {
            pageable = new PageRequest(i, PAGE_SIZE, sort);
            List<EcContactEntity> contactEntities = contactManager.findByIdBetween
                    (beginId, endId, pageable).getContent();
            esContactManager.bulkIndex(contactEntities.stream().map(this::convert).collect(Collectors.toList()));
        }
    }

    /**
     * 导入id比当前id大，且时间为昨天的数据
     *
     * @param id
     *         ES中的id
     */
    private void partInsertIdAfterAndTimeBefore(Long id) {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        //去数据库判断比该记录晚的，增量插入ES
        Pageable pageable = new PageRequest(0, 1, sort);
        //3点时取今天0点时间
        Date beginOfDay = DateUtil.beginOfDay(CommonUtil.getNow());
        Page<EcContactEntity> page = contactManager.findByIdGreaterThanAndCreateTimeLessThan(id, beginOfDay, pageable);
        //没有新数据
        if (page.getTotalElements() == 0) {
            return;
        }
        //判断要查多少页
        for (int i = 0; i < page.getTotalElements() / PAGE_SIZE + 1; i++) {
            pageable = new PageRequest(i, PAGE_SIZE, sort);
            List<EcContactEntity> contactEntities = contactManager.findByIdGreaterThanAndCreateTimeLessThan
                    (id, beginOfDay, pageable)
                    .getContent();
            esContactManager.bulkIndex(contactEntities.stream().map(this::convert).collect(Collectors.toList()));
        }
    }

    /**
     * 更新某个字段(companyScore)
     * 列名
     */
    public void partUpdateByColumn() {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        //去数据库查询该范围的集合，插入到ES
        Pageable pageable = new PageRequest(0, 1, sort);
        Page<EcContactEntity> page = contactManager.findAll(pageable);
        //没有新数据
        if (page.getTotalElements() == 0) {
            return;
        }
        //判断要查多少页
        for (int i = 0; i < page.getTotalElements() / PAGE_SIZE + 1; i++) {
            pageable = new PageRequest(i, PAGE_SIZE, sort);
            List<EcContactEntity> contactEntities = contactManager.findAll(pageable).getContent();
            esContactManager.bulkIndex(contactEntities.stream().map(this::convertOneColumn).collect(Collectors.toList
                    ()));
        }

    }

    private EsContact convertOneColumn(EcContactEntity ecContactEntity) {
        EsContact esContact = esContactManager.findById(ecContactEntity.getId());
        esContact.setCompanyScore(CommonUtil.cutDouble2(ecContactEntity.getCompanyScore()) * 100);
        return esContact;
    }

    /**
     * 将DB读取的entity转为ES的对象
     *
     * @param ecContactEntity
     *         ecContactEntity
     * @return ESContact
     */
    private EsContact convert(EcContactEntity ecContactEntity) {
        EsContact esContact = new EsContact();
        esContact.setId(ecContactEntity.getId());
        esContact.setAddress(ecContactEntity.getAddress());
        esContact.setCompId(ecContactEntity.getCompId());
        esContact.setName(ecContactEntity.getName());
        esContact.setCompany(ecContactEntity.getCompany());
        esContact.setMobile(ecContactEntity.getMobile());
        esContact.setPhone(ecContactEntity.getPhone());
        esContact.setAddress(ecContactEntity.getAddress());
        esContact.setMemo(ecContactEntity.getMemo());
        esContact.setProvince(Convert.toInt(ecContactEntity.getProvince()));
        esContact.setCity(Convert.toInt(ecContactEntity.getCity()));
        esContact.setWebsiteId(ecContactEntity.getWebsiteId());
        esContact.setVocation(ecContactEntity.getVocation());
        esContact.setMemberSizeTag(ecContactEntity.getMemberSizeTag());
        esContact.setNeedSale(ecContactEntity.getNeedSale() ? 1 : 0);
        String ipc = "未备案";
        if (StrUtil.equals("Y", ecContactEntity.getIpcFlag())) {
            ipc = "已备案";
        }
        esContact.setIpcFlag(ipc);
        esContact.setState(ecContactEntity.getState());
        esContact.setMainJob(ecContactEntity.getMainJob());
        esContact.setCreateTime(ecContactEntity.getCreateTime());
        esContact.setCompanyScore(CommonUtil.cutDouble2(ecContactEntity.getCompanyScore()) * 100);
        //下面的都是别的表聚合来的数据
        List<String> extraInfo = companyJobInfoManager.getExtraInfo(ecContactEntity.getCompId());
        esContact.setJobName(extraInfo.get(0));
        esContact.setWelfare(extraInfo.get(1));
        esContact.setPosDes(extraInfo.get(2));
        //将行业名称添加到es
        List<String> industryList = industryInfoManager.getIndustryAndComintroInfoForEs(ecContactEntity.getCompId(),
                ecContactEntity.getCompany());
        esContact.setIndustry(industryList.get(0));
        esContact.setComintro(industryList.get(1));

        esContact.setInsertTime(CommonUtil.getTimeStamp());
        return esContact;
    }


}
