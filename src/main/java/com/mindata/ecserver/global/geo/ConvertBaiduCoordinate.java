package com.mindata.ecserver.global.geo;

import com.mindata.ecserver.global.geo.service.BaiduCoordinateService;
import com.mindata.ecserver.global.http.MapBaiduRequestProperty;
import com.mindata.ecserver.global.http.RequestProperty;
import com.mindata.ecserver.global.http.RetrofitServiceBuilder;
import com.mindata.ecserver.global.http.response.BaiduConvertResponseData;
import com.mindata.ecserver.retrofit.CallManager;
import com.xiaoleilu.hutool.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

import static com.mindata.ecserver.global.Constant.*;

/**
 * @author hanliqiang wrote on 2017/11/29
 */
@Service
public class ConvertBaiduCoordinate {
    @Resource
    private RetrofitServiceBuilder retrofitServiceBuilder;
    @Resource
    private CallManager callManager;
    @Value("${main.baidu-url}")
    private String baiduUrl;

    /**
     * 将其他坐标转换为百度坐标
     *
     * @param coords 源坐标
     * @return
     * @throws IOException
     */
    public BaiduConvertResponseData convertBaiduCoordinate(String coords) throws IOException {
        RequestProperty requestProperty = new MapBaiduRequestProperty(baiduUrl);
        BaiduCoordinateService baiduCoordinateService = retrofitServiceBuilder.getBaiduCoordinateService(requestProperty);
        BaiduConvertResponseData convertResponseData = (BaiduConvertResponseData) callManager.execute(baiduCoordinateService.getBaiduCoordinate(coords, BAIDU_FROM, BAIDU_TO, OUTPUT_TYPE, BAIDU_MAP_AK));
        if (ObjectUtil.isNull(convertResponseData)) {
            return null;
        }
        return convertResponseData;
    }
}
