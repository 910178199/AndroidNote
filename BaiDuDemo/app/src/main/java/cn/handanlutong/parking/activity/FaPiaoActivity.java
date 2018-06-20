package cn.handanlutong.parking.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;
import org.json.JSONObject;

import cn.handanlutong.parking.R;
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
 * Created by ww on 2017/11/21.
 */

public class FaPiaoActivity extends BaseActivity implements View.OnClickListener{

    private RelativeLayout back;
    private TextView tv_fapiao_zje, tv_fapiao_history;
    private Button btn_fapiao;
    private SharedPreferenceUtil spUtil;
    private String fapiao_zje;
    private YWLoadingDialog mDialog;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_fapiao);
        spUtil = SharedPreferenceUtil.init(this, SharedPreferenceUtil.LOGIN_INFO, MODE_PRIVATE);
        back = (RelativeLayout) findViewById(R.id.back);
        tv_fapiao_zje = (TextView) findViewById(R.id.tv_fapiao_zje);
        tv_fapiao_history = (TextView) findViewById(R.id.tv_fapiao_history);
        btn_fapiao = (Button) findViewById(R.id.btn_fapiao);
        postFaPiaoData();
    }

    /**
     * 获取 可开发票 额度  == 我的钱包余额 接口 （一样）
     */
    private void postFaPiaoData() {
        mDialog = new YWLoadingDialog(this);
        mDialog.show();
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.postFaPiaoData(httpUtils, jsobj1, this, UrlConfig.APPBALANCE_GETTOTAL_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    @Override
    public void setLisener() {
        super.setLisener();
        back.setOnClickListener(this);
        btn_fapiao.setOnClickListener(this);
        tv_fapiao_history.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.btn_fapiao:
                if (! StringUtil.isEmpty(fapiao_zje)){
                    if (! fapiao_zje.equals("0.00")){
                        Intent intent = new Intent(this, FaPiaoADDActivity.class);
                        intent.putExtra("fapiao_zje", fapiao_zje);
                        startActivity(intent);
                    }else {
                        ToastUtil.makeShortText(this, "由于您还没有消费，暂不能使用该功能");
                    }
                }
                break;
            case R.id.tv_fapiao_history:
                Intent intent1 = new Intent(this, FaPiaoHistoryActivity.class);
                startActivity(intent1);
                break;
            default:
                break;
        }
    }

    @Override
    public void onSuccessHttp(String responseInfo, int resultCode) {
        super.onSuccessHttp(responseInfo, resultCode);
        switch (resultCode) {
            case UrlConfig.APPBALANCE_GETTOTAL_CODE:
                mDialog.dismiss();
                LogUtils.d("我的余额 获取成功：" + responseInfo);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String result = obj.optString("result");
                    if (code == 0) {
                        JSONObject obj1 = obj.optJSONObject("result");
                        double money = (double) Integer.parseInt(obj1.optString("kkfpje")) / 100;
                        fapiao_zje = String.format("%.2f", money);
                        tv_fapiao_zje.setText("" + fapiao_zje);
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
            default:
                break;
        }
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
        postFaPiaoData();
    }
}
