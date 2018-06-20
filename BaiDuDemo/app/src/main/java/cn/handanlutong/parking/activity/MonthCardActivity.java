package cn.handanlutong.parking.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.adapter.MonthCardAdapter;
import cn.handanlutong.parking.bean.LicensePlateCardVo;
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

/**
 * Created by ww on 2017/9/8.
 */

public class MonthCardActivity extends BaseActivity implements View.OnClickListener{

    private RelativeLayout back;
    private YWLoadingDialog mDialog;
    private SharedPreferenceUtil spUtil;
    private LinearLayout ll_no_ditu, ll_no_yueka;
    private ListView lv_youhui;
    private Button btn_Recharge1;
    public int carFlag;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_month_card);
        spUtil = SharedPreferenceUtil.init(this, SharedPreferenceUtil.LOGIN_INFO, Activity.MODE_PRIVATE);
        back = (RelativeLayout) findViewById(R.id.back);
        ll_no_ditu = (LinearLayout) findViewById(R.id.ll_no_ditu);
        ll_no_yueka = (LinearLayout) findViewById(R.id.ll_no_yueka);
        lv_youhui = (ListView) findViewById(R.id.lv_youhui);
        btn_Recharge1 = (Button) findViewById(R.id.btn_Recharge1);
        postMonthCardData();
    }

    /**
     * 我的 月卡 查询接口
     */
    private void postMonthCardData() {
        mDialog = new YWLoadingDialog(this);
        mDialog.show();
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
                jsobj1.put("userId", spUtil.getInt("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.postMonthCardData(httpUtils, jsobj1, this, UrlConfig.USER_Month_Card_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    /**
     * 购买月卡 接口请求
     */
    private void postMonthCardBuyData() {
        mDialog = new YWLoadingDialog(this);
        mDialog.show();
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
                jsobj1.put("userId", spUtil.getInt("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.postMonthCardBuyData(httpUtils, jsobj1, this, UrlConfig.USER_Month_Card_Buy_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    @Override
    public void setLisener() {
        super.setLisener();
        back.setOnClickListener(this);
        btn_Recharge1.setOnClickListener(this);
        ll_no_yueka.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.btn_Recharge1:
                carFlag = 2;
                postMonthCardBuyData();
                break;
            case R.id.ll_no_yueka:
                carFlag = 2;
                postMonthCardBuyData();
                break;
            default:
                break;
        }
    }

    @Override
    public void onSuccessHttp(String responseInfo, int resultCode) {
        super.onSuccessHttp(responseInfo, resultCode);
        switch (resultCode){
            case UrlConfig.USER_Month_Card_CODE:
                mDialog.dismiss();
                LogUtils.d("我的月卡查询：" + responseInfo);
                try {
                    JSONObject obj= new JSONObject(responseInfo);
                    int code =obj.optInt("code");
                    String result=obj.optString("result");
                    if(code == 0){
                        List<LicensePlateCardVo> list = AnsynHttpRequest.parser.fromJson(result, new TypeToken<List<LicensePlateCardVo>>(){}.getType());
                        if (list.size() == 0){
                            lv_youhui.setVisibility(View.GONE);
                            ll_no_ditu.setVisibility(View.VISIBLE);
                            btn_Recharge1.setVisibility(View.GONE);
                        }else {
                            lv_youhui.setVisibility(View.VISIBLE);
                            ll_no_ditu.setVisibility(View.GONE);
                            btn_Recharge1.setVisibility(View.VISIBLE);
                            lv_youhui.setAdapter(new MonthCardAdapter(this, list, carFlag));
                        }
                    } else if (code == 1001){ //版本更新 弹框
                        JSONObject obj1 = obj.optJSONObject("result");
                        int newversionNo = Integer.parseInt(obj1.optString("versionNo"));
                        if (newversionNo > JieKouDiaoYongUtils.getVersionCode(this)) {
                            UpdateManager mUpdateManager = new UpdateManager(this);
                            mUpdateManager.showNoticeDialog(obj1.optString("versionPath"), newversionNo, obj1.optString("versionDescription"));
                        }
                    } else if (code == 1002){ //退出登录 弹框
                        JieKouDiaoYongUtils.loginTanKuan(this);
                    } else{
                        if (! StringUtil.isEmpty(result)){
                            ToastUtil.makeShortText(this, result);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case UrlConfig.USER_Month_Card_Buy_CODE:
                mDialog.dismiss();
                LogUtils.d("去购买月卡：" + responseInfo);
                try {
                    JSONObject obj= new JSONObject(responseInfo);
                    int code =obj.optInt("code");
                    String message=obj.optString("message");
                    if (code == 0){
                        Intent intent = new Intent(this, BuyMonthCardActivity.class);
                        intent.putExtra("carFlag", carFlag);
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
                    } else{
                        if (! StringUtil.isEmpty(message)){
                            ToastUtil.makeShortText(this, message);
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
        mDialog.dismiss();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        postMonthCardData();
    }
}
