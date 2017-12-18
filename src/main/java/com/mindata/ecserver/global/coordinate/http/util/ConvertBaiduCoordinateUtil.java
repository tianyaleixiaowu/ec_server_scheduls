package com.mindata.ecserver.global.coordinate.http.util;

/**
 * @author hanliqiang wrote on 2017/11/29
 */
public class ConvertBaiduCoordinateUtil {

    /**
     * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换算法 将 GCJ-02 坐标转换成 BD-09 坐标
     */
    public static String convertBaiduCoordinate(String location) {
        String[] coordinates = location.split(",");
        double x = Double.valueOf(coordinates[0]), y = Double.valueOf(coordinates[1]);
        double pi = 3.14159265358979324 * 3000.0 / 180.0;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * pi);
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
