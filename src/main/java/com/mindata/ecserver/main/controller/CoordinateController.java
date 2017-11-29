package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.main.service.CompanyCoordinateService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author hanliqiang wrote on 2017/11/27
 */
@Controller
public class CoordinateController {
    @Resource
    private CompanyCoordinateService coordinateService;

    @GetMapping("/baidu")
    public String baidu() throws IOException, InterruptedException {
        coordinateService.saveCompanyCoordinate();
        return "baidu";
    }
}
