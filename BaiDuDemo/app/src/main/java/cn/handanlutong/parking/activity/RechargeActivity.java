package cn.handanlutong.parking.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.adapter.GridViewAdapter;
import cn.handanlutong.parking.bean.CzfxListBean;
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
import cn.handanlutong.parking.zoom.ExpandableGridView;

/**
 * 钱包充值
 * Created by zhangyonggang on 2017/4/10.
 */

public class RechargeActivity extends BaseActivity implements View.OnClickListener {


    private static final int SDK_PAY_FLAG = 1;

    private ImageView iv_back_Recharge;
    private CheckBox cb_WeChat_suer, cb_AliPay_suer;
    private ExpandableGridView gv_Recharge;
    private List<CzfxListBean.DataBean> mRecharges;
    private GridViewAdapter adapter;
    private TextView tv_protocol, tv_recharge_guize;
    private Button btn_Recharge1;
    private RelativeLayout rl_WeChat, rl_AliPay;
    private EditText et_Recharge_Money;
    private SharedPreferenceUtil spUtil;
    private IWXAPI api;
    private String czje = "";
    private int id;

    @Override
    public void initView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//防止弹出键盘时，底部的组件上移而屏幕不上移
        setContentView(R.layout.activity_recharge);
        UrlConfig.type = 2;
        spUtil = SharedPreferenceUtil.init(this, SharedPreferenceUtil.LOGIN_INFO, Activity.MODE_PRIVATE);
        id = spUtil.getInt("id");
        iv_back_Recharge = (ImageView) findViewById(R.id.iv_back_Recharge);
        et_Recharge_Money = (EditText) findViewById(R.id.et_Recharge_Money);
        et_Recharge_Money.setOnFocusChangeListener(onFocusChangeListener);
        cb_WeChat_suer = (CheckBox) findViewById(R.id.cb_WeChat_suer);
        cb_AliPay_suer = (CheckBox) findViewById(R.id.cb_AliPay_suer);
        btn_Recharge1 = (Button) findViewById(R.id.btn_Recharge1);
        tv_protocol = (TextView) findViewById(R.id.tv_protocol);
        tv_recharge_guize = (TextView) findViewById(R.id.tv_recharge_guize);
        rl_WeChat = (RelativeLayout) findViewById(R.id.rl_WeChat);
        rl_AliPay = (RelativeLayout) findViewById(R.id.rl_AliPay);
        gv_Recharge = (ExpandableGridView) findViewById(R.id.gv_Recharge);
        gv_Recharge.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelectedPosition(position);
                adapter.notifyDataSetChanged();
                czje = String.valueOf(mRecharges.get(position).getCzje() / 100);
                et_Recharge_Money.setText("");
                et_Recharge_Money.setHint("请输入至少1元");
                et_Recharge_Money.setFocusable(false);
            }
        });
        et_Recharge_Money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                et_Recharge_Money.setCursorVisible(true);
                et_Recharge_Money.setHint("");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                et_Recharge_Money.setHint("");
                et_Recharge_Money.setCursorVisible(true);
                String str = et_Recharge_Money.getText().toString().trim();
                if (str.indexOf('0') == 0) {
                    return;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();
                int len = editable.toString().length();
                if (len == 1 && text.equals("0")) {
                    editable.clear();
                }
//                type_price = arg0.toString();
                if (editable.length() == 3) {
                    hideSoftInput(et_Recharge_Money);
                }
            }
        });
        getReChargeList();
    }

    @Override
    public void setLisener() {
        iv_back_Recharge.setOnClickListener(this);
        tv_protocol.setOnClickListener(this);
        btn_Recharge1.setOnClickListener(new JieKouDiaoYongUtils.NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (StringUtil.isEmpty(czje)) {
                    if (StringUtil.isEmpty(et_Recharge_Money.getText().toString())) {
                        ToastUtil.makeShortText(RechargeActivity.this, "请输入充值金额");
                        return;
                    } else {
                        czje = et_Recharge_Money.getText().toString();
                    }
                }
                LogUtils.d("czje  is:" + czje);

                if (cb_WeChat_suer.isChecked()) {
                    if (!isWeixinAvilible(RechargeActivity.this)) {
                        ToastUtil.makeShortText(RechargeActivity.this, "请先安装微信或者选择其他支付方式");
                        return;
                    } else {
                        WechatPaySubmit(Double.parseDouble(czje) * 100);
                    }
                }

                if (cb_AliPay_suer.isChecked()) {
                    AliPaySubmit(Double.parseDouble(czje) * 100);
                }
            }
        });
        rl_WeChat.setOnClickListener(this);
        rl_AliPay.setOnClickListener(this);
        et_Recharge_Money.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back_Recharge:
                this.finish();
                break;
            case R.id.btn_Recharge1:

                break;
            case R.id.tv_protocol:
                Intent intent_ReCharge_Protocol = new Intent(this, UserProtocolActivity.class);
                intent_ReCharge_Protocol.putExtra("H5_url", "file:///android_asset/agreement_cz.html");
                intent_ReCharge_Protocol.putExtra("tv_title", "充值协议");
                intent_ReCharge_Protocol.putExtra("hdgqsj", "false");
                startActivity(intent_ReCharge_Protocol);
                break;
            case R.id.et_Recharge_Money:
                czje = "";
                et_Recharge_Money.setFocusable(true);
                et_Recharge_Money.setFocusableInTouchMode(true);
                et_Recharge_Money.requestFocus();
                adapter.setSelectedPosition(-1);
                adapter.notifyDataSetChanged();
                break;
            case R.id.rl_WeChat:
                if (cb_AliPay_suer.isChecked()) {
                    cb_AliPay_suer.setChecked(false);
                }
                if (!cb_WeChat_suer.isChecked()) {
                    cb_WeChat_suer.setChecked(true);
                }
                cb_WeChat_suer.setBackgroundResource(R.mipmap.xuanzhongzhifufangshi);
                cb_AliPay_suer.setBackgroundResource(R.drawable.bg_oval);
                break;
            case R.id.rl_AliPay:
                if (cb_WeChat_suer.isChecked()) {
                    cb_WeChat_suer.setChecked(false);
                }
                if (!cb_AliPay_suer.isChecked()) {
                    cb_AliPay_suer.setChecked(true);
                }
                cb_WeChat_suer.setBackgroundResource(R.drawable.bg_oval);
                cb_AliPay_suer.setBackgroundResource(R.mipmap.xuanzhongzhifufangshi);
                break;
            default:
                break;
        }
    }

    /**
     * 获取充值返现规则
     */
    private void postGuiZeData() {
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
                jsobj1.put("appType", UrlConfig.android_type);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.getGuiZeData(httpUtils, jsobj1, this, UrlConfig.GET_GUI_ZE_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    /**
     * 获取充值赠送规则
     */
    private void postGuiZeData2() {
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
                jsobj1.put("appType", UrlConfig.android_type);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.getGuiZeData2(httpUtils, jsobj1, this, UrlConfig.GET_GUI_ZE_CZZS_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    /**
     * 微信支付
     *
     * @param money
     */
    private void WechatPaySubmit(double money) {
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                JSONObject jsobj2 = new JSONObject();
                jsobj2.put("zje", money);

                jsobj1.put("parameter", jsobj2);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
                jsobj1.put("appType", UrlConfig.android_type);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.AppUserWeChatPayReCharge(httpUtils, jsobj1, this, UrlConfig.MYWALLET_WECHATPAY_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    /**
     * 获取充值列表
     */
    public void getReChargeList() {
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
                jsobj1.put("appType", UrlConfig.android_type);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.AppUserBalancesReChargeList(httpUtils, jsobj1, this, UrlConfig.APPBALANCE_GETRECHARGE_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    /**
     * 支付宝支付
     *
     * @param money
     * @param money
     */
    public void AliPaySubmit(double money) {
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                JSONObject jsobj2 = new JSONObject();
                jsobj2.put("zje", money);

                jsobj1.put("parameter", jsobj2);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
                jsobj1.put("appType", UrlConfig.android_type);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.AppUserAliPayReCharge(httpUtils, jsobj1, this, UrlConfig.MYWALLET_ALIPAY_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }

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
                PayTask alipay = new PayTask(RechargeActivity.this);
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
                        ToastUtil.makeShortText(RechargeActivity.this, "支付成功");
                        RechargeActivity.this.finish();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        ToastUtil.makeShortText(RechargeActivity.this, "支付失败");
                    }
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 触摸屏幕其它地方隐藏软键盘
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (RechargeActivity.this.getCurrentFocus() != null) {
                if (RechargeActivity.this.getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(RechargeActivity.this.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 焦点变化事件监听
     */
    public View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            EditText editText = (EditText) v;
            if (!hasFocus) {// 失去焦点
                editText.setHint(editText.getTag().toString());
            } else {
                String hint = editText.getHint().toString();
                editText.setTag(hint);
                editText.setHint("");
            }
        }
    };


    // 隐藏键盘
    protected void hideSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.HIDE_NOT_ALWAYS);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); // 强制隐藏键盘
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

    @Override
    public void onSuccessHttp(String responseInfo, int resultCode) {
        super.onSuccessHttp(responseInfo, resultCode);
        switch (resultCode) {
            case UrlConfig.MYWALLET_ALIPAY_CODE:
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
            case UrlConfig.MYWALLET_WECHATPAY_CODE:
                LogUtils.d("微信充值成功：" + responseInfo);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String result = obj.optString("result");
                    if (code == 0) {
                        JSONObject obj1 = obj.optJSONObject("result");
                        UrlConfig.type = 2;
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
                    } else {
                        if (!StringUtil.isEmpty(result)) {
                            ToastUtil.makeShortText(this, result);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case UrlConfig.APPBALANCE_GETRECHARGE_CODE:
                LogUtils.d("获取充值返现列表成功：" + responseInfo);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    int type = obj.optInt("type");
                    String message = obj.optString("message");
                    if (code == 0) {
                        Gson gson = new Gson();
                        CzfxListBean czfxlistbean = gson.fromJson(responseInfo, CzfxListBean.class);
                        mRecharges = czfxlistbean.getResult();
                        adapter = new GridViewAdapter(this, mRecharges);
                        gv_Recharge.setAdapter(adapter);
                        adapter.setSelectedPosition(2);
                        czje = String.valueOf(mRecharges.get(2).getCzje() / 100);
                        if (type == 1) { //无活动时候
                            tv_recharge_guize.setVisibility(View.GONE);
                        } else if (type == 2) { //充返规则
                            tv_recharge_guize.setVisibility(View.VISIBLE);
                            tv_recharge_guize.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    postGuiZeData();
                                }
                            });
                        } else if (type == 3) { //赠送规则
                            tv_recharge_guize.setVisibility(View.VISIBLE);
                            tv_recharge_guize.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    postGuiZeData2();
                                }
                            });
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
                        ToastUtil.makeShortText(this, message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case UrlConfig.GET_GUI_ZE_CODE:
                LogUtils.d("获取充值返现列表的规则：" + responseInfo);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String result = obj.optString("result");
                    String message = obj.optString("message");
                    if (code == 0) {
                        Intent intent = new Intent(this, UserProtocolActivity.class);
                        intent.putExtra("H5_url", result);
                        intent.putExtra("tv_title", "活动规则");
                        intent.putExtra("hdgqsj", "false");
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
                        ToastUtil.makeShortText(this, message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case UrlConfig.GET_GUI_ZE_CZZS_CODE:
                LogUtils.d("获取充值返现列表的充值赠送规则：" + responseInfo);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String result = obj.optString("result");
                    String message = obj.optString("message");
                    if (code == 0) {
                        Intent intent = new Intent(this, UserProtocolActivity.class);
                        intent.putExtra("H5_url", result);
                        intent.putExtra("tv_title", "活动规则");
                        intent.putExtra("hdgqsj", "false");
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
                        ToastUtil.makeShortText(this, message);
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
        LogUtils.d("充值失败！");
    }

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
