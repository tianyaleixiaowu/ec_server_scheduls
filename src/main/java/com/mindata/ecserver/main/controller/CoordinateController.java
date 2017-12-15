package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.main.manager.EsCompanyCoordinateManager;
import com.mindata.ecserver.main.service.CompanyCoordinateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    @Resource
    private EsCompanyCoordinateManager esCompanyCoordinateManager;

    /**
     * 根据地址获取坐标(可以传任何字符串，但必须包含城市)
     *
     * @param address 地址
     * @return 结果
     * @throws IOException 异常
     */
    @GetMapping("")
    public Object getCoordinate(String address, String city) throws IOException {
        return companyCoordinateService.getOutLocation(address, city);
    }

    /**
     * 更新id范围内的数据
     *
     * @param beginId 开始id
     * @param endId   结束id
     * @return 结果
     * @throws IOException 异常
     */
    @PutMapping("/idBetween")
    public Object updateIdBetweenCoordinate(Long beginId, Long endId, Boolean force) throws IOException {
        companyCoordinateService.partInsertIdBetween(beginId, endId, force);
        return "更新完毕";
    }

    /**
     * 更新elasticsearch库contactId范围内的数据，只更新ES
     *
     * @param beginId
     *         开始id
     * @param endId
     *         结束id
     * @return 结果
     * @throws IOException
     *         异常
     */
    @PutMapping("/es/idBetween")
    public Object updateOnlyEsIdBetweenCoordinate(Long beginId, Long endId, Boolean force) throws IOException {
        esCompanyCoordinateManager.bulkIndexCompany(beginId, endId, null, force);
        return "更新完毕";
    }

    /**
     * 更新一段时间范围内的数据
     *
     * @param begin 开始时间
     * @param end   结束时间
     * @return 结果
     * @throws IOException 异常
     */
    @PutMapping("/dateBetween")
    public Object updateDateBetweenCoordinate(String begin, String end, Boolean force) throws IOException {
        companyCoordinateService.partInsertDateBetween(begin, end, force);
        return "更新完毕";
    }
}
