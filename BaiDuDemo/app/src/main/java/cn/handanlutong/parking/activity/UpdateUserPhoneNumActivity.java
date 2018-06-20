package cn.handanlutong.parking.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
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
 * Created by ww on 2018/1/22.
 */

public class UpdateUserPhoneNumActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout back;
    private Button btn_go;
    private SharedPreferenceUtil spUtil;
    private YWLoadingDialog mDialog;
    private TextView tv_update_old_phone;
    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_update_user_phone);
        spUtil = SharedPreferenceUtil.init(this, SharedPreferenceUtil.LOGIN_INFO, Activity.MODE_PRIVATE);
        back = (RelativeLayout) findViewById(R.id.back);
        btn_go = (Button) findViewById(R.id.btn_go);
        tv_update_old_phone = (TextView) findViewById(R.id.tv_update_old_phone);
        Intent intent = getIntent();
        String str_phone = intent.getStringExtra("phoneNum");
        tv_update_old_phone.setText("" + str_phone);
    }

    @Override
    public void setLisener() {
        super.setLisener();
        back.setOnClickListener(this);
        btn_go.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btn_go:
                postUpdatePhoneData();
                break;
            default:
                break;
        }
    }

    /**
     * 修改手机号 接口请求
     */
    private void postUpdatePhoneData() {
        mDialog = new YWLoadingDialog(this);
        mDialog.show();
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                jsobj1.put("authKey", spUtil.getString("authkey"));
                jsobj1.put("userId", spUtil.getInt("id"));
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.postUpdatePhoneData(httpUtils, jsobj1, this, UrlConfig.Update_USER_Phone_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    @Override
    public void onSuccessHttp(String responseInfo, int resultCode) {
        super.onSuccessHttp(responseInfo, resultCode);
        switch (resultCode) {
            case UrlConfig.Update_USER_Phone_CODE:
                mDialog.dismiss();
                LogUtils.d("是否可以修改手机号：" + responseInfo);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String result = obj.optString("result");
                    String message = obj.optString("message");
                    if (code == 0) {
                        Intent intent = new Intent(this, UpdateUserNewPhoneNumActivity.class);
                        startActivity(intent); //在30天内 可以修改
                    } else if (code == 1001) { //版本更新 弹框
                        JSONObject obj1 = obj.optJSONObject("result");
                        int newversionNo = Integer.parseInt(obj1.optString("versionNo"));
                        if (newversionNo > JieKouDiaoYongUtils.getVersionCode(this)) {
                            UpdateManager mUpdateManager = new UpdateManager(this);
                            mUpdateManager.showNoticeDialog(obj1.optString("versionPath"), newversionNo, obj1.optString("versionDescription"));
                        }
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
        LogUtils.d("请求失败！");
        mDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在销毁时 发送广播
        BroadcastReceiver broad = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        };
        //发送广播
        sendBroadcast(new Intent("finish"));
    }
}
