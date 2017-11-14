package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.main.manager.EcCodeAreaManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 根据输入的字符串查询对应的城市
 *
 * @author wuweifeng wrote on 2017/11/13.
 */
@RestController
@RequestMapping("/area")
public class AreaController {
    @Resource
    private EcCodeAreaManager ecCodeAreaManager;

    @RequestMapping("")
    public Object getArea(String area) {
        return ecCodeAreaManager.findAreaCode(area);
    }
}
