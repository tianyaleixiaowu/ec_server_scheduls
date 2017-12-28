package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.global.util.CommonUtil;
import com.mindata.ecserver.main.model.primary.EcCustomerOperation;
import com.mindata.ecserver.main.model.secondary.PtCustomerState;
import com.mindata.ecserver.main.repository.secondary.PtCustomerStateRepository;
import com.xiaoleilu.hutool.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        Sort sort = new Sort(Sort.Direction.DESC, "customerOperationId");
        Pageable pageable = new PageRequest(0, 1, sort);
        Page<PtCustomerState> page = find(pageable);
        //要查询的起始Operation的id
        Long customerStateId = 0L;
        if (page.getTotalElements() > 0L) {
            customerStateId = page.getContent().get(0).getCustomerOperationId();
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
            PtCustomerState ptCustomerState = ptCustomerStateRepository.findByCustomerOperationId(ecCustomerOperation
                    .getId());
            if (ptCustomerState != null) {
                continue;
            }
            dealOperationData(ecCustomerOperation);
        }
        logger.info("线程id为" + Thread.currentThread().getId() + "处理完毕了一页，50条");
    }

    private void dealOperationData(EcCustomerOperation ecCustomerOperation) {
        PtCustomerState ptCustomerState = new PtCustomerState();
        ptCustomerState.setCreateTime(CommonUtil.getNow());
        ptCustomerState.setUpdateTime(CommonUtil.getNow());
        ptCustomerState.setCrmId(ecCustomerOperation.getCrmId());
        ptCustomerState.setCustomerOperationId(ecCustomerOperation.getId());
        ptCustomerState.setFollowUser(ecCustomerOperation.getOperateUser());
        ptCustomerState.setOperateTime(ecCustomerOperation.getOperateTime());
        ptCustomerState.setOperateType(ecCustomerOperation.getOperateType());
        ptCustomerState.setOldData(ecBjmdOldDataManager.existCrmId(ecCustomerOperation.getCrmId()));
        String content = ecCustomerOperation.getContent();
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
                ) {
            ptCustomerState.setSaleState(1);
        } else if(StrUtil.containsIgnoreCase(content, "合作成交")
                && StrUtil.containsIgnoreCase(ecCustomerOperation.getOperateType(), "更新客户阶段")) {
            ptCustomerState.setSaleState(2);
        } else {
            ptCustomerState.setSaleState(0);
        }
        ptCustomerState.setStatusCode(ecCustomerManager.statusCode(ecCustomerOperation.getCrmId()));
        ptCustomerStateRepository.save(ptCustomerState);
    }
}
