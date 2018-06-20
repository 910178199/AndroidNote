package cn.handanlutong.parking.activity.baidu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.activity.BaseActivity;
import cn.handanlutong.parking.adapter.DiTuAdapter;
import cn.handanlutong.parking.bean.GarageBean;
import cn.handanlutong.parking.bean.SFguize;
import cn.handanlutong.parking.customview.YWLoadingDialog;
import cn.handanlutong.parking.http.HttpMethod;
import cn.handanlutong.parking.http.UrlConfig;
import cn.handanlutong.parking.utils.JieKouDiaoYongUtils;
import cn.handanlutong.parking.utils.LogUtils;
import cn.handanlutong.parking.utils.NetWorkUtil;
import cn.handanlutong.parking.utils.SharedPreferenceUtil;
import cn.handanlutong.parking.utils.ToastUtil;
import cn.handanlutong.parking.utils.UpdateManager;

/**
 * Created by ww on 2017/5/31.
 * 地图列表页
 */

public class DiTuActivity extends BaseActivity implements View.OnClickListener{
    RelativeLayout iv_back;
    TextView tv_back;
    LinearLayout ll_serch;
    LinearLayout ll_no_ditu;
    ListView lv_ditu;
    List<GarageBean.ResultBean> garage;
    LatLng mlatlng_my;
    String mCity;
    List<SFguize> list = new ArrayList<>();
    DiTuAdapter adapter;
    int i=0;
    public YWLoadingDialog mDialog;
    private SharedPreferenceUtil spUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ditu);
        spUtil = SharedPreferenceUtil.init(this, SharedPreferenceUtil.LOGIN_INFO, Activity.MODE_PRIVATE);
        iv_back = (RelativeLayout) findViewById(R.id.back);
        tv_back = (TextView) findViewById(R.id.tv_back);
        ll_serch = (LinearLayout) findViewById(R.id.ll_serch);
        ll_no_ditu = (LinearLayout) findViewById(R.id.ll_no_ditu);
        lv_ditu = (ListView) findViewById(R.id.lv_ditu);
        iv_back.setOnClickListener(this);
        tv_back.setOnClickListener(this);
        ll_serch.setOnClickListener(this);
        Intent intent=getIntent();
        if (intent!=null){
            mlatlng_my=intent.getParcelableExtra("latlng_my");
            mCity=intent.getStringExtra("city");
            garage = (List<GarageBean.ResultBean>)intent.getSerializableExtra("garage");
            if (garage!=null){
                if (garage.size()!=0){
                    ll_no_ditu.setVisibility(View.GONE);
                    lv_ditu.setVisibility(View.VISIBLE);
                    mDialog = new YWLoadingDialog(this);
                    mDialog.show();
                    postData(garage, i);
                    lv_ditu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                String newDistance = Utils.convertMtoKM((int) DistanceUtil.getDistance(
//                                        new LatLng(Double.valueOf(garage.get(position).getCczbY()), Double.valueOf(garage.get(position).getCczbX())), mlatlng));
                            Intent intent = new Intent(DiTuActivity.this, CarPlaceItemActivity.class);
                            intent.putExtra("garage", (Serializable) garage);
                            intent.putExtra("zindex", position);
                            intent.putExtra("distance", garage.get(position).getCcjl());
                            intent.putExtra("latlng_my", mlatlng_my);
                            startActivity(intent);
                        }
                    });
                }else{
                    ll_no_ditu.setVisibility(View.VISIBLE);
                    lv_ditu.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.tv_back:
                finish();
                break;
            case R.id.ll_serch:
                Intent intent = new Intent(this, SerchActivity.class);
                intent.putExtra("city", mCity);
                startActivity(intent);
                finish();
                break;
        }
    }
    public void postData(List<GarageBean.ResultBean> garage, int position) {
        if (NetWorkUtil.isNetworkConnected(DiTuActivity.this)) {
            JSONObject jsobj1=new JSONObject();
            try {
                JSONObject jsobj2 = new JSONObject();
                jsobj2.put("id", garage.get(position).getDjlx());

                jsobj1.put("parameter", jsobj2);
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.CCsf_GUIze(httpUtils, jsobj1, this, UrlConfig.CCsf_CODE);
        } else {
            ToastUtil.makeShortText(DiTuActivity.this, "请检查网络！");
        }
    }
    @Override
    public void onSuccessHttp(String responseInfo, int resultCode) {
        switch (resultCode) {
            case UrlConfig.CCsf_CODE:
                try {
                    JSONObject object = new JSONObject(responseInfo);
                    int code = object.optInt("code");
                    String result=object.optString("result");
                    Log.d("carplace", "88888收费标准"+object.toString());
                    if (code == 0) {
                        Gson gson=new Gson();
                        SFguize guize = gson.fromJson(result, SFguize.class);
                        list.add(guize);
                        if (list.size() == garage.size()){
                            mDialog.dismiss();
                            adapter = new DiTuAdapter(DiTuActivity.this, garage, mlatlng_my, list);
                            lv_ditu.setAdapter(adapter);
                        }else {
                            postData(garage , ++i);
                        }
                    } else if (code == 1001){ //版本更新 弹框
                        JSONObject obj1 = object.optJSONObject("result");
                        int newversionNo = Integer.parseInt(obj1.optString("versionNo"));
                        if (newversionNo > JieKouDiaoYongUtils.getVersionCode(this)) {
                            UpdateManager mUpdateManager = new UpdateManager(this);
                            mUpdateManager.showNoticeDialog(obj1.optString("versionPath"), newversionNo, obj1.optString("versionDescription"));
                        }
                    } else if (code == 1002){ //退出登录 弹框
                        JieKouDiaoYongUtils.loginTanKuan(this);
                    } else{
                        ToastUtil.makeShortText(DiTuActivity.this, result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
    @Override
    public void onFailureHttp(HttpException error, String msg, int resultCode) {
        super.onFailureHttp(error, msg, resultCode);
        LogUtils.d("访问失败");
        mDialog.dismiss();
    }
}
