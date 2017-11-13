package com.mindata.ecserver.main.service;

import com.mindata.ecserver.main.manager.EsVocationCodeManager;
import com.mindata.ecserver.main.manager.VocationCodeManager;
import com.mindata.ecserver.main.model.es.EsVocationCode;
import com.mindata.ecserver.main.model.primary.EcVoccationCodeEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EsVocationCodeService {
    @Resource
    private VocationCodeManager vocationCodeManager;
    @Resource
    private EsVocationCodeManager esVocationCodeManager;
//    将所有数据插入到ES
    public void totalInsert() {
        List<EcVoccationCodeEntity> codeEntityList = vocationCodeManager.findVocationCode();
        if(codeEntityList.size()==0){
            return;
        }
        esVocationCodeManager.bulkIndex(codeEntityList.stream().map(this::convert).collect(Collectors.toList()));
    }

    private EsVocationCode convert(EcVoccationCodeEntity ecVoccationCodeEntity) {
        EsVocationCode esVocationCode = new EsVocationCode();
        esVocationCode.setVocationCode(ecVoccationCodeEntity.getVocationCode());
        esVocationCode.setParentCode(ecVoccationCodeEntity.getParentCode());
        esVocationCode.setVocationName(ecVoccationCodeEntity.getVocationName());
        esVocationCode.setVocationGrade(ecVoccationCodeEntity.getVocationGrade());
        return esVocationCode;
    }
}
