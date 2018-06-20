package cn.handanlutong.parking.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;
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
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;


/**
 * 登录页
 * Created by zhangyonggang on 2017/4/15.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_mobile,login_close1,iv_news,login_close2;
    private EditText et_mobile,et_VerificationCode;
    private Button btn_VerificationCode,btn_login;
    private TextView tv_Protocol;
    private String phoneNum;
    private String VerificationCode;
    private SharedPreferenceUtil spUtil;
    private LoginInfoBean logininfobean;
    public YWLoadingDialog mDialog;
    CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            btn_VerificationCode.setEnabled(false);
            et_mobile.setEnabled(false);
            login_close1.setEnabled(false);
            btn_VerificationCode.setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            btn_VerificationCode.setEnabled(true);
            btn_VerificationCode.setText("获取验证码");
            et_mobile.setEnabled(true);
        }
    };

    @Override
    public void initView() {
        setContentView(R.layout.activity_login);
        tv_Protocol= (TextView) findViewById(R.id.tv_Protocol);
        iv_mobile = (ImageView) findViewById(R.id.iv_mobile);
        login_close1 = (ImageView) findViewById(R.id.login_close1);
        login_close2 = (ImageView) findViewById(R.id.login_close2);
        iv_news = (ImageView) findViewById(R.id.iv_news);
        et_mobile= (EditText) findViewById(R.id.et_mobile);
        et_VerificationCode= (EditText) findViewById(R.id.et_VerificationCode);
        btn_VerificationCode= (Button) findViewById(R.id.btn_VerificationCode);
        btn_login= (Button) findViewById(R.id.btn_login);
        et_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                login_close1.setVisibility(View.VISIBLE);
                iv_mobile.setImageResource(R.mipmap.green_phone);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()==11){
                    login_close1.setVisibility(View.GONE);
                    btn_VerificationCode.setEnabled(true);
                    btn_VerificationCode.setBackgroundResource(R.mipmap.rec5);
                    btn_VerificationCode.setTextColor(Color.parseColor("#333333"));
                }else if (s.length()==0){
                    login_close1.setVisibility(View.GONE);
                    btn_VerificationCode.setEnabled(false);
                    iv_mobile.setImageResource(R.mipmap.phone);
                    btn_VerificationCode.setTextColor(Color.parseColor("#c0c0c0"));
                    btn_VerificationCode.setBackgroundResource(R.mipmap.rec3);
                }
                if (s.length()==11 && et_VerificationCode.getText().length()==4){
                    btn_login.setEnabled(true);
                    btn_login.setBackgroundResource(R.mipmap.btn_yuan);
                    btn_login.setTextColor(Color.parseColor("#ffffff"));
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
                iv_news.setImageResource(R.mipmap.green_news);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()==4 && et_mobile.getText().length()==11){
                    login_close2.setVisibility(View.GONE);
                    btn_login.setEnabled(true);
                    btn_login.setBackgroundResource(R.mipmap.btn_yuan);
                    btn_login.setTextColor(Color.parseColor("#ffffff"));
                }else if(s.length()==0){
                    login_close2.setVisibility(View.GONE);
                    btn_login.setEnabled(false);
                    iv_news.setImageResource(R.mipmap.news);
                    btn_login.setBackgroundResource(R.mipmap.rec4);
                    btn_login.setTextColor(Color.parseColor("#ffffff"));
                }
            }
        });

    }

    @Override
    public void setLisener() {
        tv_Protocol.setOnClickListener(this);
        btn_VerificationCode.setOnClickListener(this);
        btn_VerificationCode.setEnabled(false);
        login_close1.setOnClickListener(this);
        login_close2.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_login.setEnabled(false);
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
                String salt=StringUtil.GetBH();

                jsobj2.put("salt", salt);
                jsobj2.put("phoneNo", phoneNum);

                String a=StringUtil.getMD5(phoneNum, "8cf15d3add99663fb4247271166cb7ec");
                String b=StringUtil.getMD5(a, salt);
                String c=StringUtil.getMD5(a+salt, b);
                String authKey=StringUtil.getMD5(phoneNum+salt, a+c+b);

                jsobj1.put("authKey", authKey);
                jsobj1.put("parameter", jsobj2);
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.GainLoginPassCodes(httpUtils, jsobj1, this, UrlConfig.GETPASSCODE_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    /**
     * 登录
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
                jsobj2.put("phoneBarand", JieKouDiaoYongUtils.getSystemModel()); //手机型号
                jsobj2.put("phoneSysVersion", JieKouDiaoYongUtils.getSystemVersion()); //系统版本

                jsobj.put("parameter", jsobj2);
                jsobj.put("appType", UrlConfig.android_type);
                jsobj.put("version", JieKouDiaoYongUtils.getVerName(this));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.PassCodeLogins(httpUtils, jsobj, this, UrlConfig.VERIFICATIONCODE_LOGIN_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_close1:
                et_mobile.setText("");
                login_close1.setVisibility(View.GONE);
                iv_mobile.setImageResource(R.mipmap.phone);
                btn_VerificationCode.setTextColor(Color.parseColor("#c0c0c0"));
                btn_VerificationCode.setBackgroundResource(R.mipmap.rec3);
                break;
            case R.id.login_close2:
                et_VerificationCode.setText("");
                login_close2.setVisibility(View.GONE);
                iv_news.setImageResource(R.mipmap.news);
                btn_login.setBackgroundResource(R.mipmap.rec4);
                btn_login.setTextColor(Color.parseColor("#ffffff"));
                break;
            case R.id.tv_Protocol:
                Intent intent = new Intent(this,UserProtocolActivity.class);
                intent.putExtra("H5_url","file:///android_asset/agreement.html");
                intent.putExtra("tv_title","用户协议");
                intent.putExtra("hdgqsj", "false");
                startActivity(intent);
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

    @Override
    public void onSuccessHttp(String responseInfo, int resultCode) {
        super.onSuccessHttp(responseInfo, resultCode);
        switch (resultCode){
            case UrlConfig.GETPASSCODE_CODE:
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
            case UrlConfig.VERIFICATIONCODE_LOGIN_CODE:
                mDialog.dismiss();
                LogUtils.d("登录成功："+responseInfo);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code=obj.optInt("code");
                    String result = obj.optString("result");
                    if(code == 0){
                        spUtil = SharedPreferenceUtil.init(this,SharedPreferenceUtil.LOGIN_INFO, MODE_PRIVATE);
                        logininfobean= AnsynHttpRequest.parser.fromJson(responseInfo,LoginInfoBean.class);
                        spUtil.put("username",logininfobean.getResult().getUsername());
                        spUtil.put("phoneNo",logininfobean.getResult().getPhoneNo());
                        spUtil.put("password",logininfobean.getResult().getPassword());
                        spUtil.put("salt",logininfobean.getResult().getSalt());
                        spUtil.put("authkey",logininfobean.getResult().getAuthkey());
                        spUtil.put("fullName",logininfobean.getResult().getFullName());
                        spUtil.put("age",logininfobean.getResult().getAge());
                        spUtil.put("sex",logininfobean.getResult().getSex());
                        spUtil.put("userImagePath",logininfobean.getResult().getUserImagePath());
                        spUtil.putInt("id",logininfobean.getResult().getId());
                        JPushInterface.setAlias(getApplicationContext(), et_mobile.getText().toString().trim(), new TagAliasCallback() {

                            @Override
                            public void gotResult(int arg0, String arg1, Set<String> arg2) {
                            }
                        });
                        Intent intent= new Intent(this,MainActivity.class);
                        startActivity(intent);
                        finish();
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            System.gc();
            UrlConfig.isSetDialogShow = true;
            finish();
            killAll();
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
