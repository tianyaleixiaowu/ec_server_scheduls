package com.mindata.ecserver.main.controller;

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
    public Object queryCodeByVocationName(String vocationName) {
        return esVocationCodeService.queryCodeByVocationName(vocationName);
    }
}
