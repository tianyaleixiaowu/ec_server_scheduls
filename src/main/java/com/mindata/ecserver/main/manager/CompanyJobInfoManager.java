package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.thirdly.CompanyJobInfo;
import com.mindata.ecserver.main.model.thirdly.CompanyJobInfo51;
import com.mindata.ecserver.main.model.thirdly.CompanyJobInfoGanji;
import com.mindata.ecserver.main.model.thirdly.CompanyJobInfoZl;
import com.mindata.ecserver.main.repository.thirdly.CompanyJobInfo51Repository;
import com.mindata.ecserver.main.repository.thirdly.CompanyJobInfoGanjiRepository;
import com.mindata.ecserver.main.repository.thirdly.CompanyJobInfoRepository;
import com.mindata.ecserver.main.repository.thirdly.CompanyJobInfoZlRepository;
import com.xiaoleilu.hutool.util.StrUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

import static com.mindata.ecserver.global.Constant.DOUHAO;

/**
 * @author wuweifeng wrote on 2017/11/8.
 */
@Service
public class CompanyJobInfoManager {
    @Resource
    private CompanyJobInfoRepository companyJobInfoRepository;
    @Resource
    private CompanyJobInfo51Repository companyJobInfo51Repository;
    @Resource
    private CompanyJobInfoZlRepository companyJobInfoZlRepository;
    @Resource
    private CompanyJobInfoGanjiRepository companyJobInfoGanjiRepository;

    /**
     * 获取招聘工作名称、福利、职位描述、公司简介
     *
     * @param companyId compId
     * @return 所有的工作名称
     */
    public List<String> getExtraInfo(Long companyId) {
        StringBuilder jobName = new StringBuilder();
        StringBuilder welfare = new StringBuilder();
        StringBuilder posDes = new StringBuilder();

        List<CompanyJobInfo> companyJobInfos = companyJobInfoRepository.findByCompId(companyId);
        List<CompanyJobInfo51> companyJobInfos1 = companyJobInfo51Repository.findByCompId(companyId);
        List<CompanyJobInfoGanji> companyJobInfos2 = companyJobInfoGanjiRepository.findByCompId(companyId);
        List<CompanyJobInfoZl> companyJobInfos3 = companyJobInfoZlRepository.findByCompId(companyId);

        for (CompanyJobInfo jobInfo : companyJobInfos) {
            if (StrUtil.isNotEmpty(jobInfo.getJobName())) {
                jobName.append(jobInfo.getJobName()).append(DOUHAO);
            }
            if (StrUtil.isNotEmpty(jobInfo.getWelfare())) {
                //避免重复
                welfare.append(jobInfo.getWelfare()).append(DOUHAO);
            }
            if (StrUtil.isNotEmpty(jobInfo.getPosDes())) {
                posDes.append(jobInfo.getPosDes()).append(DOUHAO);
            }
        }
        for (CompanyJobInfo51 jobInfo : companyJobInfos1) {
            if (StrUtil.isNotEmpty(jobInfo.getJobName()) && !jobName.toString().contains(jobInfo.getJobName()) ) {
                jobName.append(jobInfo.getJobName()).append(DOUHAO);
            }

            if (StrUtil.isNotEmpty(jobInfo.getWelfare())) {
                welfare.append(jobInfo.getWelfare()).append(DOUHAO);
            }
            if (StrUtil.isNotEmpty(jobInfo.getPosDes())) {
                posDes.append(jobInfo.getPosDes()).append(DOUHAO);
            }
        }
        for (CompanyJobInfoGanji jobInfo : companyJobInfos2) {
            if (StrUtil.isNotEmpty(jobInfo.getJobName())) {
                jobName.append(jobInfo.getJobName()).append(DOUHAO);
            }
            if (StrUtil.isNotEmpty(jobInfo.getWelfare())) {
                welfare.append(jobInfo.getWelfare()).append(DOUHAO);
            }

            if (StrUtil.isNotEmpty(jobInfo.getPosDes())) {
                posDes.append(jobInfo.getPosDes()).append(DOUHAO);
            }
        }
        for (CompanyJobInfoZl jobInfo : companyJobInfos3) {
            if (StrUtil.isNotEmpty(jobInfo.getJobName())) {
                jobName.append(jobInfo.getJobName());
            }
            if (StrUtil.isNotEmpty(jobInfo.getWelfare())) {
                welfare.append(jobInfo.getWelfare());
            }
            if (StrUtil.isNotEmpty(jobInfo.getPosDes())) {
                posDes.append(jobInfo.getPosDes());
            }
        }
        return Arrays.asList(jobName.toString(), welfare.toString(), posDes.toString());
    }

}
