package com.mindata.ecserver.main.manager;

import com.mindata.ecserver.main.model.primary.CompanyQichacha;
import com.mindata.ecserver.main.model.thirdly.*;
import com.mindata.ecserver.main.repository.primary.CompanyQichachaRepository;
import com.mindata.ecserver.main.repository.thirdly.*;
import com.xiaoleilu.hutool.util.StrUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

import static com.mindata.ecserver.global.Constant.DOUHAO;

/**
 * 获取行业名称  更新到ES
 *
 * @author hanliqiang wrote on 2017/11/14
 */
@Service
public class CompanyIndustryInfoManager {
    @Resource
    private CompanyIndustryInfo51Repository companyIndustryInfo51Repository;
    @Resource
    private CompanyIndustryInfo88Repository companyIndustryInfo88Repository;
    @Resource
    private CompanyIndustryInfo3158Repository companyIndustryInfo3158Repository;
    @Resource
    private CompanyIndustryInfoGanjiRepository companyIndustryInfoGanjiRepository;
    @Resource
    private CompanyIndustryInfoRepository companyIndustryInfoRepository;
    @Resource
    private CompanyIndustryInfoZlRepository companyIndustryInfoZlRepository;
    @Resource
    private CompanyQichachaRepository companyQichachaRepository;

    /**
     * 不包含3158表的行业名称
     */
    public List<String> getIndustryInfoForDb(Long compId,String companyName) {
        StringBuilder industry = new StringBuilder();
        StringBuilder comintro = new StringBuilder();

        List<CompanyIndustryInfo51> industryInfo51s = companyIndustryInfo51Repository.findByCompId(compId);
        List<CompanyIndustryInfo88> industryInfo88s = companyIndustryInfo88Repository.findByCompId(compId);
        List<CompanyIndustryInfoGanji> industryInfoGanjis = companyIndustryInfoGanjiRepository.findByCompId(compId);
        List<CompanyIndustryInfo> industryInfos = companyIndustryInfoRepository.findByCompId(compId);
        List<CompanyIndustryInfoZl> industryInfoZls = companyIndustryInfoZlRepository.findByCompId(compId);
        List<CompanyQichacha> companyQichachas = companyQichachaRepository.findByCompanyName(companyName);
        for (CompanyIndustryInfo51 companyIndustryInfo51 : industryInfo51s) {
            if (StrUtil.isNotEmpty(companyIndustryInfo51.getIndustry())) {
                industry.append(companyIndustryInfo51.getIndustry()).append(DOUHAO);
            }
            if (StrUtil.isNotEmpty(companyIndustryInfo51.getComintro())) {
                comintro.append(companyIndustryInfo51.getComintro()).append(DOUHAO);
            }
        }
        for (CompanyIndustryInfo88 companyIndustryInfo88 : industryInfo88s) {
            if (StrUtil.isNotEmpty(companyIndustryInfo88.getIndustry()) && !industry.toString().contains(companyIndustryInfo88.getIndustry()) ) {
                industry.append(companyIndustryInfo88.getIndustry()).append(DOUHAO);
            }
            if (StrUtil.isNotEmpty(companyIndustryInfo88.getComintro()) && !comintro.toString().contains(companyIndustryInfo88.getComintro())) {
                comintro.append(companyIndustryInfo88.getComintro()).append(DOUHAO);
            }
        }
        for (CompanyIndustryInfoGanji companyIndustryInfoGanji : industryInfoGanjis) {
            if (StrUtil.isNotEmpty(companyIndustryInfoGanji.getIndustry()) && !industry.toString().contains(companyIndustryInfoGanji.getIndustry()) ) {
                industry.append(companyIndustryInfoGanji.getIndustry()).append(DOUHAO);
            }
            if (StrUtil.isNotEmpty(companyIndustryInfoGanji.getComintro()) && !comintro.toString().contains(companyIndustryInfoGanji.getComintro())) {
                comintro.append(companyIndustryInfoGanji.getComintro()).append(DOUHAO);
            }
        }
        for (CompanyIndustryInfo companyIndustryInfo : industryInfos) {
            if (StrUtil.isNotEmpty(companyIndustryInfo.getIndustry()) && !industry.toString().contains(companyIndustryInfo.getIndustry()) ) {
                industry.append(companyIndustryInfo.getIndustry()).append(DOUHAO);
            }
            if (StrUtil.isNotEmpty(companyIndustryInfo.getComintro()) && !comintro.toString().contains(companyIndustryInfo.getComintro())) {
                comintro.append(companyIndustryInfo.getComintro()).append(DOUHAO);
            }
        }
        for (CompanyIndustryInfoZl companyIndustryInfoZl : industryInfoZls) {
            if (StrUtil.isNotEmpty(companyIndustryInfoZl.getIndustry()) && !industry.toString().contains(companyIndustryInfoZl.getIndustry()) ) {
                industry.append(companyIndustryInfoZl.getIndustry()).append(DOUHAO);
            }
            if (StrUtil.isNotEmpty(companyIndustryInfoZl.getComintro()) && !comintro.toString().contains
                    (companyIndustryInfoZl.getComintro())) {
                comintro.append(companyIndustryInfoZl.getComintro()).append(DOUHAO);
            }
        }
        for (CompanyQichacha companyQichcacha : companyQichachas) {
            if (StrUtil.isNotEmpty(companyQichcacha.getCompanyDesc()) && !comintro.toString().contains(companyQichcacha.getCompanyDesc()) ) {
                comintro.append(companyQichcacha.getCompanyDesc()).append(DOUHAO);
            }
        }
        return Arrays.asList(industry.toString(), comintro.toString());
    }

    /**
     * 单独查3158的行业名称
     */
    private String get3158IndustryInfo(Long compId) {
        StringBuilder industry = new StringBuilder();
        List<CompanyIndustryInfo3158> industryInfo3158s = companyIndustryInfo3158Repository.findByCompId(compId);
        for (CompanyIndustryInfo3158 companyIndustryInfo3158 : industryInfo3158s) {
            if (StrUtil.isNotEmpty(companyIndustryInfo3158.getIndustry())) {
                industry.append(companyIndustryInfo3158.getIndustry()).append(DOUHAO);
            }
        }
        return industry.toString();
    }

    /**
     * 查所有表的行业和公司简介
     */
    public List<String> getIndustryAndComintroInfoForEs(Long compId,String companyName) {
        StringBuilder industry = new StringBuilder();
        List<String> list = getIndustryInfoForDb(compId,companyName);
        industry.append(list.get(0));
        String vocationName = get3158IndustryInfo(compId);
        if (StrUtil.isNotEmpty(vocationName) && !list.contains(vocationName) ) {
            industry.append(list.get(0));
        }
        return Arrays.asList(industry.toString(), list.get(1));
    }

}
