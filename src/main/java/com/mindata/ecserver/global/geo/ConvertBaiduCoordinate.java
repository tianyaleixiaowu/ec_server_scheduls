package com.mindata.ecserver.global.geo;

import org.springframework.stereotype.Service;

/**
 * @author hanliqiang wrote on 2017/11/29
 */
@Service
public class ConvertBaiduCoordinate {

//    @Resource
//    private RetrofitServiceBuilder retrofitServiceBuilder;
//    @Resource
//    private CallManager callManager;
//    @Value("${main.baidu-url}")
//    private String baiduUrl;

    private static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;

//    /**
//     * 将其他坐标转换为百度坐标
//     *
//     * @param coords 源坐标
//     * @return
//     * @throws IOException
//     */
//    public BaiduConvertResponseData convertBaiduCoordinate(String coords) throws IOException {
//        RequestProperty requestProperty = new MapBaiduRequestProperty(baiduUrl);
//        BaiduCoordinateService baiduCoordinateService = retrofitServiceBuilder.getBaiduCoordinateService(requestProperty);
//        BaiduConvertResponseData convertResponseData = (BaiduConvertResponseData) callManager.execute(baiduCoordinateService.getBaiduCoordinate(coords, BAIDU_FROM, BAIDU_TO, OUTPUT_TYPE, BAIDU_MAP_AK));
//        if (ObjectUtil.isNull(convertResponseData)) {
//            return null;
//        }
//        return convertResponseData;
//    }

    /**
     * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换算法 将 GCJ-02 坐标转换成 BD-09 坐标
     */
    public String convertBaiduCoordinate(String location) {
        String[] coordinates = location.split(",");
        double x = Double.valueOf(coordinates[0]), y = Double.valueOf(coordinates[1]);
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
        double tempLon = z * Math.cos(theta) + 0.0065;
        double tempLat = z * Math.sin(theta) + 0.006;
        return retainSix(tempLon) + "," + retainSix(tempLat);
    }

    /**
     * 保留小数点后六位
     *
     * @param num 参数
     * @return 结果
     */
    private static double retainSix(double num) {
        String result = String.format("%.6f", num);
        return Double.valueOf(result);
    }
}
