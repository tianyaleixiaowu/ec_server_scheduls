package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.global.util.CommonUtil;
import com.mindata.ecserver.main.model.primary.EcCustomerOperation;
import com.mindata.ecserver.main.model.secondary.PtCustomerState;
import com.mindata.ecserver.main.repository.secondary.PtCustomerStateRepository;
import com.xiaoleilu.hutool.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 客户每天的状态变更
 *
 * @author wuweifeng wrote on 2017/12/27.
 */
@Component
public class PtCustomerStateManager {
    @Resource
    private PtCustomerStateRepository ptCustomerStateRepository;
    @Resource
    private EcBjmdOldDataManager ecBjmdOldDataManager;
    @Resource
    private PtPushResultManager ptPushResultManager;
    @Resource
    private EcCustomerManager ecCustomerManager;
    @Resource
    private PtPhoneHistoryManager ptPhoneHistoryManager;
    @Resource
    private EcCustomerOperationManager ecCustomerOperationManager;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 分页查询customerState
     *
     * @param pageable
     *         分页
     * @return 集合
     */
    public Page<PtCustomerState> find(Pageable pageable) {
        return ptCustomerStateRepository.findAll(pageable);
    }

    /**
     * 查询最新的一条数据的OperationId
     *
     * @return id
     */
    public Long findLastOperationId() {
        PtCustomerState ptCustomerState = ptCustomerStateRepository.findFirstByOrderByCustomerOperationIdDesc();
        Long customerStateId = 0L;
        if (ptCustomerState != null) {
            customerStateId = ptCustomerState.getCustomerOperationId();
        }
        return customerStateId;
    }

    /**
     * 处理Operation数据
     *
     * @param customerOperationList
     *         集合
     */
    public void parseOperationData(List<EcCustomerOperation> customerOperationList) {
        for (EcCustomerOperation ecCustomerOperation : customerOperationList) {
            dealOperationData(ecCustomerOperation, false);
        }
        logger.info("线程id为" + Thread.currentThread().getId() + "处理完毕了一页，50条");
    }

    /**
     * 每次处理1条，给kafka队列用的
     * @param ecCustomerOperationId
     *  ecCustomerOperationId
     */
    public void dealOperationData(Long ecCustomerOperationId, boolean force) {
        dealOperationData(ecCustomerOperationManager.findOne(ecCustomerOperationId), force);
    }

    private void dealOperationData(EcCustomerOperation ecCustomerOperation, boolean force) {
        if (ecCustomerOperation == null) {
            return;
        }
        PtCustomerState ptCustomerState = ptCustomerStateRepository.findByCustomerOperationId(ecCustomerOperation
                .getId());
        if (ptCustomerState != null && !force) {
            return;
        }
        String content = ecCustomerOperation.getContent();
        if (ptCustomerState == null) {
            ptCustomerState = new PtCustomerState();
            ptCustomerState.setCreateTime(CommonUtil.getNow());
            ptCustomerState.setCrmId(ecCustomerOperation.getCrmId());
            ptCustomerState.setCustomerOperationId(ecCustomerOperation.getId());
            ptCustomerState.setFollowUser(ecCustomerOperation.getOperateUser());
            ptCustomerState.setOperateTime(ecCustomerOperation.getOperateTime());
            ptCustomerState.setOperateType(ecCustomerOperation.getOperateType());
            ptCustomerState.setOldData(ecBjmdOldDataManager.existCrmId(ecCustomerOperation.getCrmId()));
            ptCustomerState.setStatusCode(ecCustomerManager.statusCode(ecCustomerOperation.getCrmId()));
            if (StrUtil.containsIgnoreCase(content, "百度技术")
                    || StrUtil.containsIgnoreCase(content, "网站咨询")
                    || StrUtil.containsIgnoreCase(content, "400")) {
                ptCustomerState.setSourceFrom(2);
            } else if (ptPushResultManager.existCrmId(ecCustomerOperation.getCrmId())) {
                //麦达
                ptCustomerState.setSourceFrom(1);
            } else {
                //其他
                ptCustomerState.setSourceFrom(3);
            }
        }
        ptCustomerState.setUpdateTime(CommonUtil.getNow());

        if (StrUtil.containsIgnoreCase(content, "初步意向")
                || StrUtil.containsIgnoreCase(content, "意向客户")
                || StrUtil.containsIgnoreCase(content, "L2")
                || StrUtil.containsIgnoreCase(content, "L3")
                || StrUtil.containsIgnoreCase(content, "L4")
                || StrUtil.containsIgnoreCase(content, "L5")
                || StrUtil.containsIgnoreCase(content, "L6")
                || (!StrUtil.containsIgnoreCase(content, "客户分组") && StrUtil.containsIgnoreCase(ecCustomerOperation
                .getOperateType(), "更新客户资料"))
                || StrUtil.containsIgnoreCase(ecCustomerOperation.getOperateType(), "拜访客户")
                || (StrUtil.containsIgnoreCase(content, "意向") && StrUtil.containsIgnoreCase(content, "客户分组"))
                || ptPhoneHistoryManager.isIntent(ecCustomerOperation.getCrmId(), ecCustomerOperation.getOperateTime())) {
            ptCustomerState.setSaleState(1);
        } else if(StrUtil.containsIgnoreCase(content, "合作成交")
                && StrUtil.containsIgnoreCase(ecCustomerOperation.getOperateType(), "更新客户阶段")) {
            ptCustomerState.setSaleState(2);
        } else {
            ptCustomerState.setSaleState(0);
        }

        ptCustomerStateRepository.save(ptCustomerState);
    }
}
