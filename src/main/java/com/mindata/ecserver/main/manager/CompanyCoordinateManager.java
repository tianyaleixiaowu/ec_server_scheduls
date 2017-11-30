package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.es.EsCompanyCoordinate;
import com.mindata.ecserver.main.model.secondary.CompanyCoordinateEntity;
import com.mindata.ecserver.main.repository.secondary.CompanyCoordinateRepository;
import com.xiaoleilu.hutool.util.StrUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
     * 将坐标信息保存到数据库
     *
     * @param companyCoordinateEntities 集合
     * @param contactId                 未推送表的Id
     */
    public void saveCoordinate(List<CompanyCoordinateEntity> companyCoordinateEntities, Long contactId) {
        List<CompanyCoordinateEntity> coordinateEntities = coordinateRepository.findByContactId(contactId);
        for (CompanyCoordinateEntity companyCoordinateEntity : companyCoordinateEntities) {
            //如果已经存在则更新
            if (coordinateEntities.size() > 0) {
                for (CompanyCoordinateEntity coordinateEntity : coordinateEntities) {
                    coordinateEntity.setStatus(companyCoordinateEntity.getStatus());
                    coordinateEntity.setAccuracy(companyCoordinateEntity.getAccuracy());
                    coordinateEntity.setLevel(companyCoordinateEntity.getLevel());
                    coordinateEntity.setBaiduCoordinate(companyCoordinateEntity.getBaiduCoordinate());
                    coordinateEntity.setGaodeCoordinate(companyCoordinateEntity.getGaodeCoordinate());
                    coordinateEntity.setQueryCondition(companyCoordinateEntity.getQueryCondition());
                    coordinateEntity.setQueryConditionValue(companyCoordinateEntity.getQueryConditionValue());
                    coordinateRepository.save(coordinateEntity);
                    return;
                }
            }
            //反之新增
            companyCoordinateEntity.setContactId(contactId);
            CompanyCoordinateEntity coordinateEntity = coordinateRepository.save(companyCoordinateEntity);
            //es插值
            EsCompanyCoordinate esCompanyCoordinate = new EsCompanyCoordinate();
            BeanUtils.copyProperties(coordinateEntity, esCompanyCoordinate);
            StringBuffer stringBuffer = new StringBuffer();
            StringBuffer buffer = new StringBuffer();
            if (StrUtil.isNotEmpty(coordinateEntity.getBaiduCoordinate())) {
                String[] baiduCoordinateArr = coordinateEntity.getBaiduCoordinate().split(",");
                stringBuffer.append(baiduCoordinateArr[1] + ",").append(baiduCoordinateArr[0]);
                esCompanyCoordinate.setBaiduCoordinate(stringBuffer.toString());
                if (StrUtil.isNotEmpty(coordinateEntity.getGaodeCoordinate())) {
                    String[] gaodeCoordinateArr = coordinateEntity.getGaodeCoordinate().split(",");
                    buffer.append(gaodeCoordinateArr[1] + ",").append(gaodeCoordinateArr[0]);
                    esCompanyCoordinate.setGaodeCoordinate(buffer.toString());
                }
                esCompanyCoordinateManager.index(esCompanyCoordinate);
            } else {
                if (StrUtil.isNotEmpty(coordinateEntity.getGaodeCoordinate())) {
                    String[] gaodeCoordinateArr = coordinateEntity.getGaodeCoordinate().split(",");
                    buffer.append(gaodeCoordinateArr[1] + ",").append(gaodeCoordinateArr[0]);
                    esCompanyCoordinate.setGaodeCoordinate(buffer.toString());
                }
                esCompanyCoordinateManager.index(esCompanyCoordinate);
            }
        }
    }

    /**
     * 查询无地址的数据
     * @param pageable
     * @return
     */
    public Page<CompanyCoordinateEntity> findByStatus(Pageable pageable){
         return coordinateRepository.findByStatus(NONE_ADDRESS,pageable);
    }

    /**
     * 查询不太靠谱的数据
     * @param pageable
     * @return
     */
    public Page<CompanyCoordinateEntity> findByAccuracy(Pageable pageable){
        return coordinateRepository.findByAccuracy(NORELIABLE_ACCURAY,pageable);
    }

    /**
     * 查询不太靠谱或者没有坐标的数据
     * @param pageable
     * @return
     */
    public Page<CompanyCoordinateEntity> findByStatusOrAccuracy(Pageable pageable){
        return coordinateRepository.findByStatusOrAccuracy(NONE_ADDRESS,NORELIABLE_ACCURAY,pageable);
    }
}
