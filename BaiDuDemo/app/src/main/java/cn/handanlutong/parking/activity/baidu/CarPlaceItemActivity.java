package cn.handanlutong.parking.activity.baidu;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.navi.NaviParaOption;
import com.baidu.mapsdkplatform.comapi.location.CoordinateType;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.activity.BaseActivity;
import cn.handanlutong.parking.bean.GarageBean;
import cn.handanlutong.parking.bean.SFguize;
import cn.handanlutong.parking.customview.YWLoadingDialog;
import cn.handanlutong.parking.http.HttpMethod;
import cn.handanlutong.parking.http.UrlConfig;
import cn.handanlutong.parking.utils.JieKouDiaoYongUtils;
import cn.handanlutong.parking.utils.LogUtils;
import cn.handanlutong.parking.utils.NavigateUtils;
import cn.handanlutong.parking.utils.NetWorkUtil;
import cn.handanlutong.parking.utils.SharedPreferenceUtil;
import cn.handanlutong.parking.utils.StringUtil;
import cn.handanlutong.parking.utils.ToastUtil;
import cn.handanlutong.parking.utils.UpdateManager;

/**
 * Created by ww on 2017/4/25.
 * 车场详情页
 */

public class CarPlaceItemActivity extends BaseActivity implements View.OnClickListener {
    RelativeLayout back;
    private TextView tv_guize, tv_dizhi, tv_xxdizhi, tv_residuals, tv_sum, tv_distance, tv_sfmc;
    private ImageView iv_carplace_url;
    private Button btn_go;
    private List<GarageBean.ResultBean> garage;
    private BitmapUtils bitmapUtils = null;
    private LatLng mlatlng_my;
    private int index;
    private SFguize guize;
    private CoordinateType mCoordinateType = null;
    private SharedPreferenceUtil spUtil;
    private YWLoadingDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carplace);
        spUtil = SharedPreferenceUtil.init(this, SharedPreferenceUtil.LOGIN_INFO, Activity.MODE_PRIVATE);
        back = (RelativeLayout) findViewById(R.id.back);
        tv_guize = (TextView) findViewById(R.id.tv_shoufei_guize);
        tv_dizhi = (TextView) findViewById(R.id.tv_place_dizhi);
        tv_xxdizhi = (TextView) findViewById(R.id.tv_place_xxdizhi);
        tv_residuals = (TextView) findViewById(R.id.residuals);
        tv_sum = (TextView) findViewById(R.id.sum);
        tv_distance = (TextView) findViewById(R.id.distance);
        iv_carplace_url = (ImageView) findViewById(R.id.iv_carplace_url);
        tv_sfmc = (TextView) findViewById(R.id.sfmc);
        btn_go = (Button) findViewById(R.id.btn_go);
        back.setOnClickListener(this);
        tv_guize.setOnClickListener(this);
        btn_go.setOnClickListener(this);
        bitmapUtils = new BitmapUtils(this);
        Intent intent = getIntent();
        if (intent != null) {
            garage = (List<GarageBean.ResultBean>) intent.getSerializableExtra("garage");
            index = intent.getIntExtra("zindex", 0);
            tv_dizhi.setText(garage.get(index).getCcmc());
            tv_xxdizhi.setText(garage.get(index).getCcqc());
            tv_residuals.setText(garage.get(index).getKbwsl());//剩余泊位数
            tv_sum.setText(garage.get(index).getZbwsl());//总泊位数
            String distan = intent.getStringExtra("distance");
            tv_distance.setText(distan);
            bitmapUtils.display(iv_carplace_url, garage.get(index).getCctp());
            mlatlng_my = intent.getParcelableExtra("latlng_my");
            postData();
        }

        //初始化百度导航
        NavigateUtils.getInstance().initNavigate(this);
    }

    /**
     * 收费标准接口
     */
    private void postData() {
        mDialog = new YWLoadingDialog(this);
        mDialog.show();
        if (NetWorkUtil.isNetworkConnected(CarPlaceItemActivity.this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                JSONObject jsobj2 = new JSONObject();
                jsobj2.put("id", garage.get(index).getDjlx());

                jsobj1.put("parameter", jsobj2);
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.CCsf_GUIze(httpUtils, jsobj1, CarPlaceItemActivity.this, UrlConfig.CCsf_CODE);
        } else {
            ToastUtil.makeShortText(CarPlaceItemActivity.this, "请检查网络！");
        }
    }

    @Override
    public void onSuccessHttp(String responseInfo, int resultCode) {
        super.onSuccessHttp(responseInfo, resultCode);
        switch (resultCode) {
            case UrlConfig.CCsf_CODE:
                mDialog.dismiss();
                LogUtils.d("车场详情请求收费标准成功：" + responseInfo);
                JSONObject object = null;
                int code = 0;
                String result = null;
                try {
                    object = new JSONObject(responseInfo);
                    code = object.optInt("code");
                    result = object.optString("result");
                    if (code == 0) {
                        Gson gson = new Gson();
                        guize = gson.fromJson(result, SFguize.class);
                        if (guize.getSfmc().equals("按次收费")) {
                            tv_sfmc.setText(StringUtil.convertFentoYuanWithout(guize.getSfje()) + "元/次");
                        } else {
                            tv_sfmc.setText(StringUtil.convertFentoYuanWithout(guize.getSfje()) + "元/" + guize.getSfmc());
                        }
                    } else if (code == 1001) { //版本更新 弹框
                        JSONObject obj1 = object.optJSONObject("result");
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_shoufei_guize:
                final AlertDialog dialo = new AlertDialog.Builder(CarPlaceItemActivity.this).create();
                dialo.show();
                dialo.getWindow().setContentView(R.layout.guize_dialog);
                RelativeLayout rl = (RelativeLayout) dialo.getWindow().findViewById(R.id.rl_guize);
                TextView btn_know = (TextView) dialo.getWindow().findViewById(R.id.tv_bt);
                TextView wsn_know = (TextView) dialo.getWindow().findViewById(R.id.tv_ws);
                TextView zhu = (TextView) dialo.getWindow().findViewById(R.id.tv_zhu);
                if (guize.getSflx() == 1) {//时段收费
                    btn_know.setText("日间（" + guize.getBtsjd() + "）" + StringUtil.convertFentoYuanWithout(guize.getBtdwjg()) + "元/" + guize.getBtdwsc() + "分钟");
                    wsn_know.setText("夜间（" + guize.getWssjd() + "）" + StringUtil.convertFentoYuanWithout(guize.getWsdwjg()) + "元/" + guize.getWsdwsc() + "分钟");
                    zhu.setText("注：" + guize.getBz());
                } else if (guize.getSflx() == 2) {//全日收费
                    btn_know.setText("");
                    wsn_know.setText("全天：" + StringUtil.convertFentoYuanWithout(guize.getBtdwjg()) + "元/" + guize.getBtdwsc() + "分钟");
                    zhu.setText("注：" + guize.getBz());
                } else if (guize.getSflx() == 3) {//按次收费
                    zhu.setText("");
                    btn_know.setText("");
                    wsn_know.setText("按次收费：" + StringUtil.convertFentoYuanWithout(guize.getBtdwjg()) + "元/次                                     ");
                }
                rl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialo.dismiss();
                    }
                });
                break;
            case R.id.btn_go:
                //跳到百度地图导航
                NaviParaOption option = new NaviParaOption().startName("我的位置").startPoint(mlatlng_my).endName("我要去的车场")
                        .endPoint(new LatLng(Double.valueOf(garage.get(index).getCczbY()), Double.valueOf(garage.get(index).getCczbX())));

//                BaiduMapNavigation.openBaiduMapNavi(option, CarPlaceItemActivity.this);
                //百度经纬度坐标
                NavigateUtils.getInstance().openNavigate(option);
                break;

        }
    }
}