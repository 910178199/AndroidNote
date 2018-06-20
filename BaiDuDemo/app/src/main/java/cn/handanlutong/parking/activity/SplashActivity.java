package cn.handanlutong.parking.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;

import com.lidroid.xutils.BitmapUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Set;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.bean.FirstPageImageBean;
import cn.handanlutong.parking.bean.LoginInfoBean;
import cn.handanlutong.parking.bean.TestDemo;
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
 * 欢迎页
 * Created by zhangyonggang on 2017/8/2.
 */

public class SplashActivity extends BaseActivity {
    private static final int GO_HOME = 0X1;
    private SharedPreferenceUtil spUtil;
    private LoginInfoBean logininfobean;
    @Override
    public void initView() {
        setContentView(R.layout.activity_splash);
//        postJieKou();
        mHandler.sendEmptyMessageDelayed(GO_HOME, 2000);
    }

    private void postJieKou() {
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
//                jsobj1.put("projectName","hdbc");
                jsobj1.put("projectName","ysz");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.jiekouQieHuan(httpUtils, jsobj1, this, UrlConfig.BASE_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }


    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_HOME:
                    redirectTo();
                    break;
            }
        }
    };

    private void redirectTo() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "share", MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (isFirstRun) {
            editor.putBoolean("isFirstRun", false);
            editor.commit();
            Intent intent = new Intent(this, GuideActivity.class);
            startActivity(intent);
            this.finish();
        } else {
            //查询广告页 接口
            if (NetWorkUtil.isNetworkConnected(this)) {
                JSONObject jsobj1 = new JSONObject();
                try {
                    jsobj1.put("appType", UrlConfig.android_type);
                    jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                HttpMethod.AppUserAdvertising(httpUtils, jsobj1, this, UrlConfig.USER_ADVERTISING_CODE);
            } else {
                ToastUtil.makeShortText(this, "请检查网络！");
            }
        }
    }

    private void AutoLogin() {
        spUtil = SharedPreferenceUtil.init(this, SharedPreferenceUtil.LOGIN_INFO, MODE_PRIVATE);
        String salt = spUtil.getString("authkey");
        String phoneNo = spUtil.getString("phoneNo");
        if (!StringUtil.isEmpty(salt) && !StringUtil.isEmpty(phoneNo)) {
            JSONObject jsobj = new JSONObject();
            try {
                JSONObject jsobj2 = new JSONObject();
                jsobj2.put("phoneNo", phoneNo);
                jsobj2.put("salt", salt);

                jsobj.put("parameter", jsobj2);
                jsobj.put("appType", UrlConfig.android_type);
                jsobj.put("version", JieKouDiaoYongUtils.getVerName(this));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.PassCodeLogins(httpUtils, jsobj, this, UrlConfig.VERIFICATIONCODE_LOGIN_CODE);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onSuccessHttp(String responseInfo, int resultCode) {
        super.onSuccessHttp(responseInfo, resultCode);
        switch (resultCode) {
            case UrlConfig.BASE_CODE:
                LogUtils.d("总接口，请求返回参数："+responseInfo);
                try {
                    JSONObject object = new JSONObject(responseInfo);
                    int code = object.optInt("code");
                    String result=object.optString("result");
                    if (code == 0){
                        TestDemo.setRootsUrl(object.optString("user_android"));
                        TestDemo.setPaysUrl(object.optString("pay_android"));
                    }else {
                        ToastUtil.makeShortText(this, result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case UrlConfig.USER_ADVERTISING_CODE:
                LogUtils.d("获取是否存在广告页："+responseInfo);
                try {
                    JSONObject object = new JSONObject(responseInfo);
                    int code = object.optInt("code");
                    String result=object.optString("result");
                    if (code == 0){
                        FirstPageImageBean.ResultBean resultbean = AnsynHttpRequest.parser.fromJson(result, FirstPageImageBean.ResultBean.class);
                        Intent intent = new Intent(this, EventReminderActivity.class);
                        intent.putExtra("resultbean", resultbean);
                        startActivity(intent);
                        finish();
                    } else if (code == 1004){ //没有广告页
                        AutoLogin();
                    } else {
                        ToastUtil.makeShortText(this, result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case UrlConfig.VERIFICATIONCODE_LOGIN_CODE:
                LogUtils.d("自动登录成功：" + responseInfo);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    if (code == 0) {
                        spUtil = SharedPreferenceUtil.init(this, SharedPreferenceUtil.LOGIN_INFO, MODE_PRIVATE);
                        logininfobean = AnsynHttpRequest.parser.fromJson(responseInfo, LoginInfoBean.class);
                        spUtil.put("username", logininfobean.getResult().getUsername());
                        spUtil.put("phoneNo", logininfobean.getResult().getPhoneNo());
                        spUtil.put("password", logininfobean.getResult().getPassword());
                        spUtil.put("salt", logininfobean.getResult().getSalt());
                        spUtil.put("authkey", logininfobean.getResult().getAuthkey());
                        spUtil.put("fullName", logininfobean.getResult().getFullName());
                        spUtil.put("age", logininfobean.getResult().getAge());
                        spUtil.put("sex", logininfobean.getResult().getSex());
                        spUtil.put("userImagePath", logininfobean.getResult().getUserImagePath());
                        spUtil.putInt("id", logininfobean.getResult().getId());

                        JPushInterface.setAlias(getApplicationContext(), logininfobean.getResult().getPhoneNo(), new TagAliasCallback() {

                            @Override
                            public void gotResult(int arg0, String arg1, Set<String> arg2) {
                            }
                        });
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (code == 3) {
                        JPushInterface.setAlias(getApplication(), "5555", new TagAliasCallback() {

                            @Override
                            public void gotResult(int arg0, String arg1, Set<String> arg2) {
                            }
                        });
                        ToastUtil.makeShortText(this, "自动登录已过期，请重新登录");
                        Intent intent = new Intent(this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (code == 1001){ //版本更新 弹框
                        Intent intent = new Intent(this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        JSONObject obj1 = obj.optJSONObject("result");
                        int newversionNo = Integer.parseInt(obj1.optString("versionNo"));
                        if (newversionNo > JieKouDiaoYongUtils.getVersionCode(this)) {
                            UpdateManager mUpdateManager = new UpdateManager(this);
                            mUpdateManager.showNoticeDialog(obj1.optString("versionPath"), newversionNo, obj1.optString("versionDescription"));
                        }
                    } else {
                        JPushInterface.setAlias(getApplication(), "5555", new TagAliasCallback() {

                            @Override
                            public void gotResult(int arg0, String arg1, Set<String> arg2) {
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }
}
