package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.global.bean.BaseData;
import com.mindata.ecserver.global.bean.ResultGenerator;
import com.mindata.ecserver.main.manager.EsVocationCodeManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/vocationCode")
public class VocationCodeController {
    @Resource
    private EsVocationCodeManager esVocationCodeManager;

    @GetMapping({""})
    public BaseData queryContactData(String vocationName) {
        return ResultGenerator.genSuccessResult(esVocationCodeManager.findByVocationName(vocationName));
    }
}
