package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.thirdly.*;
import com.mindata.ecserver.main.repository.thirdly.*;
import com.xiaoleilu.hutool.util.StrUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

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
     * @param companyId
     *         compId
     * @return 所有的工作名称
     */
    public List<String> getExtraInfo(Long companyId) {
        StringBuilder jobName = new StringBuilder();
        StringBuilder welfare = new StringBuilder();
        StringBuilder posDes = new StringBuilder();
        StringBuilder comintro = new StringBuilder();

        List<CompanyJobInfo> companyJobInfos = companyJobInfoRepository.findByCompId(companyId);
        List<CompanyJobInfo51> companyJobInfos1 = companyJobInfo51Repository.findByCompId(companyId);
        List<CompanyJobInfoGanji> companyJobInfos2 = companyJobInfoGanjiRepository.findByCompId(companyId);
        List<CompanyJobInfoZl> companyJobInfos3 = companyJobInfoZlRepository.findByCompId(companyId);

        for (CompanyJobInfo jobInfo : companyJobInfos) {
            if (StrUtil.isNotEmpty(jobInfo.getJobName())) {
                jobName.append(jobInfo.getJobName());
            }
            if (StrUtil.isNotEmpty(jobInfo.getWelfare())) {
                //避免重复
                welfare.append(jobInfo.getWelfare());
            }
            if (StrUtil.isNotEmpty(jobInfo.getPosDes())) {
                posDes.append(jobInfo.getPosDes());
            }
            if (StrUtil.isNotEmpty(jobInfo.getComintro())) {
                comintro.append(jobInfo.getComintro());
            }
        }
        for (CompanyJobInfo51 jobInfo : companyJobInfos1) {
            if (!jobName.toString().contains(jobInfo.getJobName())) {
                jobName.append(jobInfo.getJobName());
            }
            if (jobInfo.getWelfare() != null) {
                welfare.append(jobInfo.getWelfare());
            }
            if (jobInfo.getPosDes() != null) {
                posDes.append(jobInfo.getPosDes());
            }
            if (!comintro.toString().contains(jobInfo.getComintro())) {
                comintro.append(jobInfo.getComintro());
            }
        }
        for (CompanyJobInfoGanji jobInfo : companyJobInfos2) {
            if (jobInfo.getJobName() != null) {
                jobName.append(jobInfo.getJobName());
            }
            if (jobInfo.getWelfare() != null) {
                welfare.append(jobInfo.getWelfare());
            }
            if (jobInfo.getPosDes() != null) {
                posDes.append(jobInfo.getPosDes());
            }
            if (!comintro.toString().contains(jobInfo.getComintro())) {
                comintro.append(jobInfo.getComintro());
            }
        }
        for (CompanyJobInfoZl jobInfo : companyJobInfos3) {
            if (jobInfo.getJobName() != null) {
                jobName.append(jobInfo.getJobName());
            }
            if (jobInfo.getWelfare() != null) {
                welfare.append(jobInfo.getWelfare());
            }
            if (jobInfo.getPosDes() != null) {
                posDes.append(jobInfo.getPosDes());
            }
            if (!comintro.toString().contains(jobInfo.getComintro())) {
                comintro.append(jobInfo.getComintro());
            }
        }
        return Arrays.asList(jobName.toString(), welfare.toString(), posDes.toString(), comintro.toString());
    }

}
