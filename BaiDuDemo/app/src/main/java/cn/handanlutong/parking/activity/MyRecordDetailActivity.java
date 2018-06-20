package cn.handanlutong.parking.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.exception.HttpException;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.bean.CouponReceive;
import cn.handanlutong.parking.bean.FirstEvent;
import cn.handanlutong.parking.customview.YWLoadingDialog;
import cn.handanlutong.parking.http.AnsynHttpRequest;
import cn.handanlutong.parking.http.HttpMethod;
import cn.handanlutong.parking.http.UrlConfig;
import cn.handanlutong.parking.utils.JieKouDiaoYongUtils;
import cn.handanlutong.parking.utils.LogUtils;
import cn.handanlutong.parking.utils.NetWorkUtil;
import cn.handanlutong.parking.utils.SharedPreferenceUtil;
import cn.handanlutong.parking.utils.StringUtil;
import cn.handanlutong.parking.utils.ToastUtil;
import cn.handanlutong.parking.utils.UpdateManager;
import cn.handanlutong.parking.zoom.ImagPagerUtil;

/**
 * 我的订单详情
 * Created by zhangyonggang on 2017/4/10.
 */

public class MyRecordDetailActivity extends BaseActivity implements View.OnClickListener {
    private static final int SDK_PAY_FLAG = 1;
    private ViewPager viewPager;
    private ViewGroup ll_group;
    private ImageView[] dots;
    private List<ImageView> imageList_orginal = null;
    private ImageView iv_back_parking_detail, iv_havePay, iv_Pay_Rotate;
    private Button btn_zhifu;
    private TextView tv_Share, tv_ccdz, tv_cwbh, tv_rwsj, tv_tcsc, tv_CarNum,
            tv_zje, tv_zfje, tv_qkje, tv_zffs, tv_tishiyu, tv_lwsj, tv_rworlw,
            tv_liweitime, tv_TotalMoney, tv_shoufeibiaozhun;
    private SharedPreferenceUtil spUtil;
    private long id;
    private String sjly, djlx, lsh, str_yhjid;
    private BitmapUtils bitmapUtils = null;
    private RelativeLayout rl_yizhifu, rl_lwsj;
    private ArrayList<String> mList = null;
    private ArrayList<String> imageUrl_List = null;
    private List<Map<String, String>> mmList = null;
    private IWXAPI wxapi;
    public YWLoadingDialog mDialog;
    public static MyRecordDetailActivity instance;
    private AlertDialog dialog;
    private ImagPagerUtil imagPagerUtil;
    private ViewPagerAdapter adapter;
    private String ccmc, hphm, tcsc, zje, qkje;
    private double zfje_money;
    private Animation animation;
    private ImageView view, iv_yueka;

    @Override
    public void initView() {
        setContentView(R.layout.activity_jilu_xianq);
        imageList_orginal = new ArrayList<ImageView>();
        mList = new ArrayList<>();
        imageUrl_List = new ArrayList<String>();
        mmList = new ArrayList<>();
        UrlConfig.type = 1;
        instance = this;
        spUtil = SharedPreferenceUtil.init(this, SharedPreferenceUtil.LOGIN_INFO, Activity.MODE_PRIVATE);
        bitmapUtils = new BitmapUtils(this);
        Intent intent = getIntent();
        id = intent.getLongExtra("id", 0);
        sjly = intent.getStringExtra("sjly");
        btn_zhifu = (Button) findViewById(R.id.btn_zhifu);
        iv_back_parking_detail = (ImageView) findViewById(R.id.iv_back_parking_detail);
        iv_havePay = (ImageView) findViewById(R.id.iv_havePay);
        iv_Pay_Rotate = (ImageView) findViewById(R.id.iv_Pay_Rotate);
        tv_Share = (TextView) findViewById(R.id.tv_Share);
        tv_ccdz = (TextView) findViewById(R.id.tv_ccdz);
        tv_cwbh = (TextView) findViewById(R.id.tv_cwbh);
        tv_rwsj = (TextView) findViewById(R.id.tv_rwsj);
        tv_lwsj = (TextView) findViewById(R.id.tv_lwsj);
        tv_rworlw = (TextView) findViewById(R.id.tv_rworlw);
        tv_liweitime = (TextView) findViewById(R.id.tv_liweitime);
        tv_zffs = (TextView) findViewById(R.id.tv_zffs);
        tv_zje = (TextView) findViewById(R.id.tv_zje);
        tv_zfje = (TextView) findViewById(R.id.tv_zfje);
        tv_qkje = (TextView) findViewById(R.id.tv_qkje);
        tv_TotalMoney = (TextView) findViewById(R.id.tv_TotalMoney);
        tv_shoufeibiaozhun = (TextView) findViewById(R.id.tv_shoufeibiaozhun);
        tv_tishiyu = (TextView) findViewById(R.id.tv_tishiyu);
        tv_tcsc = (TextView) findViewById(R.id.tv_tcsc);
        tv_CarNum = (TextView) findViewById(R.id.tv_CarNum);
        rl_yizhifu = (RelativeLayout) findViewById(R.id.rl_yizhifu);
        rl_lwsj = (RelativeLayout) findViewById(R.id.rl_lwsj);
        iv_yueka = (ImageView) findViewById(R.id.iv_yueka);
        ll_group = (ViewGroup) findViewById(R.id.ll_viewGroup);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                setImageBackground(position % mList.size());
                Set set = mmList.get(position % mList.size()).keySet();
                Iterator iter = set.iterator();
                String key = null;
                while (iter.hasNext()) {
                    key = (String) iter.next();
                }
                if (key.equals("rwtp1Path") || key.equals("rwtp2Path")) {
                    tv_rworlw.setText("入场图片");
                } else if (key.equals("lwtp1Path") || key.equals("lwtp2Path")) {
                    tv_rworlw.setText("离场图片");
                }
//
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
        getMyOrderDetail();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }


    private void setImageBackground(int selectItems) {
        for (int j = 0; j < dots.length; j++) {
            if (j == selectItems) {
                dots[j].setBackgroundResource(R.drawable.dot_detail_focus);

            } else {
                dots[j].setBackgroundResource(R.drawable.dot_detail_normal);
            }
        }
    }

    private class ViewPagerAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            if (imageList_orginal.size() == 1) {
                return 1;
            } else {
                return Integer.MAX_VALUE;
            }
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final int finalPosition = position;
            imageList_orginal.get(position%imageList_orginal.size()).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imagPagerUtil = new ImagPagerUtil(MyRecordDetailActivity.this, mList, mmList, finalPosition % mList.size() + 1);
                    imagPagerUtil.show();
                }
            });
            position %= imageList_orginal.size();

            if (position < 0) {
                position = imageList_orginal.size() + position;
            }
            view = imageList_orginal.get(position);
            ViewParent vp = view.getParent();
            if (vp != null) {
                ViewGroup parent = (ViewGroup) vp;
                parent.removeView(view);
            }
            container.addView(view, 0);
            return view;
        }
    }


    /**
     * 获取我的订单详情
     */
    public void getMyOrderDetail() {
        mDialog = new YWLoadingDialog(this);
        mDialog.show();
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            JSONObject jsobj2 = new JSONObject();
            try {
                jsobj2.put("id", id);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("parameter", jsobj2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.AppUsergetBillRecordDetails(httpUtils, jsobj1, this, UrlConfig.CAR_BILL_RECORD_DETAIL_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    /**
     * 收费标准
     */
    public void getChargeStandard() {
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                JSONObject jsobj2 = new JSONObject();
                jsobj2.put("id", djlx);

                jsobj1.put("parameter", jsobj2);
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.CCsf_GUIze(httpUtils, jsobj1, this, UrlConfig.CCsf_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    /**
     * 生成订单
     */
    public void GenerateOrder() {
        mDialog = new YWLoadingDialog(this);
        mDialog.show();
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            JSONObject jsobj2 = new JSONObject();
            try {
                jsobj2.put("id", id);
                jsobj2.put("sjly", sjly);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("parameter", jsobj2);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.AppUserGenerateOrder(httpUtils, jsobj1, this, UrlConfig.GENERATE_ORDER_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");

        }
    }

    /**
     * 需要帮助  调用接口
     */
    private void postNeedHelpData() {
        mDialog = new YWLoadingDialog(this);
        mDialog.show();
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            JSONObject jsobj2 = new JSONObject();
            try {
                jsobj2.put("tcjlId", id);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("parameter", jsobj2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.postNeedHelpData(httpUtils, jsobj1, this, UrlConfig.NEED_HELP_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    @Override
    public void setLisener() {
        iv_back_parking_detail.setOnClickListener(this);
        tv_Share.setOnClickListener(this);
        tv_shoufeibiaozhun.setOnClickListener(this);
        btn_zhifu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back_parking_detail:
                finish();
                break;
            case R.id.tv_Share:
                postNeedHelpData();
                break;
            case R.id.tv_shoufeibiaozhun:
                getChargeStandard();
                break;
            case R.id.btn_zhifu:
                animation = AnimationUtils.loadAnimation(MyRecordDetailActivity.this, R.anim.loading);
                LinearInterpolator interpolator = new LinearInterpolator();
                animation.setInterpolator(interpolator);
                iv_Pay_Rotate.startAnimation(animation);
                iv_Pay_Rotate.setVisibility(View.VISIBLE);
                btn_zhifu.setText("");
                btn_zhifu.setEnabled(false);
                GenerateOrder();
//                Intent intent = new Intent(this, OrderPayActivity.class);
//                intent.putExtra("ccmc", ccmc);
//                intent.putExtra("id", id);
//                intent.putExtra("sjly", sjly);
//                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onFailureHttp(HttpException error, String msg, int resultCode) {
        super.onFailureHttp(error, msg);
        LogUtils.d("缴费失败");
        mDialog.dismiss();
    }

    @Override
    public void onSuccessHttp(String responseInfo, int resultCode) {
        super.onSuccessHttp(responseInfo, resultCode);
        switch (resultCode) {
            case UrlConfig.CAR_BILL_RECORD_DETAIL_CODE:
                mDialog.dismiss();
                LogUtils.d("获取我的订单详情：" + responseInfo);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String result = obj.optString("result");
                    if (code == 0) {
                        JSONObject obj1 = obj.optJSONObject("result");
                        djlx = obj1.optString("djlx");
                        ccmc = obj1.optString("ccmc");
                        hphm = obj1.optString("hphm");
                        tcsc = obj1.optString("tcsc");
                        tv_CarNum.setText(hphm);
                        tv_ccdz.setText(ccmc);
                        tv_cwbh.setText(obj1.optString("cwbh"));
                        tv_rwsj.setText(obj1.optString("rwsj"));
                        tv_tcsc.setText(tcsc);
                        String lwsj = obj1.optString("lwsj");
                        double money = (double) Integer.parseInt(obj1.optString("qkje")) / 100;
                        zfje_money = (double) Integer.parseInt(obj1.optString("zfje")) / 100;
                        if (sjly.equals("0")) { //未离场 未支付
                            rl_lwsj.setVisibility(View.GONE);
                            if (money == 0) {
                                btn_zhifu.setVisibility(View.VISIBLE);
                                btn_zhifu.setBackgroundResource(R.mipmap.bg_hui_zhifu);
                                btn_zhifu.setEnabled(false);
                            }
                            btn_zhifu.setVisibility(View.VISIBLE);
                            tv_tishiyu.setText("为节省您的宝贵时间，您可先支付后离场");
                        } else if (sjly.equals("2")) {  //未离场 已支付
                            tv_liweitime.setText("支付时间");
                            tv_lwsj.setText("" + lwsj);
                            rl_lwsj.setVisibility(View.VISIBLE);
                            String zfqd = obj1.optString("zfqd");
                            rl_yizhifu.setVisibility(View.VISIBLE);
                            String zfje = String.format("%.2f", zfje_money);
                            tv_zfje.setText("￥" + zfje);
                            tv_zffs.setText(zfqd);
                            if (zfqd.equals("微信支付")) {
                                iv_havePay.setImageResource(R.mipmap.have_pay_wechat);
                            } else if (zfqd.equals("钱包支付")) {
                                iv_havePay.setImageResource(R.mipmap.have_pay_qianbao);
                            } else if (zfqd.equals("支付宝支付")) {
                                iv_havePay.setImageResource(R.mipmap.have_pay_alipay);
                            }
                            btn_zhifu.setVisibility(View.INVISIBLE);
                            tv_tishiyu.setText("您已完成支付，如已离场，请勿理会");
                        } else if (sjly.equals("3")) {//已离场 未支付
                            rl_lwsj.setVisibility(View.VISIBLE);
                            tv_lwsj.setText("" + lwsj);
                            if (zfje_money > 0) {
                                String zfqd = obj1.optString("zfqd");
                                rl_yizhifu.setVisibility(View.VISIBLE);
                                String zfje = String.format("%.2f", zfje_money);
                                tv_zfje.setText("￥" + zfje);
                                tv_zffs.setText(zfqd);
                                if (zfqd.equals("微信支付")) {
                                    iv_havePay.setImageResource(R.mipmap.have_pay_wechat);
                                } else if (zfqd.equals("钱包支付")) {
                                    iv_havePay.setImageResource(R.mipmap.have_pay_qianbao);
                                } else if (zfqd.equals("支付宝支付")) {
                                    iv_havePay.setImageResource(R.mipmap.have_pay_alipay);
                                }
                            }
                            btn_zhifu.setVisibility(View.VISIBLE);
                            tv_tishiyu.setText("为了避免影响下次停车服务，请您立即支付停车费用");
                        }
                        double zje_money = (double) Integer.parseInt(obj1.optString("zje")) / 100;
                        zje = String.format("%.2f", zje_money);
                        tv_zje.setText("￥" + zje);

                        qkje = String.format("%.2f", money);
                        tv_qkje.setText("￥" + qkje);
                        if (obj1.has("lwtp1Path")) {
                            mList.add(obj1.optString("lwtp1Path"));

                            Map<String, String> map3 = new HashMap<>();
                            map3.put("lwtp1Path", obj1.optString("lwtp1Path"));
                            mmList.add(map3);
                        }
                        if (obj1.has("lwtp2Path")) {
                            mList.add(obj1.optString("lwtp2Path"));

                            Map<String, String> map4 = new HashMap<>();
                            map4.put("lwtp2Path", obj1.optString("lwtp2Path"));
                            mmList.add(map4);
                        }

                        if (obj1.has("rwtp1Path")) {
                            mList.add(obj1.optString("rwtp1Path"));
                            Map<String, String> map1 = new HashMap<>();
                            map1.put("rwtp1Path", obj1.optString("rwtp1Path"));
                            mmList.add(map1);
                        }
                        if (obj1.has("rwtp2Path")) {
                            mList.add(obj1.optString("rwtp2Path"));

                            Map<String, String> map2 = new HashMap<>();
                            map2.put("rwtp2Path", obj1.optString("rwtp2Path"));
                            mmList.add(map2);
                        }

                        if (mList.size() == 2) {
                            imageUrl_List.add(mList.get(0));
                            imageUrl_List.add(mList.get(1));
                            imageUrl_List.add(mList.get(0));
                            imageUrl_List.add(mList.get(1));
                        } else {
                            imageUrl_List = mList;
                        }

                        for (int i = 0; i < imageUrl_List.size(); i++) {
                            ImageView imageView = new ImageView(this);
                            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            bitmapUtils.display(imageView, imageUrl_List.get(i));
                            imageList_orginal.add(imageView);
                        }

                        dots = new ImageView[mList.size()];
                        for (int i = 0; i < dots.length; i++) {
                            ImageView imageView = new ImageView(this);
                            imageView.setLayoutParams(new ViewGroup.LayoutParams(8, 8));
                            dots[i] = imageView;
                            if (i == 0) {
                                dots[i].setBackgroundResource(R.drawable.dot_detail_focus);
                                Set set = mmList.get(i).keySet();
                                Iterator iter = set.iterator();
                                String key = null;
                                while (iter.hasNext()) {
                                    key = (String) iter.next();
                                }
                                if (key.equals("rwtp1Path") || key.equals("rwtp2Path")) {
                                    tv_rworlw.setText("入场图片");
                                } else if (key.equals("lwtp1Path") || key.equals("lwtp2Path")) {
                                    tv_rworlw.setText("离场图片");
                                }
                            } else {
                                dots[i].setBackgroundResource(R.drawable.dot_detail_normal);
                            }

                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT));
                            layoutParams.leftMargin = 5;
                            layoutParams.rightMargin = 5;
                            ll_group.addView(imageView, layoutParams);
                        }

                        adapter = new ViewPagerAdapter();
                        viewPager.setAdapter(adapter);
//                        adapter.notifyDataSetChanged();
//                        viewPager.setCurrentItem((mList.size()) * 100);
                        if (obj1.optString("zflx").equals("5")){ //支付类型 1充值 2消费 3退款 4赠送 5包期
                            iv_yueka.setVisibility(View.VISIBLE);
                            btn_zhifu.setVisibility(View.GONE);
                            tv_qkje.setText("￥0.00");
                            tv_tishiyu.setText("邯郸泊车，为您尽享智能贴心停车服务");
                        }else{
                            iv_yueka.setVisibility(View.GONE);
                        }
                    } else if (code == 1001) { //版本更新 弹框
                        JSONObject obj1 = obj.optJSONObject("result");
                        int newversionNo = Integer.parseInt(obj1.optString("versionNo"));
                        if (newversionNo > JieKouDiaoYongUtils.getVersionCode(this)) {
                            UpdateManager mUpdateManager = new UpdateManager(this);
                            mUpdateManager.showNoticeDialog(obj1.optString("versionPath"), newversionNo, obj1.optString("versionDescription"));
                        }
                    } else if (code == 1002) { //退出登录 弹框
                        JieKouDiaoYongUtils.loginTanKuan(this);
                    } else {
                        ToastUtil.makeShortText(this, result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case UrlConfig.CCsf_CODE:
                LogUtils.d("获取收费标准成功：" + responseInfo);
//                dialog.dismiss();
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String result = obj.optString("result");
                    if (code == 0) {
                        JSONObject obj1 = obj.optJSONObject("result");
                        int sflx = obj1.optInt("sflx");
                        final AlertDialog dialo = new AlertDialog.Builder(this).create();
                        dialo.show();
                        dialo.getWindow().setContentView(R.layout.guize_dialog);
                        RelativeLayout rl = (RelativeLayout) dialo.getWindow().findViewById(R.id.rl_guize);
                        TextView btn_know = (TextView) dialo.getWindow().findViewById(R.id.tv_bt);
                        TextView wsn_know = (TextView) dialo.getWindow().findViewById(R.id.tv_ws);
                        TextView zhu = (TextView) dialo.getWindow().findViewById(R.id.tv_zhu);
                        rl.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialo.dismiss();
                            }
                        });
                        if (sflx == 3) {
                            double money = (double) obj1.optInt("btdwjg") / 100;
                            wsn_know.setText("按次收费：" + String.format("%.2f", money) + "元/次");
                        } else if (sflx == 2) {
                            double money = (double) obj1.optInt("btdwjg") / 100;
                            wsn_know.setText("全天：" + String.format("%.2f", money) + "元/" + obj1.optString("btdwsc") + "分钟");
                            zhu.setText("注：" + obj1.optString("bz"));
                        } else if (sflx == 1) {
                            double money1 = (double) obj1.optInt("btdwjg") / 100;
                            double money2 = (double) obj1.optInt("wsdwjg") / 100;
                            btn_know.setText("日间（" + obj1.optString("btsjd") + "） " + String.format("%.2f", money1) + "元/" + obj1.optString("btdwsc") + "分钟");
                            wsn_know.setText("夜间（" + obj1.optString("wssjd") + "） " + String.format("%.2f", money2) + "元/" + obj1.optString("wsdwsc") + "分钟");
                            zhu.setText("注：" + obj1.optString("bz"));
                        }
                    } else if (code == 1001) { //版本更新 弹框
                        JSONObject obj1 = obj.optJSONObject("result");
                        int newversionNo = Integer.parseInt(obj1.optString("versionNo"));
                        if (newversionNo > JieKouDiaoYongUtils.getVersionCode(this)) {
                            UpdateManager mUpdateManager = new UpdateManager(this);
                            mUpdateManager.showNoticeDialog(obj1.optString("versionPath"), newversionNo, obj1.optString("versionDescription"));
                        }
                    } else if (code == 1002) { //退出登录 弹框
                        JieKouDiaoYongUtils.loginTanKuan(this);
                    } else {
                        ToastUtil.makeShortText(this, result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case UrlConfig.GENERATE_ORDER_CODE:
                LogUtils.d("生成订单成功：" + responseInfo);
                mDialog.dismiss();
                iv_Pay_Rotate.clearAnimation();
                iv_Pay_Rotate.setVisibility(View.GONE);
                btn_zhifu.setText("确认支付");
                btn_zhifu.setEnabled(true);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String result = obj.optString("result");
                    if (code == 8003) {
                        ToastUtil.makeShortText(this, "账单已支付");
                    } else if (code == 0) {
                        JSONObject obj1 = obj.optJSONObject("result");
                        String result_list = obj.optString("result_list");
                        ArrayList<CouponReceive> list = AnsynHttpRequest.parser.fromJson(result_list, new TypeToken<List<CouponReceive>>() {
                        }.getType());
                        Intent intent = new Intent(MyRecordDetailActivity.this, OrderPayActivity.class);
                        intent.putExtra("hphm", obj1.optString("hphm"));
                        intent.putExtra("lsh", obj1.optString("lsh"));
                        intent.putExtra("qkje", obj1.optString("qkje"));
                        intent.putExtra("ccmc", ccmc);
                        intent.putExtra("tcsc", obj1.optString("tcsc"));
                        intent.putExtra("zfje", obj1.optString("zfje"));
                        intent.putExtra("zje", obj1.optString("zje"));
                        if (list.size() > 0) {
                            intent.putExtra("CouponList", list);
                        }
                        startActivity(intent);
                    } else if (code == 1001) { //版本更新 弹框
                        JSONObject obj1 = obj.optJSONObject("result");
                        int newversionNo = Integer.parseInt(obj1.optString("versionNo"));
                        if (newversionNo > JieKouDiaoYongUtils.getVersionCode(this)) {
                            UpdateManager mUpdateManager = new UpdateManager(this);
                            mUpdateManager.showNoticeDialog(obj1.optString("versionPath"), newversionNo, obj1.optString("versionDescription"));
                        }
                    } else if (code == 1002) { //退出登录 弹框
                        JieKouDiaoYongUtils.loginTanKuan(this);
                    } else {
                        if (!StringUtil.isEmpty(result)) {
                            ToastUtil.makeShortText(this, result);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case UrlConfig.NEED_HELP_CODE:
                LogUtils.d("点击需要帮助成功：" + responseInfo);
                mDialog.dismiss();
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String result = obj.optString("result");
                    String message = obj.optString("message");
                    if (code == 0) {
                        Intent intent = new Intent(this, NeedHelpActivity.class);
                        intent.putExtra("jlid", id);
                        startActivity(intent);
                    } else if (code == 1001) { //版本更新 弹框
                        JSONObject obj1 = obj.optJSONObject("result");
                        int newversionNo = Integer.parseInt(obj1.optString("versionNo"));
                        if (newversionNo > JieKouDiaoYongUtils.getVersionCode(this)) {
                            UpdateManager mUpdateManager = new UpdateManager(this);
                            mUpdateManager.showNoticeDialog(obj1.optString("versionPath"), newversionNo, obj1.optString("versionDescription"));
                        }
                    } else if (code == 1002) { //退出登录 弹框
                        JieKouDiaoYongUtils.loginTanKuan(this);
                    } else {
                        if (!StringUtil.isEmpty(message)) {
                            ToastUtil.makeShortText(this, message);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
        }
        if (imagPagerUtil != null) {
            imagPagerUtil.stop();
        }
        imageList_orginal.clear();
        mList.clear();
        imageUrl_List.clear();
        mmList.clear();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true)
    public void onEvent(FirstEvent event) {
        String msg = "onEventMainThread收到了消息：" + event.getMsg();
        LogUtils.d("msg is :"+msg);
        if (event.getMsg().equals("monthCard")) {
            showAliDialog();//月卡过期弹框
            getMyOrderDetail();
        }
    }

    private void showAliDialog() {
        final Dialog dialo = new Dialog(MyRecordDetailActivity.this, R.style.Dialog);
        dialo.setCanceledOnTouchOutside(true);//设置外部不可点击
        dialo.setCancelable(true);
        dialo.setContentView(R.layout.mobile_dialog);
        TextView tv = (TextView) dialo.findViewById(R.id.mobile_tv);
        tv.setText("月卡已过期，需支付停车费用");
        Button btn_know = (Button) dialo.findViewById(R.id.btn_know);
        btn_know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialo.dismiss();
            }
        });
        dialo.show();
    }
}
