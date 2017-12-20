package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.main.manager.EsCompanyCoordinateManager;
import com.mindata.ecserver.main.service.CompanyCoordinateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
     * 更新id范围内的数据
     *
     * @param beginId 开始id
     * @param endId   结束id
     * @return 结果
     */
    @PutMapping("/idBetween")
    public Object updateIdBetweenCoordinate(Long beginId, Long endId, Boolean force) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
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
     */
    @PutMapping("/dateBetween")
    public Object updateDateBetweenCoordinate(String begin, String end, Boolean force) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        companyCoordinateService.partInsertDateBetween(begin, end, force);
        return "更新完毕";
    }

    /**
     * 地址去查经纬度
     *
     * @param address 地址
     * @param city    城市
     * @return 结果
     */
    @GetMapping("/address")
    public Object getOutCoordinateByAddress(String address, String city) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        return companyCoordinateService.getOutLocationByAddress(address, city);
    }

    /**
     * 公司去查经纬度
     *
     * @param company 公司
     * @param city    城市
     * @return 结果
     */
    @GetMapping("/company")
    public Object getOutCoordinateByCompany(String company, String city) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        return companyCoordinateService.getOutLocationByCompany(company, city);
    }
}
