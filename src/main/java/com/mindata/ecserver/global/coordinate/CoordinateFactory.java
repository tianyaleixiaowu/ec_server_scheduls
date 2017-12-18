package com.mindata.ecserver.global.coordinate;

import com.mindata.ecserver.global.coordinate.service.ICoordinateService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hanliqiang wrote on 2017/12/14
 */
@Component
public class CoordinateFactory {
    @Value("${classname}")
    private String classnames;

    /**
     * 通过反射获取实例
     *
     * @return 结果
     * @throws ClassNotFoundException 异常
     * @throws IllegalAccessException 异常
     * @throws InstantiationException 异常
     */
    public List<ICoordinateService> getInstances() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ICoordinateService coordinateService;
        String[] names = classnames.split(",");
        List<ICoordinateService> list = new ArrayList<>();
        for (String name : names) {
            coordinateService = (ICoordinateService) Class.forName(name).newInstance();
            list.add(coordinateService);
        }
        return list;
    }
}
