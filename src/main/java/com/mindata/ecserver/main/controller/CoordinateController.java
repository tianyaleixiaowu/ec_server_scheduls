package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.main.service.CompanyCoordinateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author hanliqiang wrote on 2017/11/27
 */
@RestController
@RequestMapping("/coordinate")
public class CoordinateController {
    @Resource
    private CompanyCoordinateService companyCoordinateService;

    /**
     * 获取坐标
     *
     * @param address
     * @param companyName
     * @param city
     * @return
     * @throws IOException
     */
    @GetMapping("/getCoordinate")
    public Object getCoordinate(String address, String companyName, String city) throws IOException {
        return companyCoordinateService.findCoordinate(address, companyName, city);
    }

    /**
     * 更新id范围内的数据
     *
     * @param beginId
     * @param endId
     * @return
     * @throws IOException
     */
    @GetMapping("/updateIdBetweenCoordinate")
    public Object updateIdBetweenCoordinate(Long beginId, Long endId) throws IOException {
        companyCoordinateService.partInsertIdBetween(beginId, endId);
        return "更新完毕";
    }

    /**
     * 更新一段时间范围内的数据
     *
     * @param begin
     * @param end
     * @return
     * @throws IOException
     */
    @GetMapping("/updateDateBetweenCoordinate")
    public Object updateDateBetweenCoordinate(String begin, String end) throws IOException {
        companyCoordinateService.partInsertDateBetween(begin, end);
        return "更新完毕";
    }
}
