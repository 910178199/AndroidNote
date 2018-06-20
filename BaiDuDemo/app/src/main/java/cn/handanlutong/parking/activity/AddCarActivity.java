package cn.handanlutong.parking.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.customview.YWLoadingDialog;
import cn.handanlutong.parking.http.HttpMethod;
import cn.handanlutong.parking.http.UrlConfig;
import cn.handanlutong.parking.utils.JieKouDiaoYongUtils;
import cn.handanlutong.parking.utils.LicenseKeyboardUtil;
import cn.handanlutong.parking.utils.LogUtils;
import cn.handanlutong.parking.utils.NetWorkUtil;
import cn.handanlutong.parking.utils.SharedPreferenceUtil;
import cn.handanlutong.parking.utils.StringUtil;
import cn.handanlutong.parking.utils.ToastUtil;
import cn.handanlutong.parking.utils.UpdateManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 添加车辆
 * Created by zhangyonggang on 2017/4/12.
 */

public class AddCarActivity extends BaseActivity implements View.OnClickListener{
    private TextView tv_Cancle,tv_Save;
    private EditText inputbox1,inputbox2,inputbox3,inputbox4,inputbox5,inputbox6,inputbox7,inputbox8;
    private LicenseKeyboardUtil keyboardUtil;
    private SharedPreferenceUtil spUtil;
    public YWLoadingDialog mDialog;
    private String carnum;
    private ImageView iv_blue,iv_yellow,iv_green;
    private EditText[] editTexts=null;
    private String  cpys="2";
    @Override
    public void initView() {
        setContentView(R.layout.activity_addcar);
        tv_Cancle= (TextView) findViewById(R.id.tv_Cancle);
        tv_Save= (TextView) findViewById(R.id.tv_Save);
        iv_blue= (ImageView) findViewById(R.id.iv_blue);
        iv_yellow= (ImageView) findViewById(R.id.iv_yellow);
        iv_green= (ImageView) findViewById(R.id.iv_green);

        inputbox1= (EditText) findViewById(R.id.et_car_license_inputbox1);
        inputbox2= (EditText) findViewById(R.id.et_car_license_inputbox2);
        inputbox3= (EditText) findViewById(R.id.et_car_license_inputbox3);
        inputbox4= (EditText) findViewById(R.id.et_car_license_inputbox4);
        inputbox5= (EditText) findViewById(R.id.et_car_license_inputbox5);
        inputbox6= (EditText) findViewById(R.id.et_car_license_inputbox6);
        inputbox7= (EditText) findViewById(R.id.et_car_license_inputbox7);
        inputbox8= (EditText) findViewById(R.id.et_car_license_inputbox8);
        editTexts=new EditText[]{ inputbox1,inputbox2,inputbox3,inputbox4,inputbox5,inputbox6,inputbox7,inputbox8};
        keyboardUtil = new LicenseKeyboardUtil(this,editTexts,iv_blue,iv_yellow,iv_green);
        keyboardUtil.showKeyboard();
        keyboardUtil.hideSystemKeyboard();
    }

    private void getCarList(String carnum) {
        mDialog = new YWLoadingDialog(this);
        mDialog.show();
        if (NetWorkUtil.isNetworkConnected(this)) {
            spUtil = SharedPreferenceUtil.init(this, SharedPreferenceUtil.LOGIN_INFO, MODE_PRIVATE);
            JSONObject jsobj1 = new JSONObject();
            try {
                JSONObject jsobj2 = new JSONObject();
                jsobj2.put("hphm", carnum);
                jsobj2.put("cpys", cpys);

                jsobj1.put("parameter", jsobj2);
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.AppUsergetAddCars(httpUtils, jsobj1, this, UrlConfig.CAR_ADD_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    @Override
    public void setLisener() {
        tv_Cancle.setOnClickListener(this);
        tv_Save.setOnClickListener(this);
        iv_blue.setOnClickListener(this);
        iv_yellow.setOnClickListener(this);
        iv_green.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_Cancle:
                this.finish();
                break;
            case R.id.iv_blue:
                keyboardUtil.BluePress();
                cpys="2";
                break;
            case R.id.iv_yellow:
                keyboardUtil.YellowPress();
                cpys="1";
                break;
            case R.id.iv_green:
                keyboardUtil.GreenPress();
                cpys="5";
                break;

            case R.id.tv_Save:
                int visibility = inputbox8.getVisibility();
                StringBuilder builder = new StringBuilder();
                String carnum1=inputbox1.getText().toString();
                if(!TextUtils.isEmpty(carnum1)){
                    builder.append(carnum1);
                }
                String carnum2=inputbox2.getText().toString();
                if(!TextUtils.isEmpty(carnum2)){
                    builder.append(carnum2);
                }
                String carnum3=inputbox3.getText().toString();
                if(!TextUtils.isEmpty(carnum3)){
                    builder.append(carnum3);
                }
                String carnum4=inputbox4.getText().toString();
                if(!TextUtils.isEmpty(carnum4)){
                    builder.append(carnum4);
                }
                String carnum5=inputbox5.getText().toString();
                if(!TextUtils.isEmpty(carnum5)){
                    builder.append(carnum5);
                }
                String carnum6=inputbox6.getText().toString();
                if(!TextUtils.isEmpty(carnum6)){
                    builder.append(carnum6);
                }
                String carnum7=inputbox7.getText().toString();
                if(!TextUtils.isEmpty(carnum7)){
                    builder.append(carnum7);
                }
                if(visibility==View.VISIBLE){
                    String carnum8=inputbox8.getText().toString();
                    if(!TextUtils.isEmpty(carnum8)){
                        builder.append(carnum8);
                    }
                }
                String CarNumber=builder.toString();
                if(StringUtil.isEmpty(CarNumber)){
                    ToastUtil.makeShortText(this,"车牌号码不能为空");
                    return;
                }
                if(visibility==View.VISIBLE){
                    if(CarNumber.length()!=8){
                        ToastUtil.makeShortText(this,"请输入正确的车牌号码");
                        return;
                    }
                }else {
                    if(CarNumber.length()!=7){
                        ToastUtil.makeShortText(this,"请输入正确的车牌号码");
                        return;
                    }
                }
                carnum = CarNumber;
                getCarList(CarNumber);
                break;
            default:
                break;
        }
    }




    /**
     * 添加车辆成功后显示的窗口
     */
    public void showAlertDialog(int layout){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog dialog =builder.create();
        View view = getLayoutInflater().inflate(layout,null);
        dialog.show();
        dialog.setContentView(view);
    }

    @Override
    public void onSuccessHttp(String responseInfo, int resultCode) {
        super.onSuccessHttp(responseInfo, resultCode);
        switch (resultCode){
            case UrlConfig.CAR_ADD_CODE:
                mDialog.dismiss();
                LogUtils.d("添加车辆成功："+responseInfo);
                try {
                    JSONObject object = new JSONObject(responseInfo);
                    int code = object.optInt("code");
                    String result=object.optString("result");
                    String message=object.optString("message");
                    if(code == 0){
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        AlertDialog dialog =builder.create();
                        dialog.setCanceledOnTouchOutside(false);//设置外部不可点击
                        dialog.setCancelable(false);
                        View view = getLayoutInflater().inflate(R.layout.dialog_addcar_success,null);
                        dialog.show();
                        dialog.setContentView(view);
                        Message msg=handler.obtainMessage();
                        msg.what=0x1;
                        msg.obj=dialog;
                        handler.sendMessageDelayed(msg,2000);
                    } else if (code == 3003){ //去找回的 弹框
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        final AlertDialog dialog =builder.create();
                        View view = getLayoutInflater().inflate(R.layout.dialog_personal_info,null);
                        dialog.show();
                        dialog.setContentView(view);
                        TextView tv_Cancle= (TextView) view.findViewById(R.id.tv_Cancle);
                        TextView tv_Sure= (TextView) view.findViewById(R.id.tv_Sure);
                        tv_Sure.setText("找回");
                        TextView tv_dialog_title= (TextView) view.findViewById(R.id.tv_dialog_title);
                        TextView tv_AddCar_tishiyu= (TextView) view.findViewById(R.id.tv_AddCar_tishiyu2);
                        tv_dialog_title.setText(result);
                        tv_AddCar_tishiyu.setText(message);
                        tv_Cancle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        tv_Sure.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                Intent intent = new Intent(AddCarActivity.this, CarVehiclecardActivity.class);
                                intent.putExtra("car_num", carnum);
                                intent.putExtra("cpys", cpys);
                                intent.putExtra("type", "2"); //去认证 1   去找回2
                                startActivity(intent);
                                finish();
                            }
                        });
                    } else if (code == 1001){ //版本更新 弹框
                        JSONObject obj1 = object.optJSONObject("result");
                        int newversionNo = Integer.parseInt(obj1.optString("versionNo"));
                        if (newversionNo > JieKouDiaoYongUtils.getVersionCode(this)) {
                            UpdateManager mUpdateManager = new UpdateManager(this);
                            mUpdateManager.showNoticeDialog(obj1.optString("versionPath"), newversionNo, obj1.optString("versionDescription"));
                        }
                    } else if (code == 1002){ //退出登录 弹框
                        JieKouDiaoYongUtils.loginTanKuan(this);
                    } else {  //其他情况的 弹框
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        final AlertDialog dialog =builder.create();
                        View view = getLayoutInflater().inflate(R.layout.dialog_addcar_full,null);
                        dialog.show();
                        dialog.setContentView(view);
                        Button btn_Sure= (Button) view.findViewById(R.id.btn_Sure);
                        TextView tv_AddCar_full= (TextView) view.findViewById(R.id.tv_AddCar_full);
                        TextView tv_AddCar_tishiyu= (TextView) view.findViewById(R.id.tv_AddCar_tishiyu2);
                        tv_AddCar_full.setText(result);
                        if (! message.equals("操作成功")){
                            tv_AddCar_tishiyu.setText(message);
                        }else {
                            tv_AddCar_tishiyu.setText("");
                        }
                        btn_Sure.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
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
    public void onFailureHttp(HttpException error, String msg, int resultCode) {
        super.onFailureHttp(error, msg, resultCode);
        LogUtils.d("访问失败");
        mDialog.dismiss();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0x1:
                    AlertDialog dialog= (AlertDialog) msg.obj;
                    dialog.dismiss();
                    AddCarActivity.this.finish();
                    break;
                default:
                    break;
            }
        }
    };
}
