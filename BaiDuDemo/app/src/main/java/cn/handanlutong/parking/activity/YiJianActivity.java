package cn.handanlutong.parking.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
 * Created by ww on 2017/8/2.
 * 意见反馈界面
 */

public class YiJianActivity extends BaseActivity implements View.OnClickListener {
    RelativeLayout back;
    Button btnBaocun;
    EditText et_yijian;
    TextView tv_count;
    TextView tv_phone;
    TextView tv_phone2;
    private SharedPreferenceUtil spUtil;
    public YWLoadingDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yijian);
        spUtil = SharedPreferenceUtil.init(this, SharedPreferenceUtil.LOGIN_INFO, Activity.MODE_PRIVATE);
        back = (RelativeLayout) findViewById(R.id.back);
        btnBaocun = (Button) findViewById(R.id.btn_baocun);
        et_yijian = (EditText) findViewById(R.id.et_yijian);
        tv_count = (TextView) findViewById(R.id.tv_yijian_count);
        tv_phone = (TextView) findViewById(R.id.tv_yijian_phone);
        tv_phone2 = (TextView) findViewById(R.id.tv_yijian_phone2);
        tv_phone.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//添加下划线
//        //设置EditText的显示方式为多行文本输入
//        et_yijian.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
//        //改变默认的单行模式
//        et_yijian.setSingleLine(false);
//        //水平滚动设置为False
//        et_yijian.setHorizontallyScrolling(false);
        tv_phone.setOnClickListener(this);
        tv_phone2.setOnClickListener(this);
        back.setOnClickListener(this);
        btnBaocun.setOnClickListener(this);
        et_yijian.setOnClickListener(this);
        SharedPreferences sp = getSharedPreferences("user_login", MODE_PRIVATE);
        //设置光标不显示,但不能设置光标颜色
        et_yijian.setCursorVisible(false);
        et_yijian.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                et_yijian.setCursorVisible(true);
            }

            @Override
            public void afterTextChanged(Editable s) {
//                if (s.length()==300){
//                    ToastUtil.showToast(YiJianActivity.this,"字数已到限制，");
//                }
                et_yijian.setCursorVisible(true);
                tv_count.setText(s.length() + "");
                int index = et_yijian.getSelectionStart() - 1;
                if (index > 0) {
                    if (StringUtil.isEmojiCharacter(s.charAt(index))) {
                        Editable edit = et_yijian.getText();
                        edit.delete(s.length() - 2, s.length());
                        ToastUtil.makeShortText(YiJianActivity.this, "不支持输入表情符号");
                    }
                }
            }
        });
        et_yijian.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                } else {

                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.et_yijian:
                et_yijian.setCursorVisible(true);
                break;
            case R.id.btn_baocun:
                SubmiteYijian();
                break;
            case R.id.tv_yijian_phone:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:400-810-6188"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.tv_yijian_phone2:
                Intent intent1 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:400-055-5886"));
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                break;
        }
    }

    private void SubmiteYijian() {
        String yijina = et_yijian.getText().toString().trim();
        if (StringUtil.isEmpty(yijina)) {
            ToastUtil.makeShortText(YiJianActivity.this, "请填写信息");
            return;
        }
        mDialog = new YWLoadingDialog(this);
        mDialog.show();
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                JSONObject jsobj2 = new JSONObject();
                jsobj2.put("content", yijina);

                jsobj1.put("parameter", jsobj2);
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.AppUserSubmite(httpUtils, jsobj1, this, UrlConfig.USER_SUBMITEYIJINA_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }


    @Override
    public void onSuccessHttp(String responseInfo, int resultCode) {
        super.onSuccessHttp(responseInfo, resultCode);
        switch (resultCode) {
            case UrlConfig.USER_SUBMITEYIJINA_CODE:
                mDialog.dismiss();
                LogUtils.d("提交成功：" + responseInfo);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    if (code == 0) {
                        ToastUtil.makeShortText(this, "提交成功!");
                        this.finish();
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
                        ToastUtil.makeShortText(this, "请求失败，请稍后重试");
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
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onFailureHttp(HttpException error, String msg, int resultCode) {
        super.onFailureHttp(error, msg, resultCode);
        LogUtils.d("访问失败");
        mDialog.dismiss();
    }
}
