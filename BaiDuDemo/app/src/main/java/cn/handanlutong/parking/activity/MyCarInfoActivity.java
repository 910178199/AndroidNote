package cn.handanlutong.parking.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.bean.MyCarBean;
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
 * Created by supermen on 2017/11/14.
 */

public class MyCarInfoActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back_ActionCenter;
    private SharedPreferenceUtil spUtil;
    private ToggleButton tog_btn_IsPayAuto, tog_btn_IsPayAuto2;
    private LinearLayout ll_mycar_shangchuan;
    private TextView tv_mycar_number, tv_mycar_info_shangchuan1, tv_mycar_info_shangchuan2, tv_renzheng_why,tv_mycar_color;
    private int type, position;
    private List<MyCarBean.ResultBean> mList;

    @Override
    public void initView() {
        setContentView(R.layout.activity_mycar_info);
        spUtil = SharedPreferenceUtil.init(this, SharedPreferenceUtil.LOGIN_INFO, Activity.MODE_PRIVATE);
        iv_back_ActionCenter= (ImageView) findViewById(R.id.iv_back_ActionCenter);
        tog_btn_IsPayAuto = (ToggleButton) findViewById(R.id.tog_btn_IsPayAuto);
        tog_btn_IsPayAuto2 = (ToggleButton) findViewById(R.id.tog_btn_IsPayAuto2);
        ll_mycar_shangchuan = (LinearLayout) findViewById(R.id.ll_mycar_shangchuan);
        tv_mycar_number = (TextView) findViewById(R.id.tv_mycar_number);
        tv_mycar_color = (TextView) findViewById(R.id.tv_mycar_color);
        tv_renzheng_why = (TextView) findViewById(R.id.tv_renzheng_why);
        tv_mycar_info_shangchuan1 = (TextView) findViewById(R.id.tv_mycar_info_shangchuan1);
        tv_mycar_info_shangchuan2 = (TextView) findViewById(R.id.tv_mycar_info_shangchuan2);
        Intent intent = getIntent();
        mList = (List<MyCarBean.ResultBean>) intent.getSerializableExtra("mycar_item");
        position = intent.getIntExtra("position", 0);
        tv_mycar_number.setText(""+mList.get(position).getHphm());
        if(mList.get(position).getZdzf().equals("1")){
            tog_btn_IsPayAuto.setEnabled(true);
            tog_btn_IsPayAuto.setChecked(true);
            LogUtils.d("position：" + position+"是打开的");
        }else if(mList.get(position).getZdzf().equals("0")){
            tog_btn_IsPayAuto.setEnabled(true);
            tog_btn_IsPayAuto.setChecked(false);
            LogUtils.d("position：" + position+"是关闭的");
        }else if(mList.get(position).getZdzf().equals("2")){
            tog_btn_IsPayAuto.setEnabled(false);
        }
        if (mList.get(position).getBdzt().equals("2") || mList.get(position).getBdzt().equals("1")){ //已认证 和 审核中
            ll_mycar_shangchuan.setEnabled(false);
            tv_mycar_info_shangchuan1.setTextColor(Color.parseColor("#999999"));
            tv_mycar_info_shangchuan2.setText("已上传");
        } else {
            ll_mycar_shangchuan.setEnabled(true);
            tv_mycar_info_shangchuan1.setTextColor(Color.parseColor("#333333"));
            tv_mycar_info_shangchuan2.setText("未上传");
        }

        if((mList.get(position).getCpys().equals("2"))){
            tv_mycar_color.setText("蓝色");
        }else if((mList.get(position).getCpys().equals("1"))){
            tv_mycar_color.setText("黄色");
        }else if((mList.get(position).getCpys().equals("5"))){
            tv_mycar_color.setText("绿色");
        }
    }

    @Override
    public void setLisener() {
        iv_back_ActionCenter.setOnClickListener(this);
        tog_btn_IsPayAuto.setOnClickListener(this);
        tog_btn_IsPayAuto2.setOnClickListener(this);
        tv_renzheng_why.setOnClickListener(this);
        ll_mycar_shangchuan.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back_ActionCenter:
                this.finish();
                break;
            case R.id.tog_btn_IsPayAuto:
                if(tog_btn_IsPayAuto.isChecked()){
                    type = 1;
                    isOpenAutoPay(mList.get(position).getId(), 1);
                }else {
                    type = 0;
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    final AlertDialog dialog = builder.create();
                    dialog.setCanceledOnTouchOutside(false);//设置外部不可点击
                    dialog.setCancelable(false);
                    View view2 = getLayoutInflater().inflate(R.layout.dialog_personal_info, null);
                    dialog.show();
                    dialog.setContentView(view2);
                    TextView tv_Cancle = (TextView) view2.findViewById(R.id.tv_Cancle);
                    TextView tv_Sure = (TextView) view2.findViewById(R.id.tv_Sure);
                    TextView tv_dialog_title = (TextView) view2.findViewById(R.id.tv_dialog_title);
                    tv_dialog_title.setText("确定关闭自动支付？");
                    tv_Cancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            tog_btn_IsPayAuto.setChecked(true);
                        }
                    });
                    tv_Sure.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            isOpenAutoPay(mList.get(position).getId(), 0);
                        }
                    });
                }
                break;
            case R.id.ll_mycar_shangchuan:
                Intent intent = new Intent(this, CarVehiclecardActivity.class);
                intent.putExtra("car_num", mList.get(position).getHphm());
                intent.putExtra("cpys", mList.get(position).getCpys() );
                intent.putExtra("type", "1");//去认证 1   去找回2
                startActivity(intent);
                break;
            case R.id.tog_btn_IsPayAuto2:
                if (tog_btn_IsPayAuto.isChecked()){
                    type = 1;
                } else {
                    type = 0;
                }
                break;
            case R.id.tv_renzheng_why:
                showAlertDialog();
                break;
            default:
                break;
        }
    }

    /**
     * 为什么要认证 弹框
     */
    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog dialog =builder.create();
        dialog.setCanceledOnTouchOutside(true);//设置外部可以点击
        dialog.setCancelable(true);
        View view = getLayoutInflater().inflate(R.layout.dialog_renzheng_why,null);
        dialog.show();
        dialog.setContentView(view);
    }

    /**
     * 开启自动支付接口
     * @param id
     * @param type
     */
    private void isOpenAutoPay(Long id, int type) {
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                JSONObject jsobj2 = new JSONObject();
                jsobj2.put("id", id);
                jsobj2.put("zdzf", type);

                jsobj1.put("parameter", jsobj2);
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.AppUseIsOpenAutoPay(httpUtils, jsobj1, this, UrlConfig.CAR_ISAUTOPAY_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    @Override
    public void onSuccessHttp(String responseInfo, int resultCode) {
        switch (resultCode) {
            case UrlConfig.CAR_ISAUTOPAY_CODE:
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String result = obj.optString("result");
                    if (code == 0) {
                        if (type == 1) {
                            LogUtils.d("开启自动支付成功:"+responseInfo);
                        } else if (type == 0) {
                            LogUtils.d("关闭自动支付成功:"+responseInfo);
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
    public void onFailureHttp(HttpException error, String msg, int resultCode) {
        super.onFailureHttp(error, msg, resultCode);
        LogUtils.d("访问失败");
    }
}
