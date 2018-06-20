package cn.handanlutong.parking.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.bean.CouponReceive;
import cn.handanlutong.parking.customview.YWLoadingDialog;
import cn.handanlutong.parking.http.HttpMethod;
import cn.handanlutong.parking.http.UrlConfig;
import cn.handanlutong.parking.utils.JieKouDiaoYongUtils;
import cn.handanlutong.parking.utils.LogUtils;
import cn.handanlutong.parking.utils.NetWorkUtil;
import cn.handanlutong.parking.utils.SharedPreferenceUtil;
import cn.handanlutong.parking.utils.StringUtil;
import cn.handanlutong.parking.utils.ToastUtil;
import cn.handanlutong.parking.utils.UpdateManager;

/**
 * 我的钱包
 * Created by zhangyonggang on 2017/4/10.
 */

public class MyBurseActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back_MyBurse;
    private TextView tv_Recharge, tv_mywalley_yue, text1, tv_my_wallet_zsje, tv_my_wallet_czje;
    private Button btn_Recharge;
    private SharedPreferenceUtil spUtil;
    private LinearLayout ll_fapiao, ll_daijinjuan;
    private List<CouponReceive> list;
    public YWLoadingDialog mDialog;
    @Override
    public void initView() {
        setContentView(R.layout.activity_my_wallet);
        spUtil = SharedPreferenceUtil.init(this, SharedPreferenceUtil.LOGIN_INFO, Activity.MODE_PRIVATE);
        iv_back_MyBurse = (ImageView) findViewById(R.id.iv_back_MyBurse);
        tv_Recharge = (TextView) findViewById(R.id.tv_Recharge);
        text1 = (TextView) findViewById(R.id.text1);
        tv_mywalley_yue = (TextView) findViewById(R.id.tv_mywalley_yue);
        tv_my_wallet_zsje = (TextView) findViewById(R.id.tv_my_wallet_zsje);
        tv_my_wallet_czje = (TextView) findViewById(R.id.tv_my_wallet_czje);
        btn_Recharge = (Button) findViewById(R.id.btn_Recharge);
        ll_fapiao = (LinearLayout) findViewById(R.id.ll_fapiao);
        ll_daijinjuan = (LinearLayout) findViewById(R.id.ll_daijinjuan);
        postYHJData();
    }
    /**
     * 获取用户可用的优惠券
     */
    private void postYHJData() {
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.GETuserYHQ(httpUtils, jsobj1, this, UrlConfig.USER_YHQ_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getTotalBalance();
    }

    /**
     * 获取账户余额
     */
    public void getTotalBalance() {
        mDialog = new YWLoadingDialog(this);
        mDialog.show();
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
            HttpMethod.AppUsergetTotalBalances(httpUtils, jsobj1, this, UrlConfig.APPBALANCE_GETTOTAL_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    @Override
    public void setLisener() {
        iv_back_MyBurse.setOnClickListener(this);
        tv_Recharge.setOnClickListener(this);
        btn_Recharge.setOnClickListener(this);
        ll_fapiao.setOnClickListener(this);
        ll_daijinjuan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back_MyBurse:
                this.finish();
                break;
            case R.id.tv_Recharge:
                Intent intentTransation = new Intent(this, TransactionDetailActivity.class);
                startActivity(intentTransation);
                break;
            case R.id.btn_Recharge:
                Intent intent = new Intent(this, RechargeActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_daijinjuan:
//                ToastUtil.makeShortText(this, "新功能开发中...敬请期待");
                Intent intent1 = new Intent(this, YouHuiJuanActivity.class);
                intent1.putExtra("coupon", (Serializable) list);
                startActivity(intent1);
                break;
            case R.id.ll_fapiao:
//                ToastUtil.makeShortText(this, "新功能开发中...敬请期待");
//                showAlertDialog();
                Intent intent2 = new Intent(this, MonthCardActivity.class);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }

    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = getLayoutInflater().inflate(R.layout.dialog_personal_info, null);
        dialog.show();
        dialog.setContentView(view);
        TextView tv_Cancle = (TextView) view.findViewById(R.id.tv_Cancle);
        TextView tv_Sure = (TextView) view.findViewById(R.id.tv_Sure);
        TextView tv_dialog_title = (TextView) view.findViewById(R.id.tv_dialog_title);
        tv_Sure.setText("拨号");
        tv_dialog_title.setText("开发票请拨打邯郸地区服务热线400-055-5886");
        tv_Cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tv_Sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:400-055-5886"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
//                try{
//                    Intent intent = new Intent(Intent.ACTION_CALL);
//                    Uri data = Uri.parse("tel:" + "4008106188");
//                    intent.setData(data);
//                    MyBurseActivity.this.startActivity(intent);
//                }catch (SecurityException e){
//                    e.printStackTrace();
//                }
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    if (MyBurseActivity.this.checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
//                        Intent intent = new Intent(Intent.ACTION_CALL);
//                        Uri data = Uri.parse("tel:" + "4008106188");
//                        intent.setData(data);
//                        MyBurseActivity.this.startActivity(intent);
//                    }
//                }else {
//                    Intent intent = new Intent(Intent.ACTION_CALL);
//                    Uri data = Uri.parse("tel:" + "4008106188");
//                    intent.setData(data);
//                    MyBurseActivity.this.startActivity(intent);
//                }
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onFailureHttp(HttpException error, String msg, int resultCode) {
        super.onFailureHttp(error, msg);
        LogUtils.d("获取失败");
        mDialog.dismiss();
    }

    @Override
    public void onSuccessHttp(String responseInfo, int resultCode) {
        super.onSuccessHttp(responseInfo, resultCode);
        switch (resultCode) {
            case UrlConfig.APPBALANCE_GETTOTAL_CODE:
                mDialog.dismiss();
                LogUtils.d("获取账户余额：" + responseInfo);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String result = obj.optString("result");
                    if (code == 0) {
                        JSONObject obj1 = obj.optJSONObject("result");
                        double money = (double) Integer.parseInt(obj1.optString("zje")) / 100;
                        double zsje = (double) Integer.parseInt(obj1.optString("zsje")) / 100;
                        double czje = (double) Integer.parseInt(obj1.optString("czje")) / 100;
                        String yue = String.format("%.2f", money);
                        tv_mywalley_yue.setText(yue);
                        tv_my_wallet_zsje.setText(String.format("%.2f", zsje)+"");
                        tv_my_wallet_czje.setText(String.format("%.2f", czje)+"");
                    } else if (code == 1001){ //版本更新 弹框
                        JSONObject obj1 = obj.optJSONObject("result");
                        int newversionNo = Integer.parseInt(obj1.optString("versionNo"));
                        if (newversionNo > JieKouDiaoYongUtils.getVersionCode(this)) {
                            UpdateManager mUpdateManager = new UpdateManager(this);
                            mUpdateManager.showNoticeDialog(obj1.optString("versionPath"), newversionNo, obj1.optString("versionDescription"));
                        }
                    } else if (code == 1002){ //退出登录 弹框
                        JieKouDiaoYongUtils.loginTanKuan(this);
                    } else {
                        if (! StringUtil.isEmpty(result)){
                            ToastUtil.makeShortText(this, result);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case UrlConfig.USER_YHQ_CODE:
                LogUtils.d("获取用户优惠券：" + responseInfo);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String result = obj.getString("result");
//                    result_activity = obj.getString("result_activity");
                    if (code == 0) {
                        Gson gson = new Gson();
                        list = gson.fromJson(result, new TypeToken<List<CouponReceive>>(){}.getType());
                        if (list.size() == 0){
                            text1.setText("0张");
                        } else {
                            text1.setText(""+list.size()+ "张");
                        }
                    } else if (code == 1001){ //版本更新 弹框

                    } else if (code == 1002){ //退出登录 弹框

                    } else {
                        if (! StringUtil.isEmpty(result)){
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
    protected void onRestart() {
        super.onRestart();
        postYHJData();
    }
}
