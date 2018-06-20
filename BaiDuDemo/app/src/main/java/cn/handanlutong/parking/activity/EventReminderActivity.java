package cn.handanlutong.parking.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.bean.FirstPageImageBean.ResultBean;
import cn.handanlutong.parking.bean.LoginInfoBean;
import cn.handanlutong.parking.customview.YWLoadingDialog;
import cn.handanlutong.parking.http.AnsynHttpRequest;
import cn.handanlutong.parking.http.HttpMethod;
import cn.handanlutong.parking.http.UrlConfig;
import cn.handanlutong.parking.utils.JieKouDiaoYongUtils;
import cn.handanlutong.parking.utils.LogUtils;
import cn.handanlutong.parking.utils.SharedPreferenceUtil;
import cn.handanlutong.parking.utils.StringUtil;
import cn.handanlutong.parking.utils.ToastUtil;
import cn.handanlutong.parking.utils.UpdateManager;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 活动页
 * Created by zhangyonggang on 2017/8/4.
 */

public class EventReminderActivity extends BaseActivity implements View.OnClickListener {
    private Button btn_sport;
    private SharedPreferenceUtil spUtil;
    private LoginInfoBean logininfobean;
    private ImageView ll_action_sport;
    private ResultBean resultbean;
    public YWLoadingDialog mDialog;
    CountDownTimer countdowntimer = new CountDownTimer(4000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            btn_sport.setText("跳过(" + millisUntilFinished / 1000 + "s)");
        }

        @Override
        public void onFinish() {
            btn_sport.setText("跳过(0s)");
            AutoLogin();
        }
    };


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
    public void initView() {
        setContentView(R.layout.activity_eventremind);
        btn_sport = (Button) findViewById(R.id.btn_sport);
        ll_action_sport = (ImageView) findViewById(R.id.ll_action_sport);
        Intent intent = getIntent();
        resultbean = (ResultBean) intent.getSerializableExtra("resultbean");
        if (resultbean != null){
            new BitmapUtils(this).display(ll_action_sport, resultbean.getTpdz());
//            ImageLoaderPicture vlp = new ImageLoaderPicture(this, ll_action_sport);
//            vlp.getmImageLoader().get(resultbean.getTpdz(), vlp.getOne_listener());
            countdowntimer.start();
            if (! StringUtil.isEmpty(resultbean.getNr())){
                ll_action_sport.setEnabled(true);
            }else {
                ll_action_sport.setEnabled(false);
            }
        }
    }

    @Override
    public void setLisener() {
        btn_sport.setOnClickListener(this);
        ll_action_sport.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sport:
                countdowntimer.cancel();
                AutoLogin();
                break;
            case R.id.ll_action_sport:
                countdowntimer.cancel();
                Intent intent = new Intent(this, UserProtocolActivity.class);
                try {
                    intent.putExtra("H5_url", resultbean.getNr());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                intent.putExtra("hdgqsj", "false");
                intent.putExtra("tv_title","活动详情");
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onSuccessHttp(String responseInfo, int resultCode) {
        super.onSuccessHttp(responseInfo, resultCode);
        switch (resultCode) {
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

    @Override
    public void onFailureHttp(HttpException error, String msg) {
        super.onFailureHttp(error, msg);
        LogUtils.d("请求失败！");
        mDialog.dismiss();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        countdowntimer.cancel();
        AutoLogin();
    }
}
