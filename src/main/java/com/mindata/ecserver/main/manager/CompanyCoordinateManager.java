package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.secondary.CompanyCoordinateEntity;
import com.mindata.ecserver.main.repository.secondary.CompanyCoordinateRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.mindata.ecserver.global.Constant.NONE_ADDRESS;
import static com.mindata.ecserver.global.Constant.NORELIABLE_ACCURAY;

/**
 * @author hanliqiang wrote on 2017/11/24
 */
@Service
public class CompanyCoordinateManager {
    @Resource
    private CompanyCoordinateRepository coordinateRepository;
    @Resource
    private EsCompanyCoordinateManager esCompanyCoordinateManager;

    /**
     * 将坐标信息保存到es
     *
     * @param companyCoordinateEntities 集合
     * @param contactId                 未推送表的Id
     */
    public void saveCoordinate(List<CompanyCoordinateEntity> companyCoordinateEntities, Long contactId) {
       List<CompanyCoordinateEntity> entityList = add(companyCoordinateEntities,contactId);
       for(CompanyCoordinateEntity coordinateEntity : entityList){
           esCompanyCoordinateManager.dbToEs(coordinateEntity);
       }
    }

    /**
     *向数据库添加位置信息
     * @param companyCoordinateEntities 集合
     * @param contactId 公司Id
     * @return 结果
     */
    private List<CompanyCoordinateEntity> add(List<CompanyCoordinateEntity> companyCoordinateEntities,Long contactId){
        List<CompanyCoordinateEntity> entities = new ArrayList<>();
        List<CompanyCoordinateEntity> coordinateEntities = coordinateRepository.findByContactId(contactId);
        for (CompanyCoordinateEntity companyCoordinateEntity : companyCoordinateEntities) {
            CompanyCoordinateEntity entity;
            //如果存在更新
            if (coordinateEntities.size() > 0) {
                for (CompanyCoordinateEntity coordinateEntity : coordinateEntities) {
                    coordinateEntity.setStatus(companyCoordinateEntity.getStatus());
                    coordinateEntity.setAccuracy(companyCoordinateEntity.getAccuracy());
                    coordinateEntity.setLevel(companyCoordinateEntity.getLevel());
                    coordinateEntity.setBaiduCoordinate(companyCoordinateEntity.getBaiduCoordinate());
                    coordinateEntity.setGaodeCoordinate(companyCoordinateEntity.getGaodeCoordinate());
                    coordinateEntity.setQueryCondition(companyCoordinateEntity.getQueryCondition());
                    coordinateEntity.setQueryConditionValue(companyCoordinateEntity.getQueryConditionValue());
                    entity = coordinateRepository.save(coordinateEntity);
                    entities.add(entity);
                }
            }else{
                companyCoordinateEntity.setContactId(contactId);
                entity = coordinateRepository.save(companyCoordinateEntity);
                entities.add(entity);
            }
        }
        return entities;
    }
    /**
     * 查询不太靠谱或者没有坐标的数据
     *
     * @param pageable 分页
     * @return 结果
     */
    public Page<CompanyCoordinateEntity> findByStatusOrAccuracy(Pageable pageable) {
        return coordinateRepository.findByStatusOrAccuracy(NONE_ADDRESS, NORELIABLE_ACCURAY, pageable);
    }

}
