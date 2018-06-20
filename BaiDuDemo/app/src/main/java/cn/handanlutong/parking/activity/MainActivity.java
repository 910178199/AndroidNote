package cn.handanlutong.parking.activity;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.exception.HttpException;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.handanlutong.parking.BaseApplication;
import cn.handanlutong.parking.R;
import cn.handanlutong.parking.activity.baidu.CarPlaceItemActivity;
import cn.handanlutong.parking.activity.baidu.IndoorLocationActivity;
import cn.handanlutong.parking.adapter.VpAdapter;
import cn.handanlutong.parking.apshare.ShareMenuActivity;
import cn.handanlutong.parking.baidu.service.LocationService;
import cn.handanlutong.parking.bean.FirstEvent;
import cn.handanlutong.parking.bean.FirstPageImageBean;
import cn.handanlutong.parking.bean.GarageBean;
import cn.handanlutong.parking.bean.ParkingRecordHistoryVo;
import cn.handanlutong.parking.bean.TestDemo;
import cn.handanlutong.parking.customview.CircleImageView;
import cn.handanlutong.parking.customview.YWLoadingDialog;
import cn.handanlutong.parking.http.AnsynHttpRequest;
import cn.handanlutong.parking.http.HttpMethod;
import cn.handanlutong.parking.http.UrlConfig;
import cn.handanlutong.parking.utils.JieKouDiaoYongUtils;
import cn.handanlutong.parking.utils.LogUtils;
import cn.handanlutong.parking.utils.NetWorkUtil;
import cn.handanlutong.parking.utils.SharedPreferenceUtil;
import cn.handanlutong.parking.utils.StatusBarCompat;
import cn.handanlutong.parking.utils.StringUtil;
import cn.handanlutong.parking.utils.ToastUtil;
import cn.handanlutong.parking.utils.UpdateManager;
import cn.handanlutong.parking.zoom.DataCleanManager;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;


public class MainActivity extends BaseActivity implements OnClickListener, OnRefreshListener2<ScrollView> {
    private final int SDK_PERMISSION_REQUEST = 127;
    private boolean FINE_LOCATION = false;
    private boolean COARSE_LOCATION = false;
    private String permissionInfo;
    private PullToRefreshScrollView mScrollView;
    private ScrollView scrollView;
    private DrawerLayout drawerLayout;
    private ViewPager mViewPaper;
    private ImageView[] images;
    private ImageView[] dots;
    private SharedPreferenceUtil spUtil;
    private ViewGroup group;
    private CircleImageView iv_circleHead;
    private ParkingRecordHistoryVo billrecordvo = new ParkingRecordHistoryVo();
    private List<FirstPageImageBean.ResultBean> imageList;//记录轮播图片集合
    private BitmapUtils bitmapUtils;
    private long exitTime = 0;
    private UpdateManager mUpdateManager;
    private ViewPagerAdapter adapter;
    private VpAdapter adapter2;
    private ImageView iv_drawerToggle, iv_NoHavetingcheDetail, iv_yueka_sign, iv_main_action, iv_xhd, iv_shuaxin1, iv_main_bg_tu, iv_main_icon, iv_main_xiaoxi, iv_shouye_moren;//侧滑开关
    private RelativeLayout headline, rl_have_tingchejilu, rl_tingcheDetail;
    private LinearLayout ll_MyCar, ll_MyBurse, ll_Bill_Recording, ll_Setting, ll_Invited, ll_UserInfo, ll_MyOrder,
            ll_youhuiq, ll_MyOrder1, ll_MyCar1, ll_MyPurse1, ll_find_Parking1,
            ll_go_zheli, ll_shouye_no_carplace, ll_shouye_have_carplace, ll_NoHaveCar;

    private TextView tv_zje, tv_UserName, tv_dingwei, tv_ccmc, tv_carplace_num, tv_ccjl, tv_kbwsl, tv_tingchejilu_count, tv_mycar_count, tv_car_shiru,
            tv_rwsj, tv_tcsc, tv_tishiyu, tv_IsPay, tv_tcscOrlwsj;
    public YWLoadingDialog mDialog;
    private LatLng mlatLng_my;
    private String versionPath;
    private boolean isSvToBottom = false;
    private static final int THRESHOLD_Y_LIST_VIEW = 20;
    private float mLastX;
    private float mLastY;
    private int currentPosition = 0;
    private LocationService locationService;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            iv_shuaxin1.clearAnimation();//清空动画
        }
    };
    Handler handler2 = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    mViewPaper.setCurrentItem(mViewPaper.getCurrentItem() + 1);
                    handler2.sendEmptyMessageDelayed(0, 3000);
                    break;
                case 1:
                    break;
            }
        }
    };

    @Override
    public void initView() {
        spUtil = SharedPreferenceUtil.init(this, SharedPreferenceUtil.LOGIN_INFO, Activity.MODE_PRIVATE);
        StatusBarCompat.compat(this, getResources().getColor(R.color.colorPrimary));
        setContentView(R.layout.activity_mainining);
        imageList = new ArrayList<>();
        bitmapUtils = new BitmapUtils(this);
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);
        drawerLayout = (DrawerLayout) findViewById(R.id.dl);
        group = (ViewGroup) findViewById(R.id.viewGroup);
        iv_shouye_moren = (ImageView) findViewById(R.id.iv_shouye_moren);
        iv_yueka_sign = (ImageView) findViewById(R.id.iv_yueka_sign);
        iv_drawerToggle = (ImageView) findViewById(R.id.iv_drawerToggle);
        iv_xhd = (ImageView) findViewById(R.id.iv_xhd);
        iv_main_action = (ImageView) findViewById(R.id.iv_main_action);
        iv_shuaxin1 = (ImageView) findViewById(R.id.iv_shuaxin1);
        iv_NoHavetingcheDetail = (ImageView) findViewById(R.id.iv_NoHavetingcheDetail);
        iv_main_bg_tu = (ImageView) findViewById(R.id.iv_main_bg_tu);
        iv_main_icon = (ImageView) findViewById(R.id.iv_main_icon);
        iv_main_xiaoxi = (ImageView) findViewById(R.id.iv_main_xiaoxi);
        ll_MyOrder1 = (LinearLayout) findViewById(R.id.rl_MyOrder);
        ll_MyCar1 = (LinearLayout) findViewById(R.id.rl_MyCar);
        ll_MyPurse1 = (LinearLayout) findViewById(R.id.rl_MyPurse);
        ll_find_Parking1 = (LinearLayout) findViewById(R.id.rl_find_Parking);
        headline = (RelativeLayout) findViewById(R.id.headline);
        ll_MyCar = (LinearLayout) findViewById(R.id.ll_MyCar);
        ll_youhuiq = (LinearLayout) findViewById(R.id.ll_youhuiq);
        ll_MyBurse = (LinearLayout) findViewById(R.id.ll_MyBurse);
        ll_MyOrder = (LinearLayout) findViewById(R.id.ll_MyOrder);
        ll_Bill_Recording = (LinearLayout) findViewById(R.id.ll_Bill_Recording);
        ll_Setting = (LinearLayout) findViewById(R.id.ll_Setting);
        ll_Invited = (LinearLayout) findViewById(R.id.ll_Invited);
        ll_UserInfo = (LinearLayout) findViewById(R.id.ll_UserInfo);
        ll_go_zheli = (LinearLayout) findViewById(R.id.ll_go_zheli);
        ll_shouye_no_carplace = (LinearLayout) findViewById(R.id.ll_shouye_no_carplace);
        ll_shouye_have_carplace = (LinearLayout) findViewById(R.id.ll_shouye_have_carplace);
        ll_NoHaveCar = (LinearLayout) findViewById(R.id.ll_NoHaveCar);
        rl_have_tingchejilu = (RelativeLayout) findViewById(R.id.rl_have_tingchejilu);
        rl_tingcheDetail = (RelativeLayout) findViewById(R.id.rl_tingcheDetail);
        mScrollView = (PullToRefreshScrollView) findViewById(R.id.mScrollView);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        tv_tingchejilu_count = (TextView) findViewById(R.id.tv_tingchejilu_count);
        tv_mycar_count = (TextView) findViewById(R.id.tv_mycar_count);
        tv_UserName = (TextView) findViewById(R.id.tv_UserName);
        tv_dingwei = (TextView) findViewById(R.id.tv_dingwei);
        tv_ccmc = (TextView) findViewById(R.id.tv_ccmc);
        tv_carplace_num = (TextView) findViewById(R.id.tv_carplace_num);
        tv_kbwsl = (TextView) findViewById(R.id.tv_kbwsl);
        tv_ccjl = (TextView) findViewById(R.id.tv_ccjl);
        tv_car_shiru = (TextView) findViewById(R.id.tv_car_shiru);
        tv_rwsj = (TextView) findViewById(R.id.tv_rwsj);
        tv_tcsc = (TextView) findViewById(R.id.tv_tcsc);
        tv_zje = (TextView) findViewById(R.id.tv_zje);
        tv_tishiyu = (TextView) findViewById(R.id.tv_tishiyu);
        tv_IsPay = (TextView) findViewById(R.id.tv_IsPay);
        tv_tcscOrlwsj = (TextView) findViewById(R.id.tv_tcscOrlwsj);
        iv_circleHead = (CircleImageView) findViewById(R.id.iv_circleHead);
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerStateChanged(int arg0) {

            }

            @Override
            public void onDrawerSlide(View arg0, float arg1) {

            }

            @Override
            public void onDrawerOpened(View arg0) {

            }

            @Override
            public void onDrawerClosed(View arg0) {

            }
        });
        mViewPaper = (ViewPager) findViewById(R.id.vp);
        mScrollView = (PullToRefreshScrollView) findViewById(R.id.mScrollView);
//        refreshLayout.setOnRefreshListener(new PullToRefreshScrollView.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                refreshLayout.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
////                        if (count++ % 3 == 0) {
////                            ClassicsHeader classicsHeader = refreshLayout.getHeaderView();
////                            classicsHeader.setRefreshError();
////                        }
//                        getCarList();
//                    }
//                }, 1000);
//            }
//
//            @Override
//            public void onLoading() {
//                //刷新中
//            }
//        });
//        refreshLayout.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                refreshLayout.autoRefresh();
//            }
//        }, 150);
        getCarList(); //获取车辆列表 + 停车记录 数据请求
//        dialogPostData();//活动弹框数据请求
        getImageData();//获取首页轮播图
        CheckVersionNum();//检查版本更新
//        postJpushData();//极光推送数据请求
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        locationService = ((BaseApplication) getApplication()).locationService;
        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        locationService.registerListener(mListener);
        locationService.start();
    }

    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // 非空判断
            if (location != null) {
                // 根据BDLocation 对象获得经纬度以及详细地址信息
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                String address = location.getAddrStr(); // 中国北京市海淀区悦秀路
                LogUtils.d("address:" + address + " latitude:" + latitude + " longitude:" + longitude + "---" +
                        location.getAddress() + location.getCity() + location.getProvince() + location.getDistrict()
                        + location.getCountry() + location.getSemaAptag() + location.getNetworkLocationType());
                mlatLng_my = new LatLng(location.getLatitude(), location.getLongitude());
                if (!StringUtil.isEmpty(address)) {
                    String str = address.substring(2, address.length());
                    tv_dingwei.setText("" + str);
                }
                Animation circle_anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_round_rotate);
                LinearInterpolator interpolator = new LinearInterpolator();  //设置匀速旋转，在xml文件中设置会出现卡顿
                circle_anim.setInterpolator(interpolator);
                if (circle_anim != null) {
                    iv_shuaxin1.startAnimation(circle_anim);  //开始动画
                }
                postTuiJianCarPlace(latitude, longitude);
            }
        }
    };

    /**
     * 获取附近推荐停车场 接口
     *
     * @param latitude
     * @param longitude
     */
    private void postTuiJianCarPlace(double latitude, double longitude) {
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                JSONObject jsobj2 = new JSONObject();
                jsobj2.put("wd", latitude);
                jsobj2.put("jd", longitude);

                jsobj1.put("parameter", jsobj2);
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.postTuiJianCarPlace(httpUtils, jsobj1, this, UrlConfig.Car_Place_TuiJian_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    private void postJpushData() {
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                jsobj1.put("parameter", "ysz");
                jsobj1.put("authKey", spUtil.getString("authkey"));
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.JpushBand(httpUtils, jsobj1, this, UrlConfig.USER_JPUSH_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    private void dialogPostData() {
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                jsobj1.put("userId", spUtil.getInt("id"));
                jsobj1.put("authKey", spUtil.getString("authkey"));
                jsobj1.put("lx", "android");
                jsobj1.put("wzlx", "弹窗");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.AppUserGetImageData(httpUtils, jsobj1, this, UrlConfig.SHOUYE_TC_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    private void setImageBackground(int selectItems) {
        for (int j = 0; j < dots.length; j++) {
            if (j == selectItems) {
                dots[j].setBackgroundResource(R.drawable.dot_guide_focus);
            } else {
                dots[j].setBackgroundResource(R.drawable.dot_guide_normal);
            }
        }
    }

    @Override
    public void setLisener() {
        iv_drawerToggle.setOnClickListener(this);
        ll_MyOrder1.setOnClickListener(this);
        ll_MyCar1.setOnClickListener(this);
        ll_MyPurse1.setOnClickListener(this);
        ll_find_Parking1.setOnClickListener(this);
        ll_MyBurse.setOnClickListener(this);
        ll_MyOrder.setOnClickListener(this);
        ll_Bill_Recording.setOnClickListener(this);
        ll_MyCar.setOnClickListener(this);
        ll_youhuiq.setOnClickListener(this);
        ll_Setting.setOnClickListener(this);
        ll_Invited.setOnClickListener(this);
        ll_UserInfo.setOnClickListener(this);
        iv_main_action.setOnClickListener(this);
        iv_shuaxin1.setOnClickListener(this);
        mScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mScrollView.setOnRefreshListener(this);
        headline.setOnClickListener(this);
//        scrollView.setScrollToBottomListener(new BottomScrollView.OnScrollToBottomListener() {
//            @Override
//            public void onScrollToBottom() {
//                isSvToBottom = true;
//            }
//
//            @Override
//            public void onNotScrollToBottom() {
//                isSvToBottom = false;
//            }
//        });
//        scrollView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                mScrollView.requestDisallowInterceptTouchEvent(false);
//                return false;
//            }
//        });
        mScrollView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction()==MotionEvent.ACTION_UP) {
//                    scrollView.requestDisallowInterceptTouchEvent(false);
//                }else {
//
//                }
//                scrollView.requestDisallowInterceptTouchEvent(true);
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    mLastY = event.getY();
                }
                if (action == MotionEvent.ACTION_MOVE) {
                    int top = mScrollView.getChildAt(0).getTop();
                    float nowY = event.getY();
                    if (!isSvToBottom) {
                        // 允许scrollview拦截点击事件, scrollView滑动
                        mScrollView.requestDisallowInterceptTouchEvent(false);
                    } else if (top == 0 && nowY - mLastY > THRESHOLD_Y_LIST_VIEW) {
                        // 允许scrollview拦截点击事件, scrollView滑动
                        mScrollView.requestDisallowInterceptTouchEvent(false);
                    } else {
                        // 不允许scrollview拦截点击事件， listView滑动
                        mScrollView.requestDisallowInterceptTouchEvent(true);
                    }
                }
                if (action == MotionEvent.ACTION_UP) {
                    scrollView.setEnabled(true);
                }
                return false;
            }
        });

//        mScrollView.autoRefresh();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_drawerToggle:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.headline:
                break;
            case R.id.rl_MyOrder:
                Intent intentParkingRecord = new Intent(this, TingcheJiLuActivity.class);
                startActivity(intentParkingRecord);
                break;
            case R.id.ll_MyOrder:
                Intent intentllParkingRecord = new Intent(this, TingcheJiLuActivity.class);
                startActivity(intentllParkingRecord);
                break;
            case R.id.rl_MyCar:
                Intent intentMyCar = new Intent(this, MyCarActivity.class);
                startActivity(intentMyCar);
                break;
            case R.id.ll_MyCar:
                Intent intentllMyCar = new Intent(this, MyCarActivity.class);
                startActivity(intentllMyCar);
                break;
            case R.id.rl_MyPurse:
                Intent intentMyBurse = new Intent(this, MyBurseActivity.class);
                startActivity(intentMyBurse);
                break;
            case R.id.ll_MyBurse:
                Intent intentllMyBurse = new Intent(this, MyBurseActivity.class);
                startActivity(intentllMyBurse);
                break;
            case R.id.ll_UserInfo:
                Intent intentUserInfo = new Intent(this, PersonnalInfoActivity.class);
                startActivity(intentUserInfo);
                break;
            case R.id.rl_find_Parking:
                Intent intentMapParking = new Intent(this, IndoorLocationActivity.class);
                startActivity(intentMapParking);
                break;
            case R.id.ll_Setting:
                Intent intentSetting = new Intent(this, SettingsActivity.class);
                startActivity(intentSetting);
                break;
            case R.id.ll_Invited:
                Intent intentInvited = new Intent(this, ShareMenuActivity.class);
                startActivity(intentInvited);
                break;
            case R.id.ll_Bill_Recording:
                Intent intentBill = new Intent(this, BillRecordActivity.class);
                startActivity(intentBill);
                break;
//            case R.id.ll_Stopping_State:
//                Intent intent = new Intent(this, MyRecordDetailActivity.class);
//                intent.putExtra("id", billrecordvo.getId());
//                intent.putExtra("sjly", billrecordvo.getSjly());
//                startActivity(intent);
//                break;
            case R.id.tv_AddCar:
                Intent intentAddCar = new Intent(this, AddCarActivity.class);
                startActivity(intentAddCar);
                break;
            case R.id.iv_main_action:
                iv_xhd.setVisibility(View.GONE);
                Intent intent1 = new Intent(this, MessageSystemActivity.class);
                startActivity(intent1);
                break;
            case R.id.ll_youhuiq:
//                ToastUtil.makeShortText(this, "新功能开发中...敬请期待");
                Intent intent2 = new Intent(this, YouHuiJuanActivity.class);
                startActivity(intent2);
                break;
            case R.id.iv_shuaxin1:
                getPersimmions(this);
                locationService.start();
                break;
            default:
                break;
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
//        getOrderRecord();
//        refreshView.setBackgroundResource(R.mipmap.bg_android);
//        refreshView.setBackgroundDrawable(getResources().getDrawable(R.mipmap.bg_android));
        AnimationSet aset = new AnimationSet(true);
        AlphaAnimation animation = new AlphaAnimation(1, 0);
        animation.setDuration(5000);//设置动画持续时间
        animation.startNow();
        aset.addAnimation(animation);
//        if (refreshView.isRefreshing()){
//            new AnimationUtil().setShowAnimation(iv_main_icon, 2000);
//            new AnimationUtil().setShowAnimation(iv_main_xiaoxi, 2000);
//        }
//        iv_main_icon.startAnimation(aset);
//        iv_main_xiaoxi.startAnimation(aset);
/** 常用方法 */
//animation.setRepeatCount(int repeatCount);//设置重复次数
//animation.setFillAfter(boolean);//动画执行完后是否停留在执行完的状态
//animation.setStartOffset(long startOffset);//执行前的等待时间
//        iv_main_icon.animate().alpha(0f).setDuration(2000).setListener(null);
//        iv_main_xiaoxi.animate().alpha(0f).setDuration(2000).setListener(null);
//        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out); //实现淡入淡出动画效果
//        new AnimationUtil().setHideAnimation(iv_main_icon, 2000);
//        new AnimationUtil().setHideAnimation(iv_main_xiaoxi, 2000);
        getCarList();
    }


    /**
     * 获取轮播页图片
     */
    private void getImageData() {
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                jsobj1.put("authKey", spUtil.getString("authkey"));
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.AppUserGetImageData(httpUtils, jsobj1, this, UrlConfig.FIRSTPAGE_IMAGE_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }


    /**
     * 获取我的订单
     */
    private void getOrderRecord() {
        mDialog = new YWLoadingDialog(this);
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("page", 1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.AppUsergetOrderRecords(httpUtils, jsobj1, this, UrlConfig.CAR_ORDER_RECORD_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    /**
     * 我的车辆列表
     */
    private void getCarList() {
//        mDialog = new YWLoadingDialog(this);
//        mDialog.show();
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                JSONObject jsobj2 = new JSONObject();

                jsobj1.put("parameter", jsobj2);
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.AppUsergetCarLists(httpUtils, jsobj1, this, UrlConfig.CAR_GETLIST_CODE);
        } else {
            try {
                mScrollView.onRefreshComplete();
            } catch (Exception e) {
                e.printStackTrace();
            }
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    /**
     * 检查版本更新
     */
    private void CheckVersionNum() {
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.AppCheckVersion(httpUtils, jsobj1, this, UrlConfig.VERSION_UPDATE_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {

    }

    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            images[position % images.length].setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if(isClick){
                    LogUtils.d("点击了第" + position % images.length + "张图片");
//                    getBanaerInfo(imageList.get(position % images.length).getHdid());
//                        isClick=false;
//                    }
                }
            });

            try {
                container.addView(images[position % images.length], 0);
            } catch (Exception e) {
                //handler something
            }
            return images[position % images.length];
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        tv_UserName.setText(spUtil.getString("fullName"));
        String userImagePath = spUtil.getString("userImagePath");
        if (!StringUtil.isEmpty(userImagePath)) {
            ImageLoader.getInstance().loadImage(userImagePath, new SimpleImageLoadingListener() {

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    iv_circleHead.setImageBitmap(loadedImage);
                }
            });
        }
    }

    @Override
    public void onSuccessHttp(String responseInfo, int resultCode) {
        super.onSuccessHttp(responseInfo, resultCode);
        switch (resultCode) {
            case UrlConfig.CAR_ORDER_RECORD_CODE:
                mDialog.dismiss();
                LogUtils.d("我的订单成功：" + responseInfo);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String message = obj.optString("message");
                    if (code == 0) {
                        JSONObject obj1 = obj.optJSONObject("result");
                        JSONArray jArray = obj1.optJSONArray("items");
                        String count = obj1.optString("count");
                        tv_tingchejilu_count.setText("有" + count + "笔未完成记录");
                        if (jArray.length() > 0) {
                            ll_NoHaveCar.setVisibility(View.GONE);
                            rl_have_tingchejilu.setVisibility(View.VISIBLE);
                            iv_NoHavetingcheDetail.setVisibility(View.GONE);
                            JSONObject obj2 = jArray.optJSONObject(0);
                            billrecordvo.setRwsj(obj2.optString("rwsj"));
                            billrecordvo.setCcmc(obj2.optString("ccmc"));
                            billrecordvo.setTcsc(obj2.optString("tcsc"));
                            billrecordvo.setIsPay(obj2.optString("isPay"));
                            billrecordvo.setZje(obj2.optString("zje"));
                            billrecordvo.setId(obj2.optLong("id"));
                            billrecordvo.setSjly(obj2.optString("sjly"));
                            billrecordvo.setHphm(obj2.optString("hphm"));
                            billrecordvo.setLwsj(obj2.optString("lwsj"));
                            tv_rwsj.setText(billrecordvo.getRwsj());
                            double zje = (double) Integer.parseInt(obj2.optString("zje")) / 100;
                            tv_zje.setText(String.format("%.2f", zje) + "元");
                            tv_tishiyu.setText("邯郸泊车，为您尽享智能贴心停车服务");
                            if (obj2.optString("sjly").equals("0")) {
                                tv_car_shiru.setText(billrecordvo.getHphm() + "已驶入智能停车场");
                                tv_tcscOrlwsj.setText("停车时长:");
                                tv_tcsc.setText(billrecordvo.getTcsc());
                                tv_IsPay.setText("");
                                rl_have_tingchejilu.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(MainActivity.this, MyRecordDetailActivity.class);
                                        intent.putExtra("id", billrecordvo.getId());
                                        intent.putExtra("sjly", billrecordvo.getSjly());
                                        startActivity(intent);
                                    }
                                });
                            } else if (obj2.optString("sjly").equals("3")) {
                                tv_car_shiru.setText(billrecordvo.getHphm() + "已驶离智能停车场");
                                tv_tcscOrlwsj.setText("离场时间:");
                                tv_tcsc.setText(billrecordvo.getLwsj());
                                String str;
                                if (obj2.optString("isPay").equals("已完成")) {
                                    str = "(已支付)";
                                    SpannableStringBuilder builder = new SpannableStringBuilder(str);
                                    ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.parseColor("#29c08b"));
                                    builder.setSpan(redSpan, str.indexOf("(") + 1, str.length() - 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                                    tv_IsPay.setText(builder);
                                    rl_have_tingchejilu.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(MainActivity.this, MyRecordDetailCompleteActivity.class);
                                            intent.putExtra("id", billrecordvo.getId());
                                            intent.putExtra("sjly", billrecordvo.getSjly());
                                            startActivity(intent);
                                        }
                                    });
                                } else {
                                    str = "(未支付)";
                                    SpannableStringBuilder builder = new SpannableStringBuilder(str);
                                    ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.parseColor("#ec3434"));
                                    builder.setSpan(redSpan, str.indexOf("(") + 1, str.length() - 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                                    tv_IsPay.setText(builder);
                                    rl_have_tingchejilu.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(MainActivity.this, MyRecordDetailActivity.class);
                                            intent.putExtra("id", billrecordvo.getId());
                                            intent.putExtra("sjly", billrecordvo.getSjly());
                                            startActivity(intent);
                                        }
                                    });
                                }
                            }
                            if (obj2.optString("zflx").equals("5")) {
                                tv_car_shiru.setText(billrecordvo.getHphm() + "已驶入智能停车场");
                                iv_NoHavetingcheDetail.setVisibility(View.VISIBLE);
                                iv_yueka_sign.setVisibility(View.VISIBLE);
                                iv_NoHavetingcheDetail.setImageResource(R.mipmap.yuekacar);
                                rl_tingcheDetail.setVisibility(View.GONE);

                            } else {
                                iv_NoHavetingcheDetail.setVisibility(View.GONE);
                                iv_yueka_sign.setVisibility(View.GONE);
                                rl_tingcheDetail.setVisibility(View.VISIBLE);
                            }
                        } else {
                            getBillRecord();//查看我的账单
                            rl_have_tingchejilu.setVisibility(View.VISIBLE);
                            tv_car_shiru.setText("您的爱车未驶入智能停车场");
                            iv_NoHavetingcheDetail.setVisibility(View.VISIBLE);
                            iv_NoHavetingcheDetail.setImageResource(R.mipmap.iv_maining_addcar);
                            rl_tingcheDetail.setVisibility(View.GONE);
                        }
                    } else if (code == 1001) { //版本更新 弹框

                    } else if (code == 1002) { //退出登录 弹框
                        JieKouDiaoYongUtils.loginTanKuan(this);
                    } else {
                        ToastUtil.makeShortText(this, message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case UrlConfig.CAR_GETLIST_CODE:
                mScrollView.onRefreshComplete();
                LogUtils.d("我的车辆列表：" + responseInfo);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String result = obj.optString("result");
                    if (code == 0) {
                        JSONArray array = obj.optJSONArray("result");
                        tv_mycar_count.setText("已绑定" + array.length() + "台车辆");
                        if (array.length() > 0) {
                            ll_NoHaveCar.setVisibility(View.GONE);
                            getOrderRecord();//查看我的订单
                        }
                    } else if (code == 3001) {
                        ll_NoHaveCar.setVisibility(View.VISIBLE);
                        rl_have_tingchejilu.setVisibility(View.GONE);
                        ll_NoHaveCar.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intentAddCar = new Intent(MainActivity.this, AddCarActivity.class);
                                startActivity(intentAddCar);
                            }
                        });
                        tv_mycar_count.setText("已绑定0台车辆");
                        tv_tingchejilu_count.setText("有0笔未完成记录");
                    } else if (code == 1001) { //版本更新 弹框

                    } else if (code == 1002) { //退出登录 弹框
                        JieKouDiaoYongUtils.loginTanKuan(this);
                    } else {
                        ToastUtil.makeShortText(this, result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case UrlConfig.FIRSTPAGE_IMAGE_CODE:
                LogUtils.d("获取轮播图片成功：" + responseInfo);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String result = obj.optString("result");
                    Long result_xhd = obj.optLong("result_xhd");
                    if (code == 0) {
                        iv_shouye_moren.setVisibility(View.GONE);
                        mViewPaper.setVisibility(View.VISIBLE);
                        SharedPreferenceUtil spUtils = SharedPreferenceUtil.init(this, SharedPreferenceUtil.ACTION_LIST, MODE_PRIVATE);
                        int actionsize = spUtils.getInt("actionsize");
                        FirstPageImageBean firstpageimagebean = AnsynHttpRequest.parser.fromJson(responseInfo, FirstPageImageBean.class);
//                        int xhd = firstpageimagebean.getXhd();
                        if (result_xhd > 0) {
                            iv_xhd.setVisibility(View.VISIBLE);
                        } else {
                            iv_xhd.setVisibility(View.GONE);
                        }
                        imageList = firstpageimagebean.getResult();

                        //显示的图片
                        images = new ImageView[imageList.size()];
                        for (int i = 0; i < images.length; i++) {
                            ImageView imageView = new ImageView(this);
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                            imageView.setImageResource(R.mipmap.bg_moren_banner);
                            images[i] = imageView;
//                            imageView.setBackgroundResource(imageIds[i]);
                            bitmapUtils.display(imageView, imageList.get(i).getTpdz());
                        }

                        //显示的小点
                        dots = new ImageView[imageList.size()];
                        group.removeAllViews();
                        for (int i = 0; i < dots.length; i++) {
                            ImageView imageView = new ImageView(this);
                            imageView.setLayoutParams(new ViewGroup.LayoutParams(7, 7));
                            dots[i] = imageView;
                            if (i == 0) {
                                dots[i].setBackgroundResource(R.drawable.dot_guide_focus);
                            } else {
                                dots[i].setBackgroundResource(R.drawable.dot_guide_normal);
                            }

                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT));
                            layoutParams.leftMargin = 5;
                            layoutParams.rightMargin = 5;
                            group.addView(imageView, layoutParams);
                        }
//                        adapter = new ViewPagerAdapter();
                        adapter2 = new VpAdapter(this, firstpageimagebean.getResult());
//                        mViewPaper.setAdapter(adapter);
                        mViewPaper.setAdapter(adapter2);
                        mViewPaper.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageSelected(int position) {
                                setImageBackground(position % images.length);
//                                mViewPaper.setCurrentItem(position % images.length);
                            }

                            @Override
                            public void onPageScrolled(int arg0, float arg1, int arg2) {

                            }

                            @Override
                            public void onPageScrollStateChanged(int arg0) {
//                                if(arg0==ViewPager.SCROLL_STATE_IDLE){
//                                    // 设置当前页,smoothScroll 平稳滑动
//                                    mViewPaper.setCurrentItem(currentPosition, false);
//                                }
                            }
                        });
                        mViewPaper.setCurrentItem((images.length) * 100);
                        handler2.sendEmptyMessageDelayed(0, 3000);
//                        adapter.notifyDataSetChanged();
                    } else if (code == 1004) { //无数据
                        iv_shouye_moren.setVisibility(View.VISIBLE);
                        mViewPaper.setVisibility(View.GONE);
                    } else {
                        if (StringUtil.isEmpty(result)) {
                            ToastUtil.makeShortText(this, result);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case UrlConfig.VERSION_UPDATE_CODE:
                LogUtils.d("检测版本更新：" + responseInfo);
                int currentCode = JieKouDiaoYongUtils.getVersionCode(this);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    if (code == 0) {
                        JSONObject obj1 = obj.optJSONObject("result");
                        int newversionNo = Integer.parseInt(obj1.optString("versionNo"));
                        if (newversionNo > currentCode) {
                            versionPath = obj1.optString("versionPath");
                            mUpdateManager = new UpdateManager(this);
                            mUpdateManager.showNoticeDialog(obj1.optString("versionPath"), newversionNo, obj1.optString("versionDescription"));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case UrlConfig.USER_JPUSH_CODE:
                LogUtils.d("极光推送绑定成功：" + responseInfo);
                break;
            case UrlConfig.Car_Place_TuiJian_CODE:
                locationService.stop();
                LogUtils.d("推荐附近停车场：" + responseInfo);
                try {
                    JSONObject jsonObject = new JSONObject(responseInfo);
                    int code = jsonObject.optInt("code");
                    String result = jsonObject.getString("result");
                    if (code == 0) {
                        Gson gson = new Gson();
                        handler.sendEmptyMessageDelayed(0, 1000);
                        if (!StringUtil.isEmpty(result)) {
                            final GarageBean.ResultBean ccBean = gson.fromJson(result, GarageBean.ResultBean.class);
                            final List<GarageBean.ResultBean> garage = new ArrayList<>();
                            garage.add(ccBean);
                            if (ccBean.getTotalCount().equals("0")) {
                                ll_shouye_have_carplace.setVisibility(View.GONE);
                                ll_shouye_no_carplace.setVisibility(View.VISIBLE);
                            } else {
                                ll_shouye_have_carplace.setVisibility(View.VISIBLE);
                                ll_shouye_no_carplace.setVisibility(View.GONE);
                            }
                            tv_carplace_num.setText("" + ccBean.getTotalCount());
                            tv_ccmc.setText("" + ccBean.getCcmc());
                            tv_kbwsl.setText("空" + ccBean.getKbwsl() + "车位");
                            final String newDistance = StringUtil.convertMtoKM((int) DistanceUtil.getDistance(
                                    new LatLng(Double.valueOf(ccBean.getCczbY()), Double.valueOf(ccBean.getCczbX())), mlatLng_my));
                            tv_ccjl.setText("" + newDistance);
                            ll_go_zheli.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(MainActivity.this, CarPlaceItemActivity.class);
                                    intent.putExtra("garage", (Serializable) garage);
                                    intent.putExtra("zindex", "1");
                                    intent.putExtra("distance", newDistance);
                                    intent.putExtra("latlng_my", mlatLng_my);
                                    startActivity(intent);
                                }
                            });
                            ll_shouye_have_carplace.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intentMapParking = new Intent(MainActivity.this, IndoorLocationActivity.class);
                                    startActivity(intentMapParking);
                                }
                            });
                        } else {
                            ll_shouye_have_carplace.setVisibility(View.GONE);
                            ll_shouye_no_carplace.setVisibility(View.VISIBLE);
                            tv_carplace_num.setText("0");
                        }
                    } else {
                        ToastUtil.makeShortText(this, result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case UrlConfig.SHOUYE_TC_CODE:
                LogUtils.d("首页活动弹框获取成功：" + responseInfo);
                try {
                    JSONObject jsonObject = new JSONObject(responseInfo);
                    String code = jsonObject.getString("code");
                    String result = jsonObject.getString("result");
                    String time = jsonObject.getString("time");
                    String sfgq = jsonObject.getString("sfgq");
                    if (code.equals("0000")) {
                        List<FirstPageImageBean.ResultBean> resultBean = AnsynHttpRequest.parser.fromJson(responseInfo, FirstPageImageBean.class).getResult();
                        if (sfgq.equals("true")) {
                            //不弹窗
                        } else {
                            try {
                                tanchu(spUtil.getString("phoneNo"), time, resultBean);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
//                        if (result.equals("0")){
//                            SharedPreferences pref = getSharedPreferences("myActivityName2", MODE_PRIVATE);
//                            boolean isFirst = pref.getBoolean("isFirstIn",false);
//               /不弹框             if (isFirst){
//                                return;
//                            }
//                            SharedPreferences.Editor editor = pref.edit();
//                            editor.putBoolean("isFirstIn", true);
//                            editor.commit();
//                        }else if (result.equals("1")){
//                            /
//                        }
                    } else {
                        ToastUtil.makeShortText(this, result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case UrlConfig.CAR_BILL_RECORD_CODE:
                LogUtils.d("获取账单信息成功：" + responseInfo);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String result = obj.optString("result");
                    if (code == 0) {
                        JSONObject obj1 = obj.optJSONObject("result");
                        if (obj1.has("totalCount")) {
                            ll_NoHaveCar.setVisibility(View.GONE);
                            iv_NoHavetingcheDetail.setVisibility(View.GONE);
                            int totalCount = obj1.optInt("totalCount");
                            if (totalCount > 0) {
                                rl_have_tingchejilu.setVisibility(View.VISIBLE);
                                JSONArray jArray = obj1.optJSONArray("items");
                                final ParkingRecordHistoryVo billrecordvo = new ParkingRecordHistoryVo();
                                JSONObject obj2 = jArray.optJSONObject(0);
                                billrecordvo.setRwsj(obj2.optString("rwsj"));
                                billrecordvo.setCcmc(obj2.optString("ccmc"));
                                billrecordvo.setTcsc(obj2.optString("tcsc"));
                                billrecordvo.setZje(obj2.optString("zje"));
                                billrecordvo.setId(obj2.optLong("id"));
                                billrecordvo.setSjly(obj2.optString("sjly"));
                                billrecordvo.setHphm(obj2.optString("hphm"));
                                billrecordvo.setLwsj(obj2.optString("lwsj"));
                                iv_NoHavetingcheDetail.setVisibility(View.GONE);
                                rl_tingcheDetail.setVisibility(View.VISIBLE);
                                if (obj2.has("zflx")) {
                                    if (obj2.optString("zflx").equals("5")) {
                                        iv_NoHavetingcheDetail.setVisibility(View.VISIBLE);
                                        iv_yueka_sign.setVisibility(View.VISIBLE);
                                        iv_NoHavetingcheDetail.setImageResource(R.mipmap.yuekacar);
                                        rl_tingcheDetail.setVisibility(View.GONE);
                                    } else {
                                        iv_NoHavetingcheDetail.setVisibility(View.GONE);
                                        iv_yueka_sign.setVisibility(View.GONE);
                                        rl_tingcheDetail.setVisibility(View.VISIBLE);
                                    }
                                }

                                tv_car_shiru.setText(billrecordvo.getHphm() + "已驶离智能停车场");
                                tv_rwsj.setText(billrecordvo.getRwsj());
                                tv_tcscOrlwsj.setText("离场时间:");
                                tv_tcsc.setText(billrecordvo.getLwsj());
                                double zje2 = (double) Integer.parseInt(obj2.optString("zje")) / 100;
                                tv_zje.setText(String.format("%.2f", zje2) + "元");
                                String str = "(已支付)";
                                SpannableStringBuilder builder = new SpannableStringBuilder(str);
                                ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.parseColor("#29c08b"));
                                builder.setSpan(redSpan, str.indexOf("(") + 1, str.length() - 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                                tv_IsPay.setText(builder);
                                tv_tishiyu.setText("邯郸泊车，为您尽享智能贴心停车服务");
                                if (obj2.optString("zflx").equals("5")) {
                                    iv_NoHavetingcheDetail.setVisibility(View.VISIBLE);
                                    iv_yueka_sign.setVisibility(View.VISIBLE);
                                    iv_NoHavetingcheDetail.setImageResource(R.mipmap.yuekacar);
                                    rl_tingcheDetail.setVisibility(View.GONE);
                                } else {
                                    iv_NoHavetingcheDetail.setVisibility(View.GONE);
                                    iv_yueka_sign.setVisibility(View.GONE);
                                    rl_tingcheDetail.setVisibility(View.VISIBLE);
                                }
                                rl_have_tingchejilu.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(MainActivity.this, MyRecordDetailCompleteActivity.class);
                                        intent.putExtra("id", billrecordvo.getId());
                                        intent.putExtra("sjly", billrecordvo.getSjly());
                                        startActivity(intent);
                                    }
                                });
                            } else {
                                tv_car_shiru.setText("您的爱车未驶入智能停车场");
                                iv_NoHavetingcheDetail.setVisibility(View.VISIBLE);
                                iv_NoHavetingcheDetail.setImageResource(R.mipmap.iv_maining_addcar);
                                rl_tingcheDetail.setVisibility(View.GONE);
                            }
                        } else { //无账单
                            tv_car_shiru.setText("您的爱车未驶入智能停车场");
                            iv_NoHavetingcheDetail.setVisibility(View.VISIBLE);
                            iv_NoHavetingcheDetail.setImageResource(R.mipmap.iv_maining_addcar);
                            rl_tingcheDetail.setVisibility(View.GONE);
                        }
                    } else if (code == 1001) { //版本更新 弹框

                    } else if (code == 1002) { //退出登录 弹框
                        JieKouDiaoYongUtils.loginTanKuan(this);
                    } else {
                        ToastUtil.makeShortText(this, result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 获取 已完成 账单记录
     */
    private void getBillRecord() {
        if (NetWorkUtil.isNetworkConnected(this)) {
            spUtil = SharedPreferenceUtil.init(this, SharedPreferenceUtil.LOGIN_INFO, MODE_PRIVATE);
            JSONObject jsobj1 = new JSONObject();
            try {
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("page", 1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.AppUsergetBillRecords(httpUtils, jsobj1, this, UrlConfig.CAR_BILL_RECORD_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                JPushInterface.setAlias(getApplicationContext(), "5555", new TagAliasCallback() {

                    @Override
                    public void gotResult(int arg0, String arg1, Set<String> arg2) {
                    }
                });
                UrlConfig.isSetDialogShow = true;
                killAll();
                finish();
                DataCleanManager.clearAllCache(this);//清空缓存
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onFailureHttp(HttpException error, String msg) {
        super.onFailureHttp(error, msg);
        LogUtils.d("获取失败");
        mDialog.dimissFail();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getImageData();
        getCarList();
    }

    @Subscribe(sticky = true)    //看下 `@Subscribe` 源码知道 `sticky` 默认是 `false`
    public void onEvent(FirstEvent event) {
        String msg = "onEventMainThread收到了消息：" + event.getMsg();
        Log.d("harvic", msg);
//        ToastUtil.showToast(this,""+msg);
        if (event.getMsg().equals("message3")) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getOrderRecord();
        } else if (event.getMsg().equals("message1")) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getOrderRecord();
        } else if (event.getMsg().equals("message0")) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getOrderRecord();
        } else if (event.getMsg().equals("wangwei")) {
            iv_xhd.setVisibility(View.VISIBLE);
        } else if (event.getMsg().equals("monthCardIn")) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getOrderRecord();
        } else if (event.getMsg().equals("monthCarOut")) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getOrderRecord();
        } else if (event.getMsg().equals("monthCar")) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getOrderRecord();
        }
    }

    /**
     * 首页 活动弹框
     */
    public void showActivityDialog(final List<FirstPageImageBean.ResultBean> resultBean) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);//设置外部不可点击
        dialog.setCancelable(false);
        final View view = getLayoutInflater().inflate(R.layout.dialog_activity_info, null);
        RelativeLayout iv_close = (RelativeLayout) view.findViewById(R.id.iv_action_close);
        iv_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation translateAnimation = new TranslateAnimation(0.1f, -100.0f, 0.1f, -100.0f);//初始化
                translateAnimation.setDuration(1000);//设置动画时间
                view.startAnimation(translateAnimation);//开启动画
                dialog.dismiss();
            }
        });
        ImageView rel_action = (ImageView) view.findViewById(R.id.ll_action);
        new BitmapUtils(MainActivity.this).display(rel_action, resultBean.get(0).getTpdz());//图片url
        rel_action.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserProtocolActivity.class);
                intent.putExtra("H5_url", resultBean.get(0).getNr());
                intent.putExtra("yhjurl", "");
                intent.putExtra("fxurl", "");
                intent.putExtra("tv_title", "活动详情");
                intent.putExtra("hdgqsj", resultBean.get(0).getSfgq());
                startActivity(intent);
            }
        });
        dialog.show();
        dialog.setContentView(view);
    }

    public Map<String, TestDemo> a = new HashMap<String, TestDemo>();

    public void tanchu(String iphone, String 服务器当前时间, List<FirstPageImageBean.ResultBean> resultBean) throws ParseException {
        System.out.println("============" + iphone + "===bigen==" + 服务器当前时间 + "=========");
        SharedPreferences sp = getSharedPreferences("activity", MODE_APPEND);
        String json = sp.getString("item", "");
        if (!json.equals("")) {
            a = JSON.parseObject(json, Map.class);
            Log.d("main", "" + a.get(iphone));//对象
        }
        if (a.get(iphone) == null) {
            showActivityDialog(resultBean);//首页活动弹框
            System.out.println("============" + iphone + "===弹窗===" + 服务器当前时间 + "========");
            a.put(iphone, new TestDemo(iphone, 服务器当前时间));
            TestDemo vc = a.get(iphone);
            a.put(iphone, vc);
            SharedPreferences sp2 = getSharedPreferences("activity", MODE_APPEND);
            SharedPreferences.Editor edit = sp2.edit();
            String json2 = JSON.toJSONString(a);
            edit.putString("item", json2);
            edit.commit();
        } else {
            TestDemo vc = JSON.parseObject(JSON.toJSONString(a.get(iphone)), TestDemo.class);
            String date = vc.getDate();
            if (parse(date, 服务器当前时间)) {
                showActivityDialog(resultBean);//首页活动弹框
                TestDemo vc2 = a.get(iphone);
                vc2.setDate(服务器当前时间);
                a.put(iphone, vc2);
                SharedPreferences sp2 = getSharedPreferences("activity", MODE_APPEND);
                SharedPreferences.Editor edit = sp2.edit();
                String json2 = JSON.toJSONString(a);
                edit.putString("item", json2);
                edit.commit();
                System.out.println("====" + 服务器当前时间 + "========" + iphone + "===弹窗========");
                System.out.println("当前登录时间回更" + vc2.getDate());
            } else {
                System.out.println("=====服务器当前时间小上次登录时间==" + date + "=n====" + iphone + "===不弹窗=服务器当前时间==" + 服务器当前时间 + "========");
            }
        }
        System.out.println("============" + iphone + "===end=====" + 服务器当前时间 + "======");
    }


    public static boolean parse(String validDate, String Nowdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//	    	System.out.println(Nowdate+"======="+sdf.parse(Nowdate).getTime());
//	    	System.out.println(validDate+"======="+sdf.parse(validDate).getTime());
        //服务器当前时间大于上次登录时间
//服务器当前时间小上次登录时间
        return sdf.parse(Nowdate).getTime() > sdf.parse(validDate).getTime();
    }

    @Override
    protected void onStop() {
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        // 取消监听函数
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mUpdateManager.showDownloadDialog(versionPath);
                }
                break;
            case SDK_PERMISSION_REQUEST:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    locationService.start();
                } else {
                    ToastUtil.makeShortText(this, "请检查您的定位服务是否开启");
                }
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler2.removeMessages(0);
    }

    @TargetApi(23)
    public void getPersimmions(Activity activity) {
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
}