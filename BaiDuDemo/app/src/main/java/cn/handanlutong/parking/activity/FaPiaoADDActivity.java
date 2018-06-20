package cn.handanlutong.parking.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
 * Created by ww on 2017/11/21.
 */

public class FaPiaoADDActivity extends BaseActivity implements View.OnClickListener{

    private RelativeLayout back;
    private RadioButton btn_fapiao_qiye, btn_fapiao_geren;
    private Button btn_go;
    private LinearLayout ll_fapiao_more, ll_fapiao_shuihao;
    private YWLoadingDialog mDialog;
    private SharedPreferenceUtil spUtil;
    private View view_fapiao_xian;
    private EditText et_fapiao_shuihao, et_fapiao_zje, et_fapiao_youjian, et_fapiao_taitou, et_fapiao_mobile;
    private String fapiao_zje, ghfqylx, str_ghfDz, str_ghfYhzh, str_bz;
    private Map<String, String> map_message = new HashMap<>();
    private Map<String, String> stringMap = null;
    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_fapiao_add_tianxie);
        spUtil = SharedPreferenceUtil.init(this, SharedPreferenceUtil.LOGIN_INFO, MODE_PRIVATE);
        back = (RelativeLayout) findViewById(R.id.back);
        btn_fapiao_qiye = (RadioButton) findViewById(R.id.btn_fapiao_qiye);
        btn_fapiao_geren = (RadioButton) findViewById(R.id.btn_fapiao_geren);
        view_fapiao_xian = findViewById(R.id.view_fapiao_xian);
        et_fapiao_shuihao = (EditText) findViewById(R.id.et_fapiao_shuihao);
        et_fapiao_zje = (EditText) findViewById(R.id.et_fapiao_zje);
        et_fapiao_taitou = (EditText) findViewById(R.id.et_fapiao_taitou);
        et_fapiao_youjian = (EditText) findViewById(R.id.et_fapiao_youjian);
        et_fapiao_mobile = (EditText) findViewById(R.id.et_fapiao_mobile);
        // 设置 只能输入一位小数点和数字 保留小数点后两位
        et_fapiao_zje.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        et_fapiao_zje.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //  numInt = TextUtils.isEmpty(num) ? 1 : Integer.parseInt(num);
                //优惠券必须要达到的金额Integer.parseInt(fullPrice);
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                        et_fapiao_zje.setText(s);
                        et_fapiao_zje.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    et_fapiao_zje.setText(s);
                    et_fapiao_zje.setSelection(2);
                }
                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        et_fapiao_zje.setText(s.subSequence(0, 1));
                        et_fapiao_zje.setSelection(1);
                        return;
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        btn_go = (Button) findViewById(R.id.btn_go);
        ll_fapiao_more = (LinearLayout) findViewById(R.id.ll_fapiao_more);
        ll_fapiao_shuihao = (LinearLayout) findViewById(R.id.ll_fapiao_shuihao);
        JieKouDiaoYongUtils.NoShuRuBiaoQingFuHao(this, et_fapiao_taitou);
        Intent intent = getIntent();
        fapiao_zje = intent.getStringExtra("fapiao_zje");
        if (! StringUtil.isEmpty(fapiao_zje)){
            et_fapiao_zje.setHint("可开票额度￥" + fapiao_zje);
        }else {
            et_fapiao_zje.setHint("可开票额度￥0.00");
        }
        SharedPreferenceUtil spUtil2 = SharedPreferenceUtil.init(this,SharedPreferenceUtil.FaPiaoMore, MODE_PRIVATE);
        str_ghfDz = spUtil2.getString("ghfDz");
        str_ghfYhzh = spUtil2.getString("ghfYhzh");
        str_bz = spUtil2.getString("bz");
    }

    @Override
    public void setLisener() {
        super.setLisener();
        back.setOnClickListener(this);
        btn_fapiao_qiye.setOnClickListener(this);
        btn_fapiao_geren.setOnClickListener(this);
        ll_fapiao_more.setOnClickListener(this);
        btn_go.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.btn_fapiao_qiye:
                btn_fapiao_qiye.setSelected(true);
                btn_fapiao_geren.setSelected(false);
                btn_fapiao_qiye.setChecked(true);
                btn_fapiao_geren.setChecked(false);
                btn_fapiao_qiye.setTextColor(Color.parseColor("#29c08b"));
                btn_fapiao_geren.setTextColor(Color.parseColor("#333333"));
                btn_fapiao_qiye.setBackgroundResource(R.mipmap.bg_lvse_kuang);
                btn_fapiao_geren.setBackgroundResource(R.mipmap.bg_huise_kuang);
                ll_fapiao_shuihao.setVisibility(View.VISIBLE);
                view_fapiao_xian.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_fapiao_geren:
                btn_fapiao_qiye.setSelected(false);
                btn_fapiao_geren.setSelected(true);
                btn_fapiao_qiye.setChecked(false);
                btn_fapiao_geren.setChecked(true);
                btn_fapiao_geren.setTextColor(Color.parseColor("#29c08b"));
                btn_fapiao_qiye.setTextColor(Color.parseColor("#333333"));
                btn_fapiao_geren.setBackgroundResource(R.mipmap.bg_lvse_kuang);
                btn_fapiao_qiye.setBackgroundResource(R.mipmap.bg_huise_kuang);
                ll_fapiao_shuihao.setVisibility(View.GONE);
                view_fapiao_xian.setVisibility(View.GONE);
                InputMethodManager imm1 = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm1.hideSoftInputFromWindow(et_fapiao_shuihao.getWindowToken(), 0);
                break;
            case R.id.ll_fapiao_more:
                Intent intent = new Intent(this, FaPiaoMoreMessageActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.btn_go:
                if (ll_fapiao_shuihao.getVisibility() == View.VISIBLE){ //  0 == VISIBLE , 4 == INVISIBLE , 8 == GONE
                    ghfqylx = "01"; //企业
                    if (StringUtil.isEmpty(et_fapiao_taitou.getText().toString().trim())){
                        ToastUtil.makeShortText(this, "发票抬头不能为空");
                        return;
                    }
                    if (StringUtil.isEmpty(et_fapiao_shuihao.getText().toString().trim())){
                        ToastUtil.makeShortText(this, "税号不能为空");
                        return;
                    }
                    if (StringUtil.isEmpty(et_fapiao_zje.getText().toString().trim())){
                        ToastUtil.makeShortText(this, "可开发票金额不能为空");
                        return;
                    }
                    if (StringUtil.isEmpty(et_fapiao_mobile.getText().toString().trim())){
                        ToastUtil.makeShortText(this, "手机号不能为空");
                        return;
                    }
                    if (StringUtil.isEmpty(et_fapiao_youjian.getText().toString().trim())){
                        ToastUtil.makeShortText(this, "电子邮箱不能为空");
                        return;
                    }
                }else {
                    ghfqylx = "03"; //个人
                    if (StringUtil.isEmpty(et_fapiao_taitou.getText().toString().trim())){
                        ToastUtil.makeShortText(this, "发票抬头不能为空");
                        return;
                    }
                    if (StringUtil.isEmpty(et_fapiao_zje.getText().toString().trim())){
                        ToastUtil.makeShortText(this, "可开发票金额不能为空");
                        return;
                    }
                    if (StringUtil.isEmpty(et_fapiao_youjian.getText().toString().trim())){
                        ToastUtil.makeShortText(this, "电子邮箱不能为空");
                        return;
                    }
                }
                map_message.put("ghfqylx", ghfqylx); // 01 企业 02 机关事业单位 03个人 04其他
                map_message.put("ghfmc", et_fapiao_taitou.getText().toString().trim()); //发票抬头
                map_message.put("ghfNsrsbh", et_fapiao_shuihao.getText().toString().trim()); //纳税人识别号
                map_message.put("je", ""+(int)(Double.parseDouble(et_fapiao_zje.getText().toString().trim())*100)); //开票金额
                map_message.put("ghfSj", et_fapiao_mobile.getText().toString().trim()); //联系电话
                if (!StringUtil.isEmpty(str_ghfDz) && !StringUtil.isEmpty(str_ghfYhzh) ){
                    map_message.put("ghfDz", str_ghfDz);//更多信息  地址
                    map_message.put("ghfYhzh", str_ghfYhzh);//更多信息  开户行及账户
                    map_message.put("bz", str_bz);//备注 说明
                    SharedPreferenceUtil spUtil2 = SharedPreferenceUtil.init(this, SharedPreferenceUtil.FaPiaoMore, MODE_PRIVATE);
                    spUtil2.put("ghfDz", "");
                    spUtil2.put("ghfYhzh", "");
                    spUtil2.put("bz", "");
                }
                map_message.put("ghfEmail", et_fapiao_youjian.getText().toString().trim()); //电子邮箱
                // HashMap:没有排序（乱的）  LinkedHashMap:默认原样输出
                postCommitFaPiaoData();
                break;
            default:
                break;
        }
    }

    /**
     * 发票信息提交 接口
     */
    private void postCommitFaPiaoData() {
        mDialog = new YWLoadingDialog(this);
        mDialog.show();
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                jsobj1.put("parameter", JSON.toJSONString(map_message));
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.postCommitFaPiaoData(httpUtils, jsobj1, this, UrlConfig.Commit_FaPiao_Message_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    @Override
    public void onSuccessHttp(String responseInfo, int resultCode) {
        super.onSuccessHttp(responseInfo, resultCode);
        switch (resultCode) {
            case UrlConfig.Commit_FaPiao_Message_CODE:
                mDialog.dismiss();
                LogUtils.d("发票信息提交成功：" + responseInfo);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String message = obj.optString("message");
                    if (code == 0) {
                        ToastUtil.makeShortText(this, "提交成功");
                        finish();
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
        mDialog.dimissFail();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferenceUtil spUtil2 = SharedPreferenceUtil.init(this,SharedPreferenceUtil.FaPiaoMore, MODE_PRIVATE);
        str_ghfDz = spUtil2.getString("ghfDz");
        str_ghfYhzh = spUtil2.getString("ghfYhzh");
        str_bz = spUtil2.getString("bz");
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        try {
//            if (resultCode == 0){
//                stringMap = (Map<String, String>) data.getSerializableExtra("message");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
