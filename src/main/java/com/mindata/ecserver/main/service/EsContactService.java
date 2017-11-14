package com.mindata.ecserver.main.service;

import com.mindata.ecserver.global.util.CommonUtil;
import com.mindata.ecserver.main.manager.CompanyIndustryInfoManager;
import com.mindata.ecserver.main.manager.CompanyJobInfoManager;
import com.mindata.ecserver.main.manager.ContactManager;
import com.mindata.ecserver.main.manager.EsContactManager;
import com.mindata.ecserver.main.model.es.EsContact;
import com.mindata.ecserver.main.model.primary.EcContactEntity;
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

    public void dbToEs() {
        EsContact esContact = esContactManager.findTheLastEsContact();
        //ES一条数据都没有，说明是新库
        if (esContact == null) {
            //全量插入
            totalInsert();
            return;
        }

        //该记录在数据库的创建时间
        Date createTime = esContact.getCreateTime();
        partInsertAfter(createTime);
    }

    /**
     * 导入全部数据到es
     */
    public void forseTotal() {
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

    private void partInsertAfter(Date createTime) {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        //去数据库判断比该记录晚的，增量插入ES
        Pageable pageable = new PageRequest(0, 1, sort);
        Page<EcContactEntity> page = contactManager.findContactByCreateTimeAfter(createTime, pageable);
        //没有新数据
        if (page.getTotalElements() == 0) {
            return;
        }
        //判断要查多少页
        for (int i = 0; i < page.getTotalElements() / PAGE_SIZE + 1; i++) {
            pageable = new PageRequest(i, PAGE_SIZE, sort);
            List<EcContactEntity> contactEntities = contactManager.findContactByCreateTimeAfter(createTime, pageable)
                    .getContent();
            esContactManager.bulkIndex(contactEntities.stream().map(this::convert).collect(Collectors.toList()));
        }
    }

    /**
     * 将DB读取的entity转为ES的对象
     * @param ecContactEntity
     * ecContactEntity
     * @return
     * ESContact
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
        esContact.setProvince(ecContactEntity.getProvince());
        esContact.setCity(ecContactEntity.getCity());
        esContact.setWebsiteId(ecContactEntity.getWebsiteId());
        esContact.setVocation(ecContactEntity.getVocation());
        esContact.setMemberSizeTag(ecContactEntity.getMemberSizeTag());
        String ipc = "未备案";
        if (StrUtil.equals("Y", ecContactEntity.getIpcFlag())) {
            ipc = "已备案";
        }
        esContact.setIpcFlag(ipc);
        esContact.setState(ecContactEntity.getState());
        esContact.setMainJob(ecContactEntity.getMainJob());
        esContact.setCreateTime(ecContactEntity.getCreateTime());
        //下面的都是别的表聚合来的数据
        List<String> extroInfo = companyJobInfoManager.getExtraInfo(ecContactEntity.getCompId());
        esContact.setJobName(extroInfo.get(0));
        esContact.setWelfare(extroInfo.get(1));
        esContact.setPosDes(extroInfo.get(2));
        esContact.setComintro(extroInfo.get(3));
        //将行业名称添加到es
        List<String> industryList =  industryInfoManager.getIndustryInfoForEs(ecContactEntity.getCompId());
        esContact.setIndustry(industryList.get(0));
        esContact.setInsertTime(CommonUtil.getTimeStamp());
        return esContact;
    }


}
