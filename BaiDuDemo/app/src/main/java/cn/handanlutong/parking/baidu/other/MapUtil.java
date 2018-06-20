package cn.handanlutong.parking.baidu.other;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.model.LatLng;

/**
 * Created by ww on 2017/4/24.
 * 处理地图的工具类
 */

public class MapUtil {
    //地球半径
    private final static double EARTH_RADIUS = 6378137.0;

    /**
     * 处理缩放 sdk 缩放级别范围： [3.0,19.0]
     */
    public static void perfomZoom(float zoomLevel,BaiduMap mBaiduMap) {
        MapStatusUpdate u = MapStatusUpdateFactory.zoomTo(zoomLevel);
        mBaiduMap.animateMapStatus(u);
    }

    /**
     * 移动地图中心点
     * @param latLng
     */
    public static void moveMapCenterPoint(LatLng latLng, BaiduMap mBaiduMap){
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);
        mBaiduMap.animateMapStatus(u);
    }

    /**
     * 计算地球上两点之间的距离,结果以米为单位
     */

    public static double calculateDistance(double lat_a, double lng_a, double lat_b, double lng_b) {
        double radLat1 = (lat_a * Math.PI / 180.0);
        double radLat2 = (lat_b * Math.PI / 180.0);
        double a = radLat1 - radLat2;
        double b = (lng_a - lng_b) * Math.PI / 180.0;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }
}
