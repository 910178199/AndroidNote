package cn.handanlutong.parking.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.adapter.LVxzyhqAdapter;
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
import cn.handanlutong.parking.utils.alipayutils.PayResult;

/**
 * 订单支付页
 * Created by zhangyonggang on 2017/11/23.
 */

public class OrderPayActivity extends BaseActivity implements View.OnClickListener {
    private static final int SDK_PAY_FLAG = 0x2;
    private ImageView iv_back_order_pay, iv_Pay_Rotate;
    private TextView tv_ParkingName, tv_CarNum, tv_tcsc, tv_zje, tv_havePay, tv_yizhifu, tv_yhq_je, tv_yhq_tishi;
    private String qkje, lsh, str_yhjid, new_bubian_je;
    private Button btn_goPay, btn_Pay_Sure;
    private RelativeLayout rl_youhuijuan;
    private PopupWindow pop = null;
    private View parentView;
    private RelativeLayout rl_popVindow, rl_popdismiss;
    private SharedPreferenceUtil spUtil;
    private CheckBox cb_QB_money_suer, cb_WeChat_suer, cb_AliPay_suer;
    private IWXAPI api;
    private Animation animation;
    public static OrderPayActivity instance;
    public static String TotalMoney;
    private YWLoadingDialog mDialog;

    @Override
    public void initView() {
        parentView = getLayoutInflater().inflate(R.layout.activity_order_pay, null);
        setContentView(parentView);
        instance = this;
        spUtil = SharedPreferenceUtil.init(this, SharedPreferenceUtil.LOGIN_INFO, Activity.MODE_PRIVATE);
        iv_back_order_pay = (ImageView) findViewById(R.id.iv_back_order_pay);
        tv_ParkingName = (TextView) findViewById(R.id.tv_ParkingName);
        tv_CarNum = (TextView) findViewById(R.id.tv_CarNum);
        tv_tcsc = (TextView) findViewById(R.id.tv_tcsc);
        tv_zje = (TextView) findViewById(R.id.tv_zje);
        tv_havePay = (TextView) findViewById(R.id.tv_havePay);
        tv_yizhifu = (TextView) findViewById(R.id.tv_yizhifu);
        tv_yhq_je = (TextView) findViewById(R.id.tv_yhq_je);
        tv_yhq_tishi = (TextView) findViewById(R.id.tv_yhq_tishi);
        btn_goPay = (Button) findViewById(R.id.btn_goPay);
        rl_youhuijuan = (RelativeLayout) findViewById(R.id.rl_youhuijuan);
        Intent intent = getIntent();
        lsh = intent.getStringExtra("lsh");
        tv_ParkingName.setText(intent.getStringExtra("ccmc"));
        tv_CarNum.setText(intent.getStringExtra("hphm"));
        tv_tcsc.setText(intent.getStringExtra("tcsc"));
        String zje = intent.getStringExtra("zje");
        double zje_money = (double) Integer.parseInt(zje) / 100;
        tv_zje.setText("￥" + String.format("%.2f", zje_money));
        String zfje = intent.getStringExtra("zfje");
        double zfje_money = (double) Integer.parseInt(zfje) / 100;
        if (zfje_money > 0) {
            tv_havePay.setText("￥-" + String.format("%.2f", zfje_money));
        }
        String qkje = intent.getStringExtra("qkje");
        final double money = (double) Integer.parseInt(qkje) / 100;

        final ArrayList<CouponReceive> list = (ArrayList<CouponReceive>) intent.getSerializableExtra("CouponList");
        if (list != null && list.size() > 0) {
            str_yhjid = list.get(0).getId() + "";
            tv_yhq_tishi.setText("（" + list.size() + "张优惠券可用）");
            tv_yhq_je.setText("￥-" + StringUtil.convertFentoYuanWithout(list.get(0).getYhjje()));
            double ysz = money - (double) list.get(0).getYhjje() / 100;
            if (ysz < 0) {
                ysz = 0;
            }
            new_bubian_je = String.format("%.2f", ysz);
            btn_goPay.setText("立即支付￥" + new_bubian_je);
            tv_yizhifu.setText(new_bubian_je);
            rl_youhuijuan.setEnabled(true);
            rl_youhuijuan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showTWOdialog(list, money);
                }
            });
        } else {
            tv_yhq_tishi.setText("（暂无可用优惠券）");
            rl_youhuijuan.setEnabled(false);
            str_yhjid = "";
            new_bubian_je = String.format("%.2f", money);
            btn_goPay.setText("立即支付￥" + new_bubian_je);
            tv_yizhifu.setText(new_bubian_je);
        }

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        GenerateOrder();
    }

    @Override
    public void setLisener() {
        iv_back_order_pay.setOnClickListener(this);
        btn_goPay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back_order_pay:
                this.finish();
                break;
            case R.id.rl_popdismiss:
                pop.dismiss();
                rl_popVindow.clearAnimation();
                break;
            case R.id.btn_goPay:
                showPayPopwindow(new_bubian_je);
                break;
            default:
                break;
        }
    }

    private void showPayPopwindow(String zje) {
        pop = new PopupWindow(OrderPayActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_gopay_layout, null);
        rl_popVindow = (RelativeLayout) view.findViewById(R.id.rl_popVindow);
        rl_popdismiss = (RelativeLayout) view.findViewById(R.id.rl_popdismiss);
        iv_Pay_Rotate = (ImageView) view.findViewById(R.id.iv_Pay_Rotate);
        rl_popdismiss.setOnClickListener(this);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView tv_money = (TextView) view.findViewById(R.id.tv_gopay_money);
        btn_Pay_Sure = (Button) view.findViewById(R.id.btn_Pay_Sure);
        RelativeLayout rl_qb_money = (RelativeLayout) view.findViewById(R.id.rl_QB_money);
        RelativeLayout rl_WeChat = (RelativeLayout) view.findViewById(R.id.rl_WeChat);
        RelativeLayout rl_AliPay = (RelativeLayout) view.findViewById(R.id.rl_AliPay);
        cb_QB_money_suer = (CheckBox) view.findViewById(R.id.cb_QB_money_suer);
        cb_WeChat_suer = (CheckBox) view.findViewById(R.id.cb_WeChat_suer);
        cb_AliPay_suer = (CheckBox) view.findViewById(R.id.cb_AliPay_suer);
        tv_money.setText(zje);
        btn_Pay_Sure.setText("立即支付￥" + zje);
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);
        rl_popVindow.startAnimation(AnimationUtils.loadAnimation(OrderPayActivity.this, R.anim.activity_translate_in));
        pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
        rl_qb_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cb_QB_money_suer.isChecked()) {
                    cb_QB_money_suer.setChecked(true);
                }
                if (cb_AliPay_suer.isChecked()) {
                    cb_AliPay_suer.setChecked(false);
                }
                if (cb_WeChat_suer.isChecked()) {
                    cb_WeChat_suer.setChecked(false);
                }
                cb_WeChat_suer.setBackgroundResource(R.drawable.bg_oval);
                cb_QB_money_suer.setBackgroundResource(R.mipmap.xuanzhongzhifufangshi);
                cb_AliPay_suer.setBackgroundResource(R.drawable.bg_oval);
            }
        });

        rl_WeChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_QB_money_suer.isChecked()) {
                    cb_QB_money_suer.setChecked(false);
                }
                if (cb_AliPay_suer.isChecked()) {
                    cb_AliPay_suer.setChecked(false);
                }
                if (!cb_WeChat_suer.isChecked()) {
                    cb_WeChat_suer.setChecked(true);
                }
                cb_QB_money_suer.setBackgroundResource(R.drawable.bg_oval);
                cb_WeChat_suer.setBackgroundResource(R.mipmap.xuanzhongzhifufangshi);
                cb_AliPay_suer.setBackgroundResource(R.drawable.bg_oval);
            }
        });

        rl_AliPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_QB_money_suer.isChecked()) {
                    cb_QB_money_suer.setChecked(false);
                }
                if (cb_WeChat_suer.isChecked()) {
                    cb_WeChat_suer.setChecked(false);
                }
                if (!cb_AliPay_suer.isChecked()) {
                    cb_AliPay_suer.setChecked(true);
                }
                cb_QB_money_suer.setBackgroundResource(R.drawable.bg_oval);
                cb_WeChat_suer.setBackgroundResource(R.drawable.bg_oval);
                cb_AliPay_suer.setBackgroundResource(R.mipmap.xuanzhongzhifufangshi);
            }
        });

        btn_Pay_Sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cb_WeChat_suer.isChecked()) {
                    if (!isWeixinAvilible(OrderPayActivity.this)) {
                        ToastUtil.makeShortText(OrderPayActivity.this, "请先安装微信或者选择其他支付方式");
                        return;
                    } else {
                        WechatPaySubmit();
                    }
                }
                if (cb_AliPay_suer.isChecked()) {
                    AliPaySubmit();
                }
                if (cb_QB_money_suer.isChecked()) {
                    singlePayconfig();
                }
                animation = AnimationUtils.loadAnimation(OrderPayActivity.this, R.anim.loading);
                LinearInterpolator interpolator = new LinearInterpolator();
                animation.setInterpolator(interpolator);
                iv_Pay_Rotate.startAnimation(animation);
                iv_Pay_Rotate.setVisibility(View.VISIBLE);
                btn_Pay_Sure.setText("");
                btn_Pay_Sure.setEnabled(false);
            }
        });
    }

    /**
     * 生成订单
     */
//    public void GenerateOrder() {
//        mDialog = new YWLoadingDialog(this);
//        mDialog.show();
//        if (NetWorkUtil.isNetworkConnected(this)) {
//            JSONObject jsobj1 = new JSONObject();
//            JSONObject jsobj2 = new JSONObject();
//            try {
//                jsobj2.put("id", id);
//                jsobj2.put("sjly", sjly);
//                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
//                jsobj1.put("authKey", spUtil.getString("authkey"));
//                jsobj1.put("appType", UrlConfig.android_type);
//                jsobj1.put("parameter", jsobj2);
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            HttpMethod.AppUserGenerateOrder(httpUtils, jsobj1, this, UrlConfig.GENERATE_ORDER_CODE);
//        } else {
//            iv_Pay_Rotate.clearAnimation();
//            iv_Pay_Rotate.setVisibility(View.GONE);
//            btn_Pay_Sure.setText("立即支付￥" + new_bubian_je);
//            btn_Pay_Sure.setEnabled(true);
//            ToastUtil.makeShortText(this, "请检查网络！");
//
//        }
//    }


    /**
     * 微信支付
     */
    private void WechatPaySubmit() {
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                JSONObject jsobj2 = new JSONObject();
                jsobj2.put("lsh", lsh);
                jsobj2.put("couponId", str_yhjid);

                jsobj1.put("parameter", jsobj2);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
                jsobj1.put("appType", UrlConfig.android_type);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.WXzhifuDd(httpUtils, jsobj1, this, UrlConfig.MYWALLET_WECHATPAY_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    /**
     * 支付宝支付
     */
    public void AliPaySubmit() {
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                JSONObject jsobj2 = new JSONObject();
                jsobj2.put("lsh", lsh);
                jsobj2.put("couponId", str_yhjid);

                jsobj1.put("parameter", jsobj2);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
                jsobj1.put("appType", UrlConfig.android_type);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.ZFBzhifuDd(httpUtils, jsobj1, this, UrlConfig.MYWALLET_ALIPAY_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    /**
     * 订单支付 (钱包支付)
     */
    public void singlePayconfig() {
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                JSONObject jsobj2 = new JSONObject();
                jsobj2.put("lsh", lsh);
                jsobj2.put("couponId", str_yhjid);

                jsobj1.put("parameter", jsobj2);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
                jsobj1.put("appType", UrlConfig.android_type);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.AppUserMyOrderPay(httpUtils, jsobj1, this, UrlConfig.MYORDER_PAY_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    /**
     * 检查微信是否存在
     *
     * @param context
     * @return
     */
    public boolean isWeixinAvilible(Context context) {
        PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                System.out.println(pinfo.get(i).packageName);
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 调支付宝进行支付
     *
     * @param orderInfo
     */
    private void AlipayV(final String orderInfo) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(OrderPayActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = getLayoutInflater().inflate(R.layout.dialog_myorder, null);
        dialog.show();
        dialog.setContentView(view);
        TextView tv_Cancle = (TextView) view.findViewById(R.id.tv_Cancle);
        TextView tv_Sure = (TextView) view.findViewById(R.id.tv_Sure);
        tv_Cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_Sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderPayActivity.this, RechargeActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onSuccessHttp(String responseInfo, int resultCode) {
        super.onSuccessHttp(responseInfo, resultCode);
        switch (resultCode) {
            case UrlConfig.GENERATE_ORDER_CODE:
                mDialog.dismiss();
                LogUtils.d("生成订单成功：" + responseInfo);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String result = obj.optString("result");
                    String result_list = obj.optString("result_list");
                    if (code == 0) {
                        JSONObject obj1 = obj.optJSONObject("result");
                        final List<CouponReceive> list = AnsynHttpRequest.parser.fromJson(result_list, new TypeToken<List<CouponReceive>>() {
                        }.getType());
                        lsh = obj1.optString("lsh");
                        qkje = obj1.optString("qkje");
                        String zje = obj1.optString("zje");
                        final double money = (double) Integer.parseInt(qkje) / 100;
                        final double zje_money = (double) Integer.parseInt(zje) / 100;
                        tv_CarNum.setText(obj1.optString("hphm"));
                        tv_tcsc.setText(obj1.optString("tcsc"));
                        tv_zje.setText("￥" + String.format("%.2f", zje_money));
                        String zfje = obj1.optString("zfje");
                        double zfje_money = (double) Integer.parseInt(zfje) / 100;
                        if (zfje_money > 0) {
                            tv_havePay.setText("￥-" + String.format("%.2f", zfje_money));
                        }
                        tv_yizhifu.setText(String.format("%.2f", money));
                        btn_goPay.setText("立即支付￥" + String.format("%.2f", money));
                        if (list.size() == 0) {
                            tv_yhq_tishi.setText("（暂无可用优惠券）");
                            rl_youhuijuan.setEnabled(false);
                            str_yhjid = "";
                            new_bubian_je = String.format("%.2f", money);
                            btn_goPay.setText("立即支付￥" + new_bubian_je);
                        } else {
                            str_yhjid = list.get(0).getId() + "";
                            tv_yhq_tishi.setText("（有" + list.size() + "张优惠券可用）");
                            tv_yhq_je.setText("￥-" + StringUtil.convertFentoYuanWithout(list.get(0).getYhjje()));
                            double ysz = money - (double) list.get(0).getYhjje() / 100;
                            if (ysz < 0) {
                                ysz = 0;
                            }
                            new_bubian_je = String.format("%.2f", ysz);
                            btn_goPay.setText("立即支付￥" + new_bubian_je);
                            rl_youhuijuan.setEnabled(true);
                        }
                        rl_youhuijuan.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showTWOdialog(list, money);
                            }
                        });
                    } else if (code == 8004) {
                        ToastUtil.makeShortText(this, "账单已支付");
                        iv_Pay_Rotate.clearAnimation();
                        iv_Pay_Rotate.setVisibility(View.GONE);
                        btn_Pay_Sure.setText("立即支付￥" + new_bubian_je);
                        btn_Pay_Sure.setEnabled(true);
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
                        if (StringUtil.isEmpty(result)) {
                            ToastUtil.makeShortText(this, result);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case UrlConfig.MYWALLET_WECHATPAY_CODE:
                LogUtils.d("微信支付成功：" + responseInfo);
                iv_Pay_Rotate.clearAnimation();
                iv_Pay_Rotate.setVisibility(View.GONE);
                btn_Pay_Sure.setText("立即支付￥" + new_bubian_je);
                btn_Pay_Sure.setEnabled(true);
                TotalMoney = new_bubian_je;
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String result = obj.optString("result");
                    if (code == 0) {
                        JSONObject obj1 = obj.optJSONObject("result");
                        UrlConfig.type = 1;
                        api = WXAPIFactory.createWXAPI(this, obj1.optString("appid"));
                        PayReq req = new PayReq();
                        req.appId = obj1.optString("appid");
                        req.partnerId = obj1.optString("partnerid");
                        req.prepayId = obj1.optString("prepayid");
                        req.nonceStr = obj1.optString("noncestr");
                        req.timeStamp = obj1.optString("timestamp");
                        req.packageValue = obj1.optString("wx_package");
                        req.sign = obj1.optString("sign");
                        api.registerApp(obj1.optString("appid"));
                        api.sendReq(req);
                    } else if (code == 1001) { //版本更新 弹框
                        JSONObject obj1 = obj.optJSONObject("result");
                        int newversionNo = Integer.parseInt(obj1.optString("versionNo"));
                        if (newversionNo > JieKouDiaoYongUtils.getVersionCode(this)) {
                            UpdateManager mUpdateManager = new UpdateManager(this);
                            mUpdateManager.showNoticeDialog(obj1.optString("versionPath"), newversionNo, obj1.optString("versionDescription"));
                        }
                    } else if (code == 1002) { //退出登录 弹框
                        JieKouDiaoYongUtils.loginTanKuan(this);
                    } else if (code == 6008) { //优惠金额大于订单总金额 支付金额为0，并且直接去支付成功页
                        ToastUtil.makeShortText(OrderPayActivity.this, "支付成功");
                        Intent intent = new Intent(OrderPayActivity.this, PayComplateActivity.class);
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                        String str = formatter.format(curDate);
                        intent.putExtra("TotalMoney", new_bubian_je);
                        intent.putExtra("PayTiem", str);//支付时间
                        intent.putExtra("PayType", "3");
                        startActivity(intent);
                    } else {
                        if (!StringUtil.isEmpty(result)) {
                            ToastUtil.makeShortText(this, result);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case UrlConfig.MYWALLET_ALIPAY_CODE:
                LogUtils.d("支付宝支付成功：" + responseInfo);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String result = obj.optString("result");
                    if (code == 0) {
                        AlipayV(result);
                    } else if (code == 1001) { //版本更新 弹框
                        JSONObject obj1 = obj.optJSONObject("result");
                        int newversionNo = Integer.parseInt(obj1.optString("versionNo"));
                        if (newversionNo > JieKouDiaoYongUtils.getVersionCode(this)) {
                            UpdateManager mUpdateManager = new UpdateManager(this);
                            mUpdateManager.showNoticeDialog(obj1.optString("versionPath"), newversionNo, obj1.optString("versionDescription"));
                        }
                    } else if (code == 1002) { //退出登录 弹框
                        JieKouDiaoYongUtils.loginTanKuan(this);
                    } else if (code == 6008) {
                        ToastUtil.makeShortText(OrderPayActivity.this, "支付成功");
                        Intent intent = new Intent(OrderPayActivity.this, PayComplateActivity.class);
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                        String str = formatter.format(curDate);
                        intent.putExtra("TotalMoney", new_bubian_je);
                        intent.putExtra("PayTiem", str);//支付时间
                        intent.putExtra("PayType", "3");
                        startActivity(intent);
                    } else {
                        if (!StringUtil.isEmpty(result)) {
                            ToastUtil.makeShortText(this, result);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case UrlConfig.MYORDER_PAY_CODE:
                LogUtils.d("钱包支付成功：" + responseInfo);
                iv_Pay_Rotate.clearAnimation();
                iv_Pay_Rotate.setVisibility(View.GONE);
                pop.dismiss();
                rl_popVindow.clearAnimation();
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String result = obj.optString("result");
                    if (code == 0) {
                        Intent intent = new Intent(this, PayComplateActivity.class);
                        intent.putExtra("TotalMoney", new_bubian_je);
                        intent.putExtra("PayTiem", obj.optString("result"));
                        intent.putExtra("PayType", "1");
                        startActivity(intent);
//                        this.finish();
                    } else if (code == 1001) { //版本更新 弹框
                        JSONObject obj1 = obj.optJSONObject("result");
                        int newversionNo = Integer.parseInt(obj1.optString("versionNo"));
                        if (newversionNo > JieKouDiaoYongUtils.getVersionCode(this)) {
                            UpdateManager mUpdateManager = new UpdateManager(this);
                            mUpdateManager.showNoticeDialog(obj1.optString("versionPath"), newversionNo, obj1.optString("versionDescription"));
                        }
                    } else if (code == 1002) { //退出登录 弹框
                        JieKouDiaoYongUtils.loginTanKuan(this);
                    } else if (code == 8005) {
                        showAlertDialog(); //钱包余额不足的弹框
                    } else if (code == 6008) {
                        ToastUtil.makeShortText(OrderPayActivity.this, "支付成功");
                        Intent intent = new Intent(OrderPayActivity.this, PayComplateActivity.class);
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                        String str = formatter.format(curDate);
                        intent.putExtra("TotalMoney", new_bubian_je);
                        intent.putExtra("PayTiem", str);//支付时间
                        intent.putExtra("PayType", "3");
                        startActivity(intent);
                    } else {
                        if (!StringUtil.isEmpty(result)) {
                            ToastUtil.makeShortText(this, result);
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
    public void onFailureHttp(HttpException error, String msg) {
        super.onFailureHttp(error, msg);
        mDialog.dismiss();
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    LogUtils.d("resultInfo is:" + resultInfo);
                    String resultStatus = payResult.getResultStatus();
                    LogUtils.d("resultStatus is:" + resultStatus);
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        ToastUtil.makeShortText(OrderPayActivity.this, "支付成功");
                        Intent intent = new Intent(OrderPayActivity.this, PayComplateActivity.class);
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                        String str = formatter.format(curDate);
                        intent.putExtra("TotalMoney", new_bubian_je);
                        intent.putExtra("PayTiem", str);//支付时间
                        intent.putExtra("PayType", "3");
                        startActivity(intent);
//                        OrderPayActivity.this.finish();
                    } else {
                        iv_Pay_Rotate.clearAnimation();
                        iv_Pay_Rotate.setVisibility(View.GONE);
                        btn_Pay_Sure.setText("立即支付￥" + new_bubian_je);
                        btn_Pay_Sure.setEnabled(true);
                        ToastUtil.makeShortText(OrderPayActivity.this, "支付失败");
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private void showTWOdialog(final List<CouponReceive> list, final double zje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = getLayoutInflater().inflate(R.layout.dialog_gopay_choiseyhq, null);
        dialog.show();

        dialog.setContentView(view);
        ImageView iv = (ImageView) view.findViewById(R.id.iv_dialog_close);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ListView lv_xzyhq = (ListView) view.findViewById(R.id.lv_dialog_xzyhq);
        lv_xzyhq.setAdapter(new LVxzyhqAdapter(this, list));
        lv_xzyhq.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();
                str_yhjid = list.get(position).getId() + "";
                int int_yhjje = list.get(position).getYhjje() / 100;
                double ysz = zje - (double) int_yhjje;
                if (ysz < 0) {
                    ysz = 0;
                }
                new_bubian_je = String.format("%.2f", ysz);
                tv_yizhifu.setText(new_bubian_je);
                btn_goPay.setText("立即支付￥" + new_bubian_je);
                tv_yhq_tishi.setText("（" + list.size() + "张优惠券可用）");
                tv_yhq_je.setText("￥-" + StringUtil.convertFentoYuanWithout(list.get(position).getYhjje()));
            }
        });
        Button btn_no = (Button) view.findViewById(R.id.btn_dialog_no);
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                tv_yhq_tishi.setText("（" + list.size() + "张优惠券可用）");
                tv_yhq_je.setText("￥0.00");
                str_yhjid = "";
                new_bubian_je = String.format("%.2f", zje);
                tv_yizhifu.setText(new_bubian_je);
                btn_goPay.setText("立即支付￥" + new_bubian_je);
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true)
    public void onEvent(FirstEvent event) {
        if (event.getMsg().equals("message0") && event.getCarPlace().getPay().equals("0")) {
            String ddje = event.getCarPlace().getDdje();
            double double_ddje = (double) Integer.parseInt(ddje) / 100;
            Intent intent = new Intent(OrderPayActivity.this, PayComplateActivity.class);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            String str = formatter.format(curDate);
            intent.putExtra("TotalMoney", String.format("%.2f", double_ddje));
            intent.putExtra("PayTiem", str);//支付时间
            intent.putExtra("PayType", "1");
            startActivity(intent);
        }
    }

}
