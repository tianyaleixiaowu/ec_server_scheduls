package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.thirdly.CompanyJobInfo;
import com.mindata.ecserver.main.model.thirdly.CompanyJobInfo51;
import com.mindata.ecserver.main.model.thirdly.CompanyJobInfoGanji;
import com.mindata.ecserver.main.model.thirdly.CompanyJobInfoZl;
import com.mindata.ecserver.main.repository.thirdly.CompanyJobInfo51Repository;
import com.mindata.ecserver.main.repository.thirdly.CompanyJobInfoGanjiRepository;
import com.mindata.ecserver.main.repository.thirdly.CompanyJobInfoRepository;
import com.mindata.ecserver.main.repository.thirdly.CompanyJobInfoZlRepository;
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
     * @param companyId
     * compId
     * @return
     * 所有的工作名称
     */
    public List<String> getExtraInfo(Long companyId) {
        String jobName = "";
        String welfare = "";
        String posDes = "";
        String comintro = "";

        List<CompanyJobInfo> companyJobInfos = companyJobInfoRepository.findByCompId(companyId);
        List<CompanyJobInfo51> companyJobInfos1 = companyJobInfo51Repository.findByCompId(companyId);
        List<CompanyJobInfoGanji> companyJobInfos2 = companyJobInfoGanjiRepository.findByCompId(companyId);
        List<CompanyJobInfoZl> companyJobInfos3 = companyJobInfoZlRepository.findByCompId(companyId);

        for (CompanyJobInfo jobInfo : companyJobInfos) {
            if (jobInfo.getJobName() != null) {
                jobName += jobInfo.getJobName();
            }
            if (jobInfo.getWelfare() != null) {
                welfare += jobInfo.getWelfare();
            }
            if (jobInfo.getPosDes() != null) {
                posDes += jobInfo.getPosDes();
            }
            if (jobInfo.getComintro() != null) {
                comintro += jobInfo.getComintro();
            }
        }
        for (CompanyJobInfo51 jobInfo : companyJobInfos1) {
            if (jobInfo.getJobName() != null) {
                jobName += jobInfo.getJobName();
            }
            if (jobInfo.getWelfare() != null) {
                welfare += jobInfo.getWelfare();
            }
            if (jobInfo.getPosDes() != null) {
                posDes += jobInfo.getPosDes();
            }
            if (jobInfo.getComintro() != null) {
                comintro += jobInfo.getComintro();
            }
        }
        for (CompanyJobInfoGanji jobInfo : companyJobInfos2) {
            if (jobInfo.getJobName() != null) {
                jobName += jobInfo.getJobName();
            }
            if (jobInfo.getWelfare() != null) {
                welfare += jobInfo.getWelfare();
            }
            if (jobInfo.getPosDes() != null) {
                posDes += jobInfo.getPosDes();
            }
            if (jobInfo.getComintro() != null) {
                comintro += jobInfo.getComintro();
            }
        }
        for (CompanyJobInfoZl jobInfo : companyJobInfos3) {
            if (jobInfo.getJobName() != null) {
                jobName += jobInfo.getJobName();
            }
            if (jobInfo.getWelfare() != null) {
                welfare += jobInfo.getWelfare();
            }
            if (jobInfo.getPosDes() != null) {
                posDes += jobInfo.getPosDes();
            }
            if (jobInfo.getComintro() != null) {
                comintro += jobInfo.getComintro();
            }
        }
        return Arrays.asList(jobName, welfare, posDes, comintro);
    }

}
