package cn.handanlutong.parking.activity.baidu;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.navi.NaviParaOption;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiBoundSearchOption;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BaiduNaviManagerFactory;
import com.baidu.navisdk.adapter.IBNRoutePlanManager;
import com.baidu.navisdk.adapter.IBNTTSManager;
import com.baidu.navisdk.adapter.IBaiduNaviManager;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.handanlutong.parking.BaseApplication;
import cn.handanlutong.parking.DemoGuideActivity;
import cn.handanlutong.parking.R;
import cn.handanlutong.parking.activity.BaseActivity;
import cn.handanlutong.parking.baidu.listener.MyOrientationListener;
import cn.handanlutong.parking.baidu.other.PoiOverlay;
import cn.handanlutong.parking.baidu.other.StripListView;
import cn.handanlutong.parking.baidu.service.LocationService;
import cn.handanlutong.parking.bean.GarageBean;
import cn.handanlutong.parking.bean.SFguize;
import cn.handanlutong.parking.http.AnsynHttpRequest;
import cn.handanlutong.parking.http.HttpMethod;
import cn.handanlutong.parking.http.UrlConfig;
import cn.handanlutong.parking.utils.JieKouDiaoYongUtils;
import cn.handanlutong.parking.utils.LogUtils;
import cn.handanlutong.parking.utils.NavigateUtils;
import cn.handanlutong.parking.utils.NetWorkUtil;
import cn.handanlutong.parking.utils.SharedPreferenceUtil;
import cn.handanlutong.parking.utils.StringUtil;
import cn.handanlutong.parking.utils.ToastUtil;
import cn.handanlutong.parking.utils.UpdateManager;

import static cn.handanlutong.parking.BaseApplication.TTSAppID;

public class IndoorLocationActivity extends BaseActivity implements OnClickListener {
    private final int SDK_PERMISSION_REQUEST = 127;
    private SharedPreferenceUtil spUtil;
    private LatLng mlatLng;
    private LatLng mlatLng_my;
    private LocationService locationService;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private Marker marker = null;
    private StripListView stripListView;
    // UI相关  //定位监听器（实现定位请求回调接口）,当客户端请求定位或者开始定位的时候会触发
    private MyOrientationListener myOrientationListener;
    boolean isFirstLoc = true; // 是否首次定位
    private String permissionInfo;
    LinearLayout ll_serch;
    private ImageView renovate, llocation;
    private LinearLayout ll_no_place;
    private List<GarageBean.ResultBean> garage;
    private View marker_park_low;
    private TextView tv_park_low, tv_search, tv_ListParkPlace, tv_no_place;
    protected float mCurrentX = 0;
    private LinearLayout rl_newpopupwindow;
    private boolean FINE_LOCATION = false;
    private boolean COARSE_LOCATION = false;
    /**
     * 兴趣点(poi)搜索
     */
    private static PoiSearch mPoiSearch;
    private View mainview;
    private static String mCity;
    LatLng cenpt;
    private TextView tv_price, tv_leix, tv_distance;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//设置隐藏标题栏
        spUtil = SharedPreferenceUtil.init(this, SharedPreferenceUtil.LOGIN_INFO, Activity.MODE_PRIVATE);
        getPersimmions(this);
        RelativeLayout layout = new RelativeLayout(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mainview = inflater.inflate(R.layout.activity_locationn, null);
        layout.addView(mainview);
        RelativeLayout iv_back = (RelativeLayout) mainview.findViewById(R.id.iv_back);
        ll_no_place = (LinearLayout) mainview.findViewById(R.id.ll_no_place);
        tv_no_place = (TextView) mainview.findViewById(R.id.no_place);
        tv_ListParkPlace = (TextView) mainview.findViewById(R.id.tv_ditu);
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        // 地图初始化
        mMapView = (MapView) mainview.findViewById(R.id.bmapView);
        mMapView.showZoomControls(false);
        mMapView.showScaleControl(false);
        mBaiduMap = mMapView.getMap();
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                mlatLng = mapStatus.target;
                int[] location = new int[2];
                mMapView.getLocationOnScreen(location);
                LogUtils.d("纬度:" + mlatLng.latitude + "------经度：" + mlatLng.longitude);
                postData(mlatLng);
            }
        });


        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(mlatLng, Float.parseFloat("14"));
        mBaiduMap.setMapStatus(u);
        myOrientationListener = new MyOrientationListener(this);
        myOrientationListener.setOnOrientationListener(new MyOrientationListener.OnOrientationListener() {

            @Override
            public void onOrientationChanged(float x) {
                mCurrentX = x;
            }
        });
        stripListView = new StripListView(this);
        layout.addView(stripListView);
        setContentView(layout);
        tv_search = (TextView) mainview.findViewById(R.id.main_search);
        ll_serch = (LinearLayout) mainview.findViewById(R.id.ll_serch);
        renovate = (ImageView) mainview.findViewById(R.id.iv_renovate);
        llocation = (ImageView) mainview.findViewById(R.id.iv_llocation);
        rl_newpopupwindow = (LinearLayout) mainview.findViewById(R.id.rl_newpopupwindow);
        marker_park_low = View.inflate(this, R.layout.marker_park_low, null);
        tv_park_low = (TextView) marker_park_low.findViewById(R.id.tv_park_low);
        ll_serch.setOnClickListener(this);
        renovate.setOnClickListener(this);
        llocation.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        tv_ListParkPlace.setOnClickListener(this);
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult result) {
                if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
                    Toast.makeText(IndoorLocationActivity.this, "未找到结果", Toast.LENGTH_LONG).show();
                    return;
                } else if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                    clearOverlay();//清空覆盖物
                    MyPoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
                    overlay.setData(result);
                    mBaiduMap.setOnMarkerClickListener(overlay);
                    result.getTotalPageNum();// 获取总分页数
                    result.getTotalPoiNum();
                    result.getCurrentPageCapacity();
                    result.getAllAddr();
                    Log.d("syso", "-----" + result.getTotalPageNum() + "-----" + result.getTotalPoiNum() + "-----" + "++++10+++" + result.getCurrentPageCapacity());
                    List<PoiInfo> infos = result.getAllPoi();
                    for (int i = 0; i < infos.size(); i++) {
                        if (i == 0) {
                            postData(infos.get(i).location);
                            MapStatus mapStatus = new MapStatus.Builder().target(infos.get(i).location).zoom(18).build();
                            MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
                            mBaiduMap.setMapStatus(mapStatusUpdate);
                        }
                    }
                    return;
                } else
                    // AMBIGUOUS_KEYWORD表示 检索词有岐义
                    if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {
                        // 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
                        String strInfo = "在";
                        for (CityInfo cityInfo : result.getSuggestCityList()) {
                            strInfo += cityInfo.city;
                            strInfo += ",";
                        }
                        strInfo += "找到结果";
                        Toast.makeText(IndoorLocationActivity.this, strInfo, Toast.LENGTH_LONG).show();
                    }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult result) {
                if (result.error != SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(IndoorLocationActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("syso", "" + result.getImageNum() + "------位置：" + result.getLocation() + "------选中num" +
                            result.getCheckinNum() + "-----num:" + result.getGrouponNum() + "------" + result.getFavoriteNum()
                            + "-----" + result.getTag() + "-----" + result.getType() + "-----" + result.getCommentNum());
                    cenpt = result.getLocation();//设置中心点坐标
                    MapStatus mapStatus = new MapStatus.Builder().target(cenpt).zoom(18).build();
                    MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
                    mBaiduMap.setMapStatus(mapStatusUpdate);
                    //检索成功
                    String name = result.getName();
                    ToastUtil.makeShortText(IndoorLocationActivity.this, name + "：" + result.getAddress());
                }
            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        });
        if (cenpt != null) {
            MapStatus mapStatus = new MapStatus.Builder().target(cenpt).zoom(18).build();
            MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
            mBaiduMap.setMapStatus(mapStatusUpdate);
        }
        //覆盖物点击监听器
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(final Marker result) {
                final List<GarageBean.ResultBean> list_gar = (List<GarageBean.ResultBean>) result.getExtraInfo().get("garage");
                try {
                    rl_newpopupwindow.setVisibility(View.VISIBLE);
                    List<GarageBean.ResultBean> list_garage = (List<GarageBean.ResultBean>) result.getExtraInfo().get("garage");
                    TextView tv_ParkingName = (TextView) rl_newpopupwindow.findViewById(R.id.tv_ParkingName);
                    TextView tv1 = (TextView) rl_newpopupwindow.findViewById(R.id.tv1);
                    tv_price = (TextView) rl_newpopupwindow.findViewById(R.id.tv_price);
                    tv_leix = (TextView) rl_newpopupwindow.findViewById(R.id.tv_leix);
                    tv_distance = (TextView) rl_newpopupwindow.findViewById(R.id.tv_distance);
                    tv_ParkingName.setText(list_garage.get(result.getZIndex()).getCcmc());
                    tv1.setText(list_garage.get(result.getZIndex()).getKbwsl() + "个");
                    final String newDistance = StringUtil.convertMtoKM((int) DistanceUtil.getDistance(
                            new LatLng(Double.valueOf(list_gar.get(result.getZIndex()).getCczbY()), Double.valueOf(list_gar.get(result.getZIndex()).getCczbX())), mlatLng_my));
                    tv_distance.setText(newDistance);

                    if (NetWorkUtil.isNetworkConnected(IndoorLocationActivity.this)) {
                        JSONObject jsobj1 = new JSONObject();
                        try {
                            JSONObject jsobj2 = new JSONObject();
                            jsobj2.put("id", garage.get(result.getZIndex()).getDjlx());

                            jsobj1.put("parameter", jsobj2);
                            jsobj1.put("appType", UrlConfig.android_type);
                            jsobj1.put("version", JieKouDiaoYongUtils.getVerName(IndoorLocationActivity.this));
                            jsobj1.put("authKey", spUtil.getString("authkey"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        HttpMethod.CCsf_GUIze(httpUtils, jsobj1, IndoorLocationActivity.this, UrlConfig.CCsf_CODE);
                    } else {
                        ToastUtil.makeShortText(IndoorLocationActivity.this, "请检查网络！");
                    }
                    Button btn_go = (Button) rl_newpopupwindow.findViewById(R.id.btn_go);
                    btn_go.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mBaiduMap.hideInfoWindow();
                            rl_newpopupwindow.setVisibility(View.GONE);
                            //跳到百度地图导航
                            NaviParaOption option = new NaviParaOption().startName("我的位置").startPoint(mlatLng_my).endName("我要去的车场").endPoint(result.getPosition());
//                            BaiduMapNavigation.openBaiduMapNavi(option, IndoorLocationActivity.this);

                            //百度经纬度坐标
                            NavigateUtils.getInstance().openNavigate(option);

                        }
                    });

                    LinearLayout ll_ParkingName = (LinearLayout) rl_newpopupwindow.findViewById(R.id.ll_ParkingName);
                    ll_ParkingName.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(IndoorLocationActivity.this, CarPlaceItemActivity.class);
                            intent.putExtra("garage", (Serializable) list_gar);
                            intent.putExtra("zindex", result.getZIndex());
                            intent.putExtra("cctp", list_gar.get(result.getZIndex()).getCctp());
                            intent.putExtra("distance", newDistance);
                            intent.putExtra("latlng_my", mlatLng_my);
                            startActivity(intent);
                        }
                    });

                } catch (Exception e) {
                    rl_newpopupwindow.setVisibility(View.GONE);
                    e.printStackTrace();
                }
                return true;
            }
        });
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mBaiduMap.hideInfoWindow();
                rl_newpopupwindow.setVisibility(View.GONE);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });

        //初始化百度导航
        NavigateUtils.getInstance().initNavigate(this);

    }


    @Override
    public void onStart() {
        super.onStart();
        // 开启图层定位
        mBaiduMap.setMyLocationEnabled(true);
        // 开启方向传感器
        myOrientationListener.start();
//
//        locationService = ((BaseApplication) getApplication()).locationService;
//        locationService.registerListener(mListener);
//        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
//        locationService.start();
    }


    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            mCity = location.getCity();
            mlatLng_my = new LatLng(location.getLatitude(), location.getLongitude());
            LogUtils.d("当前位置经纬度：" + location.getLatitude() + "," + location.getLongitude());
            MyLocationData myLocationData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())   //设置定位数据的精度信息，单位米
                    .direction(mCurrentX)                     //设定定位数据的方向信息 传感器0-360度
                    .latitude(location.getLatitude()) //设定定位数据的纬度
                    .longitude(location.getLongitude())//设定定位数据的经度
                    .build();   //构建生生定位数据对象
            //设置定位数据, 只有先允许定位图层后设置数据才会生效,setMyLocationEnabled(boolean)
            mBaiduMap.setMyLocationData(myLocationData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }

            postData(mlatLng_my);
        }
    };

    /**
     * 自定义overlay
     *
     * @author dell
     */
    private class MyPoiOverlay extends PoiOverlay {

        public MyPoiOverlay(BaiduMap map) {
            super(map);
        }

        @Override
        public boolean onPoiClick(int index) {
            super.onPoiClick(index);
            PoiResult result = this.getPoiResult();
            List<PoiInfo> infos = result.getAllPoi();
            PoiInfo info = infos.get(index);
            // 判断poi点是否有美食类详情页面，这里也可以判断其它不是餐厅页面需要自己去查找方法api
            mPoiSearch.searchPoiDetail(new PoiDetailSearchOption().poiUid(info.uid));
            //遍历所有POI，找到类型为公交线路的POI
            for (PoiInfo poi : result.getAllPoi()) {
                if (poi.type == PoiInfo.POITYPE.BUS_LINE || poi.type == PoiInfo.POITYPE.SUBWAY_LINE) {
                    //说明该条POI为公交信息，获取该条POI的UID
                    String busLineId = poi.uid;
                    //如下代码为发起检索代码，定义监听者和设置监听器的方法与POI中的类似
//                    mPoiSearch.searchBusLine((new BusLineSearchOption().city(mCity).uid(busLineId)));
                    break;
                }
            }
            PoiBoundSearchOption boundSearchOption = new PoiBoundSearchOption();
            LatLng southwest = new LatLng(mlatLng.latitude - 80.01, mlatLng.longitude - 160.012);// 西南
            LatLng northeast = new LatLng(mlatLng.latitude + 80.01, mlatLng.longitude + 160.012);// 东北
            LatLngBounds bounds = new LatLngBounds.Builder().include(southwest)
                    .include(northeast).build();// 得到一个地理范围对象
            boundSearchOption.bound(bounds);// 设置poi检索范围
            boundSearchOption.keyword("");// 检索关键字
            boundSearchOption.pageNum(index);
            return true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                mBaiduMap.hideInfoWindow();
                rl_newpopupwindow.setVisibility(View.GONE);
                finish();
                break;
            case R.id.tv_ditu:
                Intent inten = new Intent(IndoorLocationActivity.this, DiTuActivity.class);
                inten.putExtra("garage", (Serializable) garage);
                inten.putExtra("latlng_my", mlatLng_my);
                inten.putExtra("city", mCity);
                startActivity(inten);
                break;
            case R.id.iv_renovate://刷新
                mBaiduMap.hideInfoWindow();
                rl_newpopupwindow.setVisibility(View.GONE);
                if (FINE_LOCATION && COARSE_LOCATION) {
                    if (mlatLng != null) {
                        postData(mlatLng);
                    } else {
                        postData(mlatLng_my);
                    }
                } else {
                    ToastUtil.makeShortText(this, "请检查您的定位服务是否开启");
                }
                break;
            case R.id.ll_serch:
                mBaiduMap.hideInfoWindow();
                rl_newpopupwindow.setVisibility(View.GONE);
                Intent intent = new Intent(this, SerchActivity.class);
                intent.putExtra("city", mCity);
                intent.putExtra("latlng", mlatLng);
                startActivity(intent);
                break;
            case R.id.iv_llocation://定位
                isFirstLoc = true;
                if (FINE_LOCATION && COARSE_LOCATION) {
                    locationService.start();

                } else {
                    ToastUtil.makeShortText(this, "请检查您的定位服务是否开启");
                }
                break;
        }
    }

    private void postData(final LatLng location) {
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                JSONObject jsobj2 = new JSONObject();
                jsobj2.put("jd", location.longitude);
                jsobj2.put("wd", location.latitude);

                jsobj1.put("parameter", jsobj2);
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.BaiDuFindPlace(httpUtils, jsobj1, this, UrlConfig.markUrl_CODE, location);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    @Override
    public void onSuccessHttp(String responseInfo, int resultCode) {
        super.onSuccessHttp(responseInfo, resultCode);
        switch (resultCode) {
            case UrlConfig.markUrl_CODE:
                LogUtils.d("获取车场成功并关闭定位:" + responseInfo);
                locationService.stop();
                try {
                    JSONObject object = new JSONObject(responseInfo);
                    int code = object.optInt("code");
                    String result = object.optString("result");
                    if (code == 0) {
                        garage = AnsynHttpRequest.parser.fromJson(object.toString(), GarageBean.class).getResult();
                        if (garage.size() == 0) {
                            ll_no_place.setVisibility(View.VISIBLE);
                            rl_newpopupwindow.setVisibility(View.GONE);
                            tv_no_place.setText("该区域暂时没有认证停车场");
                        } else {
                            ll_no_place.setVisibility(View.GONE);
                            resetOverlay(garage);
                        }
                    } else if (code == 1001) { //版本更新 弹框
                        JSONObject obj1 = object.optJSONObject("result");
                        int newversionNo = Integer.parseInt(obj1.optString("versionNo"));
                        if (newversionNo > JieKouDiaoYongUtils.getVersionCode(this)) {
                            UpdateManager mUpdateManager = new UpdateManager(this);
                            mUpdateManager.showNoticeDialog(obj1.optString("versionPath"), newversionNo, obj1.optString("versionDescription"));
                        }
                    } else if (code == 1002) { //退出登录 弹框
                        JieKouDiaoYongUtils.loginTanKuan(this);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case UrlConfig.CCsf_CODE:
                LogUtils.d("收费标准：" + responseInfo);
                try {
                    JSONObject object2 = new JSONObject(responseInfo);
                    int code2 = object2.optInt("code");
                    String result2 = object2.optString("result");
                    if (code2 == 0) {
                        SFguize guize = AnsynHttpRequest.parser.fromJson(result2, SFguize.class);
                        tv_price.setText(StringUtil.convertFentoYuanWithout(guize.getSfje()) + "元");
                        tv_leix.setText(guize.getSfmc());
                    } else if (code2 == 1001) { //版本更新 弹框
                        JSONObject obj1 = object2.optJSONObject("result");
                        int newversionNo = Integer.parseInt(obj1.optString("versionNo"));
                        if (newversionNo > JieKouDiaoYongUtils.getVersionCode(this)) {
                            UpdateManager mUpdateManager = new UpdateManager(this);
                            mUpdateManager.showNoticeDialog(obj1.optString("versionPath"), newversionNo, obj1.optString("versionDescription"));
                        }
                    } else if (code2 == 1002) { //退出登录 弹框
                        JieKouDiaoYongUtils.loginTanKuan(this);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(23)
    public void getPersimmions(Activity activity) {
        LogUtils.d("65156565656156566");
        LogUtils.d("Build.VERSION.SDK_INT is:" + Build.VERSION.SDK_INT);
        LogUtils.d("Build.VERSION_CODES.M is:" + Build.VERSION_CODES.M);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请  定位精确位置
             */
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            } else {
                FINE_LOCATION = true;
            }
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            } else {
                COARSE_LOCATION = true;
            }

            /*
             * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
			 */
            // 读写权限
            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE, activity)) {
                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
            }
            // 读取电话状态权限
            if (addPermission(permissions, Manifest.permission.READ_PHONE_STATE, activity)) {
                permissionInfo += "Manifest.permission.READ_PHONE_STATE Deny \n";
            }
            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        } else {
            COARSE_LOCATION = true;
            FINE_LOCATION = true;
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission, Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            } else {
                permissionsList.add(permission);
                return false;
            }

        } else {
            return true;
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // TODO Auto-generated method stub
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case SDK_PERMISSION_REQUEST:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    locationService.start();
                    FINE_LOCATION = true;
                    COARSE_LOCATION = true;
                } else {
                    ToastUtil.makeShortText(this, "请检查您的定位服务是否开启");
                }
                break;
/*            case authBaseRequestCode:
                for (int ret : grantResults) {
                    if (ret == 0) {
                        continue;
                    } else {
                        Toast.makeText(IndoorLocationActivity.this, "缺少导航基本的权限!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                initNavi();
                break;*/
        }
    }


    //初始化添加覆盖物mark
    private void initOverlay(final List<GarageBean.ResultBean> garage) {
        for (int i = 0; i < garage.size(); i++) {
            tv_park_low.setText(garage.get(i).getKbwsl() + "");
            marker_park_low.invalidate();
            double lat = Double.valueOf(garage.get(i).getCczbY());
            double lng = Double.valueOf(garage.get(i).getCczbX());
            LatLng point = new LatLng(lat, lng);
            Bitmap bitmap = StringUtil.convertViewToBitmap(marker_park_low);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(point)//设置位置
                    .icon(BitmapDescriptorFactory.fromBitmap(bitmap))//设置覆盖物小图标
                    .draggable(true)//设置是否可以拖拽，默认为否
                    //.animateType(MarkerOptions.MarkerAnimateType.drop);//从天而降的方式
                    .animateType(MarkerOptions.MarkerAnimateType.grow);//从地生长的方式
            if (garage.get(i).getCczt().equals("2")) {
                View marker_park_high = View.inflate(IndoorLocationActivity.this, R.layout.marker_park_high, null);
                TextView tv_park_high = (TextView) marker_park_high.findViewById(R.id.tv_park_low);
                tv_park_high.setText(garage.get(i).getKbwsl() + "");
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(StringUtil.convertViewToBitmap(marker_park_high)));

            }
            marker = (Marker) mBaiduMap.addOverlay(markerOptions);
            Bundle bundle = new Bundle();
            bundle.putSerializable("garage", (Serializable) garage);
            marker.setExtraInfo(bundle);
            marker.setZIndex(i);
        }
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        locationService = ((BaseApplication) getApplication()).locationService;
        locationService.registerListener(mListener);
        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        LogUtils.d("FINE_LOCATIONn is:" + FINE_LOCATION);
        LogUtils.d("COARSE_LOCATION is:" + COARSE_LOCATION);
        if (FINE_LOCATION && COARSE_LOCATION) {
            locationService.start();
        }
//        if () {
//
//        }

//        OpenPerssion openperssion= new OpenPerssion();
//        proxyPositioning proxypositioning=new proxyPositioning(openperssion);
//        proxypositioning.StartLocation();
        super.onResume();
    }

    //清除覆盖物
    private void clearOverlay() {
        mBaiduMap.clear();
    }

    //重置覆盖物
    private void resetOverlay(List<GarageBean.ResultBean> garage) {
        clearOverlay();
        initOverlay(garage);
    }

    @Override
    protected void onDestroy() {
        //清除覆盖物
        clearOverlay();
        mMapView.onDestroy();
        mMapView = null;
        mPoiSearch.destroy();
        super.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
        // 关闭图层定位
        mBaiduMap.setMyLocationEnabled(false);
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
    }

    /**
     * 兴趣点(poi)搜索
     */
    public static void startSearch(String city, String key) {
        if (!TextUtils.isEmpty(city)) {
            mPoiSearch.searchInCity(new PoiCitySearchOption().city(city).keyword(key).pageNum(0));
        } else {
            mPoiSearch.searchNearby((new PoiNearbySearchOption()).location(new LatLng(40.053484, 116.345373)).keyword(key).radius(10000).pageNum(0));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("syso", "1111------" + resultCode + "------" + resultCode);
        if (resultCode == 1001) {
            tv_search.setText(data.getStringExtra("et_search"));
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getPersimmions(this);
        SharedPreferences sp = getSharedPreferences("search", MODE_PRIVATE);
        String salt = sp.getString("search_address", "");
        if (!salt.equals("")) {
            tv_search.setText("" + salt);
            SharedPreferences sp2 = getSharedPreferences("search", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp2.edit();
            editor.putString("search_address", "");
            editor.commit();
        } else {
            tv_search.setText("查找目的地停车场");
            SharedPreferences sp2 = getSharedPreferences("search", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp2.edit();
            editor.putString("search_address", "");
            editor.commit();
        }
    }

    @Override
    public void onFailureHttp(HttpException error, String msg, int resultCode) {
        super.onFailureHttp(error, msg, resultCode);
        LogUtils.d("访问失败");
    }


    /**
     * 静态代理模式调用定位功能
     */
    interface Subject {
        void StartLocation();
    }

    class OpenPerssion implements Subject {
        @Override
        public void StartLocation() {
            locationService.start();
        }
    }

    class proxyPositioning implements Subject {
        private Subject target;

        public proxyPositioning(Subject target) {
            this.target = target;
        }

        private void beforePositioning() {
            getPersimmions(IndoorLocationActivity.this);
        }

        @Override
        public void StartLocation() {
            beforePositioning();
            target.StartLocation();
        }
    }


}
