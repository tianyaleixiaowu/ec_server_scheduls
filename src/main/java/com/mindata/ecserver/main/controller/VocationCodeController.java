package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.global.bean.BaseData;
import com.mindata.ecserver.global.bean.ResultGenerator;
import com.mindata.ecserver.main.service.EsVocationCodeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author HanLiqiang wrote on 2017/11/14.
 */
@RestController
@RequestMapping("/vocationCode")
public class VocationCodeController {
    @Resource
    private EsVocationCodeService esVocationCodeService;

    @GetMapping("")
    public BaseData queryCodeByVocationName(String vocationName) {
        return ResultGenerator.genSuccessResult(esVocationCodeService.queryCodeByVocationName(vocationName));
    }
}
