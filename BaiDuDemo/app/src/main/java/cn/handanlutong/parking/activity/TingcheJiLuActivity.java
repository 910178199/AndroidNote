package cn.handanlutong.parking.activity;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.bean.FirstEvent;
import cn.handanlutong.parking.fragment.FragmentFactory2;
import cn.handanlutong.parking.http.HttpMethod;
import cn.handanlutong.parking.http.UrlConfig;
import cn.handanlutong.parking.utils.JieKouDiaoYongUtils;
import cn.handanlutong.parking.utils.LogUtils;
import cn.handanlutong.parking.utils.NetWorkUtil;
import cn.handanlutong.parking.utils.SharedPreferenceUtil;
import cn.handanlutong.parking.utils.ToastUtil;

/**
 * 停车记录 新 页面
 * Created by zhangyonggang on 2017/4/11.
 */

public class TingcheJiLuActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = TingcheJiLuActivity.class.getSimpleName();
    private ImageView iv_back_TransationDetail;
    private ViewPager vp_MviewPage;
    private TextView tv_ExpenseDetail, tv_Rechargedetail;
    private View vv_Rechargedetail, vv_ExpenseDetail;
    private TextView tv_amountMoney1;
    private TextView tv_carNum1;
    private TextView tv_notAuthentication;
    private TextView tvArrearsTotalMoney1;
    private TextView tvArrearsTotalMoney2;
    private TextView tv_amountMoney2;
    private TextView tv_carNum2;
    private TextView tv_unit1;
    private TextView tv_unit2;
    private static final int ARREARS_NULL = 4, ARREARS_0 = 0;
    private SharedPreferenceUtil spUtil;
    private String money;
    private String rzzt;
    private TextView tv_title;
    private TextView tv_notAuthentication2;

    @Override
    public void initView() {
        setContentView(R.layout.activity_tingche_jilu);
        spUtil = SharedPreferenceUtil.init(this, SharedPreferenceUtil.LOGIN_INFO, MODE_PRIVATE);
        iv_back_TransationDetail = (ImageView) findViewById(R.id.iv_back_TransationDetail);
        vp_MviewPage = (ViewPager) findViewById(R.id.vp_MviewPage);
        tv_ExpenseDetail = (TextView) findViewById(R.id.tv_ExpenseDetail);
        tv_Rechargedetail = (TextView) findViewById(R.id.tv_Rechargedetail);
        vv_ExpenseDetail = findViewById(R.id.vv_ExpenseDetail);
        vv_Rechargedetail = findViewById(R.id.vv_Rechargedetail);


        tvArrearsTotalMoney1 = (TextView) findViewById(R.id.tv_arrears_total_money1);
        tvArrearsTotalMoney2 = (TextView) findViewById(R.id.tv_arrears_total_money2);
        tv_amountMoney1 = (TextView) findViewById(R.id.tv_amount_money1);
        tv_amountMoney2 = (TextView) findViewById(R.id.tv_amount_money2);
        tv_carNum1 = (TextView) findViewById(R.id.tv_car_num1);
        tv_carNum2 = (TextView) findViewById(R.id.tv_car_num2);
        tv_notAuthentication = (TextView) findViewById(R.id.tv_not_authentication1);
        tv_notAuthentication2 = (TextView) findViewById(R.id.tv_not_authentication2);
        tv_unit1 = (TextView) findViewById(R.id.tv_unit1);
        tv_unit2 = (TextView) findViewById(R.id.tv_unit2);
        tv_title = (TextView) findViewById(R.id.tv_title);


        vp_MviewPage.setAdapter(new MainAdpater(getSupportFragmentManager()));
        vp_MviewPage.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                                                 @Override
                                                 public void onPageSelected(int position) {
                                                     super.onPageSelected(position);
                                                     switch (position) {
                                                         case 0:
                                                             vv_ExpenseDetail.setBackgroundColor(Color.parseColor("#29c086"));
                                                             vv_Rechargedetail.setBackgroundColor(Color.parseColor("#e7e7e7"));
                                                             tv_ExpenseDetail.setTextColor(Color.parseColor("#29c086"));
                                                             tv_Rechargedetail.setTextColor(Color.parseColor("#777777"));
                                                             break;
                                                         case 1:
                                                             vv_ExpenseDetail.setBackgroundColor(Color.parseColor("#e7e7e7"));
                                                             vv_Rechargedetail.setBackgroundColor(Color.parseColor("#29c086"));
                                                             tv_ExpenseDetail.setTextColor(Color.parseColor("#777777"));
                                                             tv_Rechargedetail.setTextColor(Color.parseColor("#29c086"));
                                                             break;
                                                         default:
                                                             break;
                                                     }
                                                 }
                                             }
        );

        initDatas();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    private void initDatas() {
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
                jsobj1.put("userId", spUtil.getInt("id"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.postTingCheQianFeiData(httpUtils, jsobj1, this, UrlConfig.STOP_CAR_TOTAL_AMOUNT_ARREARS);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }


    @Override
    public void onSuccessHttp(String responseInfo, int resultCode) {
        super.onSuccessHttp(responseInfo, resultCode);
        switch (resultCode) {
            case UrlConfig.STOP_CAR_TOTAL_AMOUNT_ARREARS:

                LogUtils.d("查询未缴费成功：" + responseInfo);
                JSONObject obj = null;
                try {
                    obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    if (0000 == code) {
                        //有车无数据
                        String topMessage = obj.optString("topMessage");
                        tv_title.setText(topMessage);

                        JSONArray result = obj.getJSONArray("result");
                        if (result.length() == 1) {//一辆车
                            money = result.optJSONObject(0).optString("qkje");
                            double qkje = (double) Integer.parseInt(money) / 100;
                            rzzt = result.optJSONObject(0).optString("rzzt");
                            String carNum = result.getJSONObject(0).getString("hphm");
                            tv_amountMoney1.setText(String.format("%.2f", qkje));
                            tv_notAuthentication.setText(rzzt);
                            tv_carNum1.setText(carNum);
                            arrearsState(ARREARS_0);
                        } else if (result.length() == 2) {//两辆车
                            money = result.getJSONObject(0).getString("qkje");
                            double qkje = (double) Integer.parseInt(money) / 100;
                            rzzt = result.getJSONObject(0).getString("rzzt");
                            String rzzt2 = result.getJSONObject(1).getString("rzzt");
                            String money2 = result.getJSONObject(1).getString("qkje");
                            double qkje2 = (double) Integer.parseInt(money2) / 100;
                            String carNum = result.getJSONObject(0).getString("hphm");
                            String carNum2 = result.getJSONObject(1).getString("hphm");
                            tv_amountMoney1.setText(String.format("%.2f", qkje));
                            tv_amountMoney2.setText(String.format("%.2f", qkje2));
                            tv_notAuthentication.setText(rzzt);
                            tv_notAuthentication2.setText(rzzt2);
                            tv_carNum1.setText(carNum);
                            tv_carNum2.setText(carNum2);
                        }
                    } else if (1004 == code) {
                        //无车无数据
                        String topMessage = obj.optString("topMessage");
                        tv_title.setText(topMessage);
                        arrearsState(ARREARS_NULL);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                break;
        }
    }

    @Override
    public void onFailureHttp(HttpException error, String msg) {
        super.onFailureHttp(error, msg);
        LogUtils.d("请求失败！");
    }

    private void arrearsState(int state) {
        switch (state) {
            case ARREARS_NULL:
                //无欠缴
                tvArrearsTotalMoney1.setVisibility(View.VISIBLE);
                tvArrearsTotalMoney2.setVisibility(View.INVISIBLE);
                tv_carNum1.setVisibility(View.INVISIBLE);
                tv_carNum2.setVisibility(View.INVISIBLE);
                tv_amountMoney1.setVisibility(View.VISIBLE);
                tv_amountMoney1.setText("0");
                tv_amountMoney2.setVisibility(View.INVISIBLE);
                tv_notAuthentication.setVisibility(View.INVISIBLE);
                tv_unit2.setVisibility(View.INVISIBLE);
                break;
            case ARREARS_0:
                tvArrearsTotalMoney1.setVisibility(View.VISIBLE);
                tvArrearsTotalMoney2.setVisibility(View.INVISIBLE);
                tv_carNum1.setVisibility(View.VISIBLE);
                tv_carNum2.setVisibility(View.INVISIBLE);
                tv_amountMoney1.setVisibility(View.VISIBLE);
                tv_amountMoney2.setVisibility(View.INVISIBLE);
                tv_notAuthentication.setVisibility(View.VISIBLE);
                tv_unit2.setVisibility(View.INVISIBLE);
                break;
        }
    }


    @Override
    public void setLisener() {
        iv_back_TransationDetail.setOnClickListener(this);
        tv_ExpenseDetail.setOnClickListener(this);
        tv_Rechargedetail.setOnClickListener(this);
    }

    private class MainAdpater extends FragmentStatePagerAdapter {
        public MainAdpater(FragmentManager fm) {
            super(fm);
        }

        // 每个条目返回的fragment
        //  0
        @Override
        public Fragment getItem(int position) {
            //  通过Fragment工厂  生产Fragment
            return FragmentFactory2.createFragment(position, onPullUpDownRefreshListener);
        }

        // 一共有几个条目
        @Override
        public int getCount() {
            return 2;
        }

        // 返回每个条目的标题
        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }

    }

    private onPullUpDownRefreshListener onPullUpDownRefreshListener = new onPullUpDownRefreshListener() {
        @Override
        public void isUpRefreshListener(boolean isUp) {
            initDatas();
        }

        @Override
        public void isDownRefreshListener(boolean isDown) {
            initDatas();
        }
    };


    public interface onPullUpDownRefreshListener {
        void isUpRefreshListener(boolean isUp);

        void isDownRefreshListener(boolean isDown);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back_TransationDetail:
                this.finish();
                break;
            case R.id.tv_ExpenseDetail:
                vp_MviewPage.setCurrentItem(0);
                break;
            case R.id.tv_Rechargedetail:
                vp_MviewPage.setCurrentItem(1);
                break;
            default:
                break;
        }
    }

    @Subscribe(sticky = true)
    public void onEvent(FirstEvent event) {
        String msg = "onEventMainThread收到了消息：" + event.getMsg();
        if (event.getMsg().equals("message3")) {

            initDatas();
        } else if (event.getMsg().equals("message1")) {

            initDatas();
        } else if (event.getMsg().equals("message0")) {
            initDatas();
        }  else if (event.getMsg().equals("monthCardIn")) {

            initDatas();
        } else if (event.getMsg().equals("monthCarOut")) {

            initDatas();
        } else if (event.getMsg().equals("monthCar")) {

            initDatas();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
