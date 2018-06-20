package cn.handanlutong.parking.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.bean.LoginInfoBean;
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
import cn.handanlutong.parking.zoom.DataCleanManager;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by ww on 2018/1/22.
 */

public class UpdateUserNewPhoneNumActivity extends BaseActivity implements View.OnClickListener{
    private RelativeLayout back;
    private EditText et_mobile,et_VerificationCode;
    private Button btn_VerificationCode,btn_login;
    private YWLoadingDialog mDialog;
    private String phoneNum, VerificationCode;
    private ImageView login_close1, login_close2;
    private SharedPreferenceUtil spUtil;
    CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            btn_VerificationCode.setEnabled(false);
            et_mobile.setEnabled(false);
            btn_VerificationCode.setText(millisUntilFinished / 1000 + "s");
            btn_VerificationCode.setTextColor(Color.parseColor("#C0C0C0"));
        }

        @Override
        public void onFinish() {
            btn_VerificationCode.setEnabled(true);
            btn_VerificationCode.setText("获取验证码");
            btn_VerificationCode.setTextColor(Color.parseColor("#4BA1DD"));
            et_mobile.setEnabled(true);
        }
    };
    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_update_new_phone);
        spUtil = SharedPreferenceUtil.init(this, SharedPreferenceUtil.LOGIN_INFO, Activity.MODE_PRIVATE);
        back=(RelativeLayout) findViewById(R.id.back);
        et_mobile= (EditText) findViewById(R.id.et_mobile);
        et_VerificationCode= (EditText) findViewById(R.id.et_VerificationCode);
        btn_login= (Button) findViewById(R.id.btn_login);
        btn_VerificationCode= (Button) findViewById(R.id.btn_VerificationCode);
        login_close1 = (ImageView) findViewById(R.id.login_close1);
        login_close2 = (ImageView) findViewById(R.id.login_close2);
    }

    @Override
    public void setLisener() {
        super.setLisener();
        back.setOnClickListener(this);
        login_close1.setOnClickListener(this);
        login_close2.setOnClickListener(this);
        btn_VerificationCode.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        et_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                login_close1.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()==11){
                    login_close1.setVisibility(View.GONE);
                    btn_VerificationCode.setEnabled(true);
                    btn_VerificationCode.setTextColor(Color.parseColor("#4BA1DD"));
                }else if (s.length()==0){
                    login_close1.setVisibility(View.GONE);
                    btn_VerificationCode.setEnabled(false);
                    btn_VerificationCode.setTextColor(Color.parseColor("#c0c0c0"));
                }
                if (s.length()==11 && et_VerificationCode.getText().length()==4){
                    btn_login.setEnabled(true);
                    btn_login.setBackgroundResource(R.mipmap.btn_yuan);
                }
            }
        });

        et_VerificationCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                login_close2.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()==4 && et_mobile.getText().length()==11){
                    login_close2.setVisibility(View.GONE);
                    btn_login.setEnabled(true);
                    btn_login.setBackgroundResource(R.mipmap.btn_yuan);
                }else if(s.length()==0){
                    login_close2.setVisibility(View.GONE);
                    btn_login.setEnabled(false);
                    btn_login.setBackgroundResource(R.mipmap.rec4);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.login_close1:
                et_mobile.setText("");
                login_close1.setVisibility(View.GONE);
                btn_VerificationCode.setTextColor(Color.parseColor("#c0c0c0"));
                break;
            case R.id.login_close2:
                et_VerificationCode.setText("");
                login_close2.setVisibility(View.GONE);
                btn_login.setBackgroundResource(R.mipmap.rec4);
                break;
            case R.id.btn_VerificationCode:
                phoneNum = et_mobile.getText().toString();
//                if(!StringUtil.isPhoneNumber(phoneNum)){
//                    ToastUtil.makeShortText(this,"请输入正确手机号！");
//                    return;
//                }
                if(StringUtil.isEmpty(phoneNum)){
                    ToastUtil.makeShortText(this,"请输入手机号！");
                    return;
                }
                SendLoginPassCode();
                break;
            case R.id.btn_login:
                phoneNum = et_mobile.getText().toString();
//                if(!StringUtil.isPhoneNumber(phoneNum)){
//                    ToastUtil.makeShortText(this,"请输入正确手机号！");
//                    return;
//                }
                if(StringUtil.isEmpty(phoneNum)){
                    ToastUtil.makeShortText(this,"请输入手机号！");
                    return;
                }
                VerificationCode = et_VerificationCode.getText().toString();
                if(StringUtil.isEmpty(VerificationCode)){
                    ToastUtil.makeShortText(this,"请输入验证码！");
                    return;
                }
                Verificationcodelogin();
                break;
            default:
                break;
        }
    }

    /**
     * 发送验证码
     */
    private void SendLoginPassCode() {
        mDialog = new YWLoadingDialog(this);
        mDialog.show();
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                JSONObject jsobj2 = new JSONObject();
                String salt= StringUtil.GetBH();

                jsobj2.put("salt", salt);
                jsobj2.put("phoneNo", phoneNum);

                String a=StringUtil.getMD5(phoneNum, "8cf15d3add99663fb4247271166cb7ec");
                String b=StringUtil.getMD5(a, salt);
                String c=StringUtil.getMD5(a+salt, b);
                String authKey=StringUtil.getMD5(phoneNum+salt, a+c+b);

                jsobj1.put("authKey", authKey);
                jsobj1.put("userId", spUtil.getInt("id"));
                jsobj1.put("parameter", jsobj2);
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.SendLoginPassCode(httpUtils, jsobj1, this, UrlConfig.Update_USER_Phone_SMS_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    /**
     * 保存 修改手机号
     */
    private void Verificationcodelogin() {
        mDialog = new YWLoadingDialog(this);
        mDialog.show();
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj = new JSONObject();
            try {
                JSONObject jsobj2 = new JSONObject();
                jsobj2.put("phoneNo", phoneNum);
                jsobj2.put("salt", VerificationCode);

                jsobj.put("parameter", jsobj2);
                jsobj.put("userId", spUtil.getInt("id"));
                jsobj.put("authKey", spUtil.getString("authkey"));
                jsobj.put("appType", UrlConfig.android_type);
                jsobj.put("version", JieKouDiaoYongUtils.getVerName(this));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.Verificationcodelogin(httpUtils, jsobj, this, UrlConfig.Update_USER_Phone_SUCCESS_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    @Override
    public void onSuccessHttp(String responseInfo, int resultCode) {
        super.onSuccessHttp(responseInfo, resultCode);
        switch (resultCode){
            case UrlConfig.Update_USER_Phone_SMS_CODE:
                mDialog.dismiss();
                LogUtils.d("发送验证码成功："+responseInfo);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code=obj.optInt("code");
                    String result = obj.optString("result");
                    String message = obj.optString("message");
                    if (code == 0){
                        timer.start();
                        ToastUtil.makeShortText(this, message);
                    } else if (code == 1001) { //版本更新 弹框
                        JSONObject obj1 = obj.optJSONObject("result");
                        int newversionNo = Integer.parseInt(obj1.optString("versionNo"));
                        if (newversionNo > JieKouDiaoYongUtils.getVersionCode(this)) {
                            UpdateManager mUpdateManager = new UpdateManager(this);
                            mUpdateManager.showNoticeDialog(obj1.optString("versionPath"), newversionNo, obj1.optString("versionDescription"));
                        }
                    } else {
                        if (! StringUtil.isEmpty(result)){
                            ToastUtil.makeShortText(this, result);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case UrlConfig.Update_USER_Phone_SUCCESS_CODE:
                mDialog.dismiss();
                LogUtils.d("手机号修改成功："+responseInfo);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code=obj.optInt("code");
                    String result = obj.optString("result");
                    if(code == 0){
//                        ToastUtil.makeShortText(this, "修改成功，请重新登录");
                        //手机号修改成功，跳到登录页
                        final Dialog dialo = new Dialog(this, R.style.Dialog);
                        dialo.setCanceledOnTouchOutside(false);//设置外部不可点击
                        dialo.setCancelable(false);
                        dialo.setContentView(R.layout.mobile_dialog);
                        TextView tv = (TextView) dialo.findViewById(R.id.mobile_tv);
                        tv.setText("修改成功，请重新登录");
                        JPushInterface.setAlias(this, "5555", new TagAliasCallback() {

                            @Override
                            public void gotResult(int arg0, String arg1, Set<String> arg2) {
                            }
                        });
                        Button btn_know = (Button) dialo.findViewById(R.id.btn_know);
                        btn_know.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                UrlConfig.isSetDialogShow = true;
                                DataCleanManager.clearAllCache(UpdateUserNewPhoneNumActivity.this);//清空缓存
                                SharedPreferenceUtil spUtil = SharedPreferenceUtil.init(UpdateUserNewPhoneNumActivity.this,SharedPreferenceUtil.LOGIN_INFO, Activity.MODE_PRIVATE);
                                spUtil.removeCurrentUserInfo();
                                SharedPreferenceUtil spUtil2 = SharedPreferenceUtil.init(UpdateUserNewPhoneNumActivity.this, SharedPreferenceUtil.FaPiaoMore, Activity.MODE_PRIVATE);
                                spUtil2.removeCurrentUserInfo();
                                dialo.dismiss();
                                BaseActivity.killAll();
                                JPushInterface.setAlias(UpdateUserNewPhoneNumActivity.this, "5555", new TagAliasCallback() {

                                    @Override
                                    public void gotResult(int arg0, String arg1, Set<String> arg2) {
                                    }
                                });
                                Intent intent = new Intent(UpdateUserNewPhoneNumActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        });
                        dialo.show();
                    } else if (code == 1001){ //版本更新 弹框
                        JSONObject obj1 = obj.optJSONObject("result");
                        int newversionNo = Integer.parseInt(obj1.optString("versionNo"));
                        if (newversionNo > JieKouDiaoYongUtils.getVersionCode(this)) {
                            UpdateManager mUpdateManager = new UpdateManager(this);
                            mUpdateManager.showNoticeDialog(obj1.optString("versionPath"), newversionNo, obj1.optString("versionDescription"));
                        }
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
        LogUtils.d("请求失败！");
        mDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
