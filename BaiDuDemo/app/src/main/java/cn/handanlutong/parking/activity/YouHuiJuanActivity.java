package cn.handanlutong.parking.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ReplacementTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.adapter.YouHuiAdapter;
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
 * Created by ww on 2017/8/23.
 * 优惠券 列表页面
 */

public class YouHuiJuanActivity extends BaseActivity implements View.OnClickListener{
    RelativeLayout back;
    TextView tv_shuoming;
    ListView lv_youhui;
    LinearLayout ll_no_ditu;
    YouHuiAdapter adapter;
    List<CouponReceive> garage;
    EditText et_youhuiq_shuru;
    Button btn_youhuiq_duihuan;
    YWLoadingDialog mDialog;
    private SharedPreferenceUtil spUtil;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youhuijuan);
        spUtil = SharedPreferenceUtil.init(this, SharedPreferenceUtil.LOGIN_INFO, Activity.MODE_PRIVATE);
        back=(RelativeLayout) findViewById(R.id.back);
        tv_shuoming = (TextView) findViewById(R.id.tv_youhui_shuoming);
        lv_youhui = (ListView) findViewById(R.id.lv_youhui);
        ll_no_ditu = (LinearLayout) findViewById(R.id.ll_no_ditu);
        et_youhuiq_shuru = (EditText) findViewById(R.id.et_youhuiq_shuru);
        btn_youhuiq_duihuan = (Button) findViewById(R.id.btn_youhuiq_duihuan);
        back.setOnClickListener(this);
        tv_shuoming.setOnClickListener(this);
        btn_youhuiq_duihuan.setOnClickListener(this);
        Intent intent=getIntent();
        if (intent!=null){
            List<CouponReceive> list = (List<CouponReceive>) intent.getSerializableExtra("coupon");
            if (list != null){
                if (list.size() == 0){
                    ll_no_ditu.setVisibility(View.VISIBLE);
                    lv_youhui.setVisibility(View.GONE);
                } else {
                    ll_no_ditu.setVisibility(View.GONE);
                    lv_youhui.setVisibility(View.VISIBLE);
                    adapter = new YouHuiAdapter(YouHuiJuanActivity.this, list);
                    lv_youhui.setAdapter(adapter);
                }
            } else {
                postYHJData();
            }
        }
        et_youhuiq_shuru.setTransformationMethod(new test());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.tv_youhui_shuoming://使用说明
                postYHJGuiZeData();
                break;
            case R.id.btn_youhuiq_duihuan:
                String str_yhq_dhm = et_youhuiq_shuru.getText().toString().trim();
                if (StringUtil.isEmpty(str_yhq_dhm)){
                    ToastUtil.makeShortText(this, "兑换码不能为空");
                    return;
                }
                postYHQDuiHuanData(str_yhq_dhm);
                break;
            default:
                break;
        }
    }

    /**
     * 获取优惠券规则
     */
    private void postYHJGuiZeData() {
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
                jsobj1.put("appType",UrlConfig.android_type);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.getYHQGuiZeData(httpUtils, jsobj1, this, UrlConfig.GET_YHQ_GUI_ZE_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
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

    /**
     * 优惠券兑换 接口
     */
    private void postYHQDuiHuanData(String str) {
        mDialog = new YWLoadingDialog(this);
        mDialog.show();
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                JSONObject jsobj2 = new JSONObject();
                jsobj2.put("yhjxlh", str);

                jsobj1.put("parameter", jsobj2);
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.postYHQDuiHuanData(httpUtils, jsobj1, this, UrlConfig.YHQDuiHuan_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
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
            case UrlConfig.YHQDuiHuan_CODE:
                mDialog.dismiss();
                LogUtils.d("优惠券兑换成功：" + responseInfo);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String result = obj.optString("result");
                    if (code == 0) {
                        ToastUtil.makeShortText(this, "兑换成功");
                        postYHJData();
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
                    if (code == 0) {
                        Gson gson = new Gson();
                        garage = gson.fromJson(result, new TypeToken<List<CouponReceive>>(){}.getType());
                        if (garage.size() == 0){
                            ll_no_ditu.setVisibility(View.VISIBLE);
                            lv_youhui.setVisibility(View.GONE);
                        } else {
                            ll_no_ditu.setVisibility(View.GONE);
                            lv_youhui.setVisibility(View.VISIBLE);
                            adapter = new YouHuiAdapter(YouHuiJuanActivity.this, garage);
                            lv_youhui.setAdapter(adapter);
                        }
                    } else {
                        ToastUtil.makeShortText(this, result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case UrlConfig.GET_YHQ_GUI_ZE_CODE:
                LogUtils.d("获取优惠券列表的规则：" + responseInfo);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String result = obj.optString("result");
                    String message = obj.optString("message");
                    if (code == 0) {
                        Intent intent = new Intent(this, UserProtocolActivity.class);
                        intent.putExtra("H5_url", result);
                        intent.putExtra("tv_title","使用说明");
                        intent.putExtra("hdgqsj", "false");
                        startActivity(intent);
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

    public class test extends ReplacementTransformationMethod {
        @Override
        protected char[] getOriginal() {
            char[] aa = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
            return aa;
        }

        @Override
        protected char[] getReplacement() {
            char[] cc = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
            return cc;
        }
    }
}
