package cn.handanlutong.parking.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.lidroid.xutils.exception.HttpException;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.bean.UserInfoBean;
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

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 修改昵称
 * Created by zhangyonggang on 2017/8/18.
 */

public class EditNikeNameActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_Save, tv_back_cancle;
    private EditText et_name;
    private ImageView iv_close;
    private SharedPreferenceUtil spUtil;
    public YWLoadingDialog mDialog;
    @Override
    public void initView() {
        setContentView(R.layout.activity_editnikename);
        spUtil = SharedPreferenceUtil.init(this, SharedPreferenceUtil.LOGIN_INFO, Activity.MODE_PRIVATE);
        Intent intent = getIntent();
        String name=intent.getStringExtra("nikeName");
        tv_back_cancle = (TextView) findViewById(R.id.tv_back_cancle);
        tv_Save = (TextView) findViewById(R.id.tv_Save);
        et_name = (EditText) findViewById(R.id.et_name);
        iv_close = (ImageView) findViewById(R.id.iv_close);
        et_name.setText(name);
//        et_name.setSelection(name.length());
        //设置光标不显示,但不能设置光标颜色
        et_name.setCursorVisible(false);
        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                iv_close.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                et_name.setCursorVisible(true);
                iv_close.setVisibility(View.VISIBLE);
                //手动设置maxLength为8
                InputFilter[] filters = {new InputFilter.LengthFilter(8)};
                et_name.setFilters(filters);
            }

            @Override
            public void afterTextChanged(Editable s) {
//                iv_close.setVisibility(View.GONE);
                et_name.setCursorVisible(true);
                //手动设置maxLength为8
                InputFilter[] filters = {new InputFilter.LengthFilter(8)};
                et_name.setFilters(filters);
                int index = et_name.getSelectionStart() - 1;
                if (index > 0) {
                    if (StringUtil.isEmojiCharacter(s.charAt(index))) {
                        Editable edit = et_name.getText();
                        edit.delete(s.length() - 2, s.length());
                        ToastUtil.makeShortText(EditNikeNameActivity.this, "不支持输入表情符号");
                    }
                }else {
                    iv_close.setVisibility(View.GONE);
                }
            }
        });
        et_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    iv_close.setVisibility(View.VISIBLE);
                }else{
                    iv_close.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void setLisener() {
        tv_back_cancle.setOnClickListener(this);
        tv_Save.setOnClickListener(this);
        iv_close.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back_cancle:
                finish();
                break;
            case R.id.iv_close:
                et_name.setText("");
                iv_close.setVisibility(View.GONE);
                break;
            case R.id.tv_Save:
                String nikename = et_name.getText().toString();
                if (TextUtils.isEmpty(nikename)) {
                    ToastUtil.makeShortText(this, "昵称不能为空");
                    return;
                }
                mDialog = new YWLoadingDialog(this);
                mDialog.show();
                if (NetWorkUtil.isNetworkConnected(this)) {
                    JSONObject jsobj1 = new JSONObject();
                    try {
                        JSONObject jsobj2 = new JSONObject();
                        jsobj2.put("fullName", nikename);

                        jsobj1.put("parameter", jsobj2);
                        jsobj1.put("appType", UrlConfig.android_type);
                        jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                        jsobj1.put("authKey", spUtil.getString("authkey"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    HttpMethod.AppUserUpdateName(httpUtils, jsobj1, this, UrlConfig.UPDATE_NAME_CODE);
                } else {
                    ToastUtil.makeShortText(this, "请检查网络！");
                }
                break;
            case R.id.et_name:
                et_name.setCursorVisible(true);
                break;
            default:
                break;
        }
    }

    @Override
    public void onSuccessHttp(String responseInfo, int resultCode) {
        super.onSuccessHttp(responseInfo, resultCode);
        switch (resultCode) {
            case UrlConfig.UPDATE_NAME_CODE:
                mDialog.dismiss();
                LogUtils.d("修改昵称成功：" + responseInfo);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String result = obj.optString("result");
                    if (code == 0) {
                        UserInfoBean.UserBean userinfobean = AnsynHttpRequest.parser.fromJson(result, UserInfoBean.UserBean.class);
                        spUtil.put("fullName",userinfobean.getFullName());
                        ToastUtil.makeShortText(this,"修改成功！");
                        this.finish();
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
}
