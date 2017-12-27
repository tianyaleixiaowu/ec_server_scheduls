package com.mindata.ecserver.main.service;

import com.mindata.ecserver.global.async.AsyncTask;
import com.mindata.ecserver.main.manager.EcCustomerOperationManager;
import com.mindata.ecserver.main.manager.PtCustomerStateManager;
import com.mindata.ecserver.main.model.primary.EcCustomerOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.mindata.ecserver.global.GeoConstant.PAGE_SIZE;
import static com.mindata.ecserver.global.GeoConstant.PER_THREAD_DEAL_COUNT;

/**
 * 定时操作PtCustomerState表
 *
 * @author wuweifeng wrote on 2017/12/27.
 */
@Service
public class PtCustomerStateService {
    @Resource
    private EcCustomerOperationManager ecCustomerOperationManager;
    @Resource
    private PtCustomerStateManager ptCustomerStateManager;
    @Resource
    private AsyncTask asyncTask;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public void dealCustomerState() {
        //获取CustomerState最新的一条数据，看看customer_Operation_id，比对一下ecCustomerOperation表的id
        Long beginOperationId = ptCustomerStateManager.findLastOperationId();
        //结束id
        Long endOperationId = ecCustomerOperationManager.findLastOperationId();

        Integer count = ecCustomerOperationManager.countByIdBetween(beginOperationId, endOperationId);
        //共多少线程
        Integer size = count / PER_THREAD_DEAL_COUNT + 1;

        //判断要查多少页
        for (int i = 0; i < size; i++) {
            Long tempBeginId = beginOperationId + 1 + PER_THREAD_DEAL_COUNT * i;
            Long tempEndId = tempBeginId + PER_THREAD_DEAL_COUNT - 1;
            if (tempEndId > endOperationId) {
                tempEndId = endOperationId;
            }
            Long finalTempEndId = tempEndId;
            asyncTask.doTask(s -> dealPartInsert(tempBeginId, finalTempEndId));
        }
    }

    private void dealPartInsert(Long beginId, Long endId) {
        logger.info("线程id为" + Thread.currentThread().getId() + "开始处理DB插入的事");
        logger.info("起始id" + beginId + ", 终止id" + endId);
        List<EcCustomerOperation> customerOperations;
        for (int i = 0; i < (endId - beginId) / PAGE_SIZE + 1; i++) {
            Pageable pageable = new PageRequest(i, PAGE_SIZE, Sort.Direction.ASC, "id");
            customerOperations = ecCustomerOperationManager.findByIdBetween(beginId, endId, pageable)
                    .getContent();
            if (customerOperations.size() == 0) {
                continue;
            }
            //处理数据
            ptCustomerStateManager.parseOperationData(customerOperations);
        }
    }
}
