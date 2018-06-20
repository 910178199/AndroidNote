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
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.adapter.ChoiseMonthCardAdapter;
import cn.handanlutong.parking.adapter.ChoiseMyCarAdapter;
import cn.handanlutong.parking.adapter.MonthCardAdapter;
import cn.handanlutong.parking.adapter.MyCarAdapter;
import cn.handanlutong.parking.bean.LicensePlateCardVo;
import cn.handanlutong.parking.bean.MyCarBean;
import cn.handanlutong.parking.bean.ParkingCardVo;
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
 * Created by ww on 2017/9/8.
 */

public class BuyMonthCardActivity extends BaseActivity implements View.OnClickListener {
    private static final int SDK_PAY_FLAG = 1;
    private RelativeLayout back;
    private GridView gv_choise_month_card, gv_choise_month_card_hphm;
    private YWLoadingDialog mDialog;
    private SharedPreferenceUtil spUtil;
    private Button btn_goPay;
    private ChoiseMyCarAdapter car_adapter;
    private ChoiseMonthCardAdapter month_card_adapter;
    private List<MyCarBean.ResultBean> mycar_list;
    private List<ParkingCardVo> month_card_list;
    private Long month_card_id, mycar_id, carid;
    private Intent intent;
    public int carFlag;
    private Animation animation;
    private ImageView iv_yueka_Pay_Rotate;
    private Button btn;
    private TextView tv_monthCard_xieyi;
    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_buy_month_card);
        spUtil = SharedPreferenceUtil.init(this, SharedPreferenceUtil.LOGIN_INFO, Activity.MODE_PRIVATE);
        back = (RelativeLayout) findViewById(R.id.back);
        gv_choise_month_card = (GridView) findViewById(R.id.gv_choise_month_card);
        gv_choise_month_card_hphm = (GridView) findViewById(R.id.gv_choise_month_card_hphm);
        btn_goPay = (Button) findViewById(R.id.btn_goPay);
        tv_monthCard_xieyi = (TextView) findViewById(R.id.tv_monthCard_xieyi);
        postMonthCardAndMyCarData();//配置月卡查询
        getCarList();//车辆列表接口
        intent = getIntent();
        carid = intent.getLongExtra("carid", -1L);
        carFlag = intent.getIntExtra("carFlag", -1);
    }

    @Override
    public void setLisener() {
        super.setLisener();
        back.setOnClickListener(this);
        btn_goPay.setOnClickListener(this);
        tv_monthCard_xieyi.setOnClickListener(this);
        btn_goPay.setEnabled(false);
        gv_choise_month_card.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (month_card_list.get(position).getStatus().equals("1")){ //月卡非禁用状态 可以点击选择
                    month_card_adapter.setSelectedPosition(position);
                    month_card_id = month_card_list.get(position).getId();
                } else { //月卡禁用状态 不可以点击选择
                    month_card_id = -1L;
                    month_card_adapter.setSelectedPosition(-2);
                }
                month_card_adapter.notifyDataSetChanged();
            }
        });
        gv_choise_month_card_hphm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean openCard=false;
                if(carid==-1L||carid.equals(mycar_list.get(position).getId())){
                    if(mycar_list.get(position).getBdzt().equals("2")){ //已认证
                        if(mycar_list.get(position).getSfxf() == 1){//可以续费
                            openCard=true;
                        }else if(!mycar_list.get(position).getBqzt().equals("2") //包期状态 非使用中
                                &&mycar_list.get(position).getHpzt().equals("0")){ //号牌状态 空闲
                            openCard=true;
                        }
                    }
                }
                if (openCard) { //可以选择车辆
                    mycar_id = mycar_list.get(position).getId();
                    btn_goPay.setEnabled(true);
                    car_adapter.setSelectedPosition(position);
                    btn_goPay.setBackgroundResource(R.mipmap.btn_yuan);
                } else {
                    mycar_id = -1L;
                    btn_goPay.setEnabled(false);
                    car_adapter.isEnabled(position);
                    car_adapter.setSelectedPosition(-2);
                    btn_goPay.setBackgroundResource(R.mipmap.rec4);
                }
                car_adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btn_goPay:
                postBuyMonthCardData();
                break;
            case R.id.tv_monthCard_xieyi:
                Intent intent_ReCharge_Protocol = new Intent(this, UserProtocolActivity.class);
                intent_ReCharge_Protocol.putExtra("H5_url", "file:///android_asset/agreement_hdfw.html");
                intent_ReCharge_Protocol.putExtra("tv_title", "包月服务用户协议");
                intent_ReCharge_Protocol.putExtra("hdgqsj", "false");
                startActivity(intent_ReCharge_Protocol);
                break;
            default:
                break;
        }
    }

    /**
     * 接口请求
     */
    private void postMonthCardAndMyCarData() {
        mDialog = new YWLoadingDialog(this);
        mDialog.show();
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
            HttpMethod.postMonthCardAndMyCarData(httpUtils, jsobj1, this, UrlConfig.USER_Month_Card_And_Car_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    /**
     * 我的车辆列表
     */
    private void getCarList() {
        if (NetWorkUtil.isNetworkConnected(this)) {
            spUtil = SharedPreferenceUtil.init(this, SharedPreferenceUtil.LOGIN_INFO, MODE_PRIVATE);
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
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    /**
     * 点击购买月卡按钮 接口
     */
    private void postBuyMonthCardData() {
        mDialog = new YWLoadingDialog(this);
        mDialog.show();
        btn_goPay.setEnabled(false);
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                JSONObject jsobj2 = new JSONObject();
                jsobj2.put("cardId", month_card_id);
                jsobj2.put("carId", mycar_id);

                jsobj1.put("parameter", jsobj2);
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
                jsobj1.put("userId", spUtil.getInt("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.postMonthCardZhiFuData(httpUtils, jsobj1, this, UrlConfig.USER_Month_Card_Zhi_Fu_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    @Override
    public void onSuccessHttp(String responseInfo, int resultCode) {
        super.onSuccessHttp(responseInfo, resultCode);
        switch (resultCode) {
            case UrlConfig.USER_Month_Card_And_Car_CODE:
                mDialog.dismiss();
                LogUtils.d("配置月卡和车辆查询：" + responseInfo);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String result = obj.optString("result");
                    if (code == 0) {
                        month_card_list = AnsynHttpRequest.parser.fromJson(result, new TypeToken<List<ParkingCardVo>>(){}.getType());
                        month_card_adapter = new ChoiseMonthCardAdapter(this, month_card_list);
                        gv_choise_month_card.setAdapter(month_card_adapter);
                        month_card_adapter.setSelectedPosition(0);
                        month_card_id = month_card_list.get(0).getId();
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
            case UrlConfig.CAR_GETLIST_CODE:
                LogUtils.d("我的车辆列表：" + responseInfo);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String result = obj.optString("result");
                    if (code == 0) {
                        JSONArray array = obj.optJSONArray("result");
                        if (array.length() > 0) {
                            MyCarBean mycarbean = AnsynHttpRequest.parser.fromJson(responseInfo, MyCarBean.class);
                            mycar_list = mycarbean.getResult();
                            car_adapter = new ChoiseMyCarAdapter(this, mycar_list, carFlag);
                            gv_choise_month_card_hphm.setAdapter(car_adapter);
                            mycar_id = -1L;
                        } else {

                        }
                        if (carid > 0) {
                            mycar_id = carid;
                            for (int i = 0; i < mycar_list.size(); i++) {
                                if (mycar_id.equals(mycar_list.get(i).getId())) {
                                    car_adapter.setSelectedPosition(i);
                                    btn_goPay.setBackgroundResource(R.mipmap.btn_yuan);
                                    btn_goPay.setEnabled(true);
                                }
                            }
                        }
                    } else if (code == 3001) { //无车辆

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
            case UrlConfig.USER_Month_Card_Zhi_Fu_CODE:
                mDialog.dismiss();
                LogUtils.d("去购买月卡支付按钮：" + responseInfo);
                btn_goPay.setEnabled(true);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String result = obj.optString("result");
                    if (code == 0) {
                        showGoPayDialog();//去支付弹框
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
            case UrlConfig.USER_Month_Card_ZFB_CODE:
                LogUtils.d("支付宝充值成功：" + responseInfo);
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
                    } else {
                        if (!StringUtil.isEmpty(result)) {
                            ToastUtil.makeShortText(this, result);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
            case UrlConfig.USER_Month_Card_WX_CODE:
                LogUtils.d("微信充值成功：" + responseInfo);
                iv_yueka_Pay_Rotate.clearAnimation();
                iv_yueka_Pay_Rotate.setVisibility(View.GONE);
                btn.setText("立即购买");
                btn.setEnabled(true);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String result = obj.optString("result");
                    if (code == 0) {
                        JSONObject obj1 = obj.optJSONObject("result");
                        UrlConfig.type = 3;
                        IWXAPI api = WXAPIFactory.createWXAPI(this, obj1.optString("appid"));
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
        LogUtils.d("获取失败");
        mDialog.dismiss();
    }

    /**
     * 去支付 弹框
     */
    private void showGoPayDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = getLayoutInflater().inflate(R.layout.dialog_gopay_layout_month_card, null);
        dialog.show();
        dialog.setContentView(view);
        RelativeLayout rl_popdismiss = (RelativeLayout) view.findViewById(R.id.rl_popdismiss);
        RelativeLayout rl_qb_money = (RelativeLayout) view.findViewById(R.id.rl_QB_money);
        RelativeLayout rl_WeChat = (RelativeLayout) view.findViewById(R.id.rl_WeChat);
        RelativeLayout rl_AliPay = (RelativeLayout) view.findViewById(R.id.rl_AliPay);
        iv_yueka_Pay_Rotate=(ImageView) view.findViewById(R.id.iv_yueka_Pay_Rotate);
        final CheckBox cb_QB_money_suer = (CheckBox) view.findViewById(R.id.cb_QB_money_suer);
        final CheckBox cb_WeChat_suer = (CheckBox) view.findViewById(R.id.cb_WeChat_suer);
        final CheckBox cb_AliPay_suer = (CheckBox) view.findViewById(R.id.cb_AliPay_suer);
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
//        ImageView iv= (ImageView) view.findViewById(R.id.iv_dialog_close);
//        iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
        rl_popdismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btn = (Button) view.findViewById(R.id.btn_Pay_Sure);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_WeChat_suer.isChecked()) {
                    if (!isWeixinAvilible(BuyMonthCardActivity.this)) {
                        ToastUtil.makeShortText(BuyMonthCardActivity.this, "请先安装微信或者选择其他支付方式");
                        return;
                    } else {
                        WechatPaySubmit();
                    }
                }
                if (cb_AliPay_suer.isChecked()) {
                    AliPaySubmit();
                }
                animation = AnimationUtils.loadAnimation(BuyMonthCardActivity.this, R.anim.loading);
                LinearInterpolator interpolator = new LinearInterpolator();
                animation.setInterpolator(interpolator);
                iv_yueka_Pay_Rotate.startAnimation(animation);
                iv_yueka_Pay_Rotate.setVisibility(View.VISIBLE);
                btn.setText("");
                btn.setEnabled(false);
            }
        });
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
                PayTask alipay = new PayTask(BuyMonthCardActivity.this);
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

    /**
     * 支付宝支付
     */
    public void AliPaySubmit() {
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                JSONObject jsobj2 = new JSONObject();
                jsobj2.put("cardId", month_card_id);
                jsobj2.put("carId", mycar_id);

                jsobj1.put("parameter", jsobj2);
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
                jsobj1.put("userId", spUtil.getInt("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.ZFBzhifuMonthCard(httpUtils, jsobj1, this, UrlConfig.USER_Month_Card_ZFB_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    /**
     * 微信支付
     */
    private void WechatPaySubmit() {
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                JSONObject jsobj2 = new JSONObject();
                jsobj2.put("cardId", month_card_id);
                jsobj2.put("carId", mycar_id);

                jsobj1.put("parameter", jsobj2);
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
                jsobj1.put("userId", spUtil.getInt("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.WXzhifuMonthCard(httpUtils, jsobj1, this, UrlConfig.USER_Month_Card_WX_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    LogUtils.d("resultInfo is:" + resultInfo);
                    String resultStatus = payResult.getResultStatus();
                    LogUtils.d("resultStatus is:" + resultStatus);
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        ToastUtil.makeShortText(BuyMonthCardActivity.this, "支付成功");
                        Intent intent = new Intent(BuyMonthCardActivity.this, MonthCardActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        iv_yueka_Pay_Rotate.clearAnimation();
                        iv_yueka_Pay_Rotate.setVisibility(View.GONE);
                        btn.setText("立即购买");
                        btn.setEnabled(true);
                        ToastUtil.makeShortText(BuyMonthCardActivity.this, "支付失败");
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int code = intent.getIntExtra("result", 1);
        switch (code) {
            case 0://表示成功
//                Intent intent1 = new Intent(ReChargeActivity.this, MyWalletActivity.class);
//                startActivity(intent1);
                finish();
                break;
            case -1://表示失败
                break;
            case -2:////表示取消
                break;
            case 1://未知不做处理
                break;
            default:
                break;
        }
    }
}
