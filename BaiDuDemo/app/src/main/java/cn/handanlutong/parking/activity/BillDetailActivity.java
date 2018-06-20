package cn.handanlutong.parking.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.adapter.LVbillAdapter;
import cn.handanlutong.parking.bean.BillVo;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyonggang on 2017/8/15.
 * 我的账单详情
 */

public class BillDetailActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back_Bill_Detail;
    private long id;
    private SharedPreferenceUtil spUtil;
    private TextView tv_stopname,tv_OrderNum,tv_backMoney,tv_backMoney_date,tv_creatTiem,tv_noPay;
    private String sjly, str_sjly;
    private Long str_id;
    private ListView lv_bill;
    private RelativeLayout rl_Associated_Order, rl_tuikuan;
    private YWLoadingDialog mDialog;
    @Override
    public void initView() {
        setContentView(R.layout.activity_bill_detail);
        Intent intent = getIntent();
        id = intent.getLongExtra("id", 0);
        sjly=intent.getStringExtra("sjly");
        iv_back_Bill_Detail = (ImageView) findViewById(R.id.iv_back_Bill_Detail);
        tv_stopname= (TextView) findViewById(R.id.tv_stopname);
        tv_OrderNum= (TextView) findViewById(R.id.tv_OrderNum);
        tv_backMoney= (TextView) findViewById(R.id.tv_backMoney);
        tv_backMoney_date= (TextView) findViewById(R.id.tv_backMoney_date);
        tv_creatTiem= (TextView) findViewById(R.id.tv_creatTiem);
        tv_noPay= (TextView) findViewById(R.id.tv_noPay);
        rl_Associated_Order= (RelativeLayout) findViewById(R.id.rl_Associated_Order);
        rl_tuikuan= (RelativeLayout) findViewById(R.id.rl_tuikuan);
        lv_bill = (ListView) findViewById(R.id.lv_bill);

        getBillRecordDetail();
    }

    /**
     * 获得账单详情
     */
    private void getBillRecordDetail() {
        mDialog = new YWLoadingDialog(this);
        mDialog.show();
        if (NetWorkUtil.isNetworkConnected(this)) {
            spUtil = SharedPreferenceUtil.init(this, SharedPreferenceUtil.LOGIN_INFO, MODE_PRIVATE);
            JSONObject jsobj1 = new JSONObject();
            JSONObject jsobj2 = new JSONObject();
            try {
                jsobj2.put("id", id);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("parameter",jsobj2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.AppUsergetBillRecordDetails(httpUtils, jsobj1, this, UrlConfig.CAR_BILL_RECORD_DETAIL_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    @Override
    public void setLisener() {
        iv_back_Bill_Detail.setOnClickListener(this);
        rl_Associated_Order.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back_Bill_Detail:
                this.finish();
                break;
            case R.id.rl_Associated_Order:
                Intent intent = new Intent(this, MyRecordDetailCompleteActivity.class);
                intent.putExtra("id", str_id);
                intent.putExtra("sjly", str_sjly);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void setDate(JSONObject obj1) {
        str_id = obj1.optLong("id");
        str_sjly = obj1.optString("sjly");
        tv_stopname.setText(obj1.optString("ccmc"));
        tv_OrderNum.setText(obj1.optString("ddbh"));
        tv_creatTiem.setText(obj1.optString("lwsj"));
        double tkje=(double)Integer.parseInt(obj1.optString("tkje"))/100;
        if(tkje>0){
            rl_tuikuan.setVisibility(View.VISIBLE);
            tv_backMoney.setText("已退款￥"+String.format("%.2f",tkje));
            tv_backMoney_date.setText(obj1.optString("tksj"));
        } else {
            rl_tuikuan.setVisibility(View.GONE);
        }


    }

    /**
     * 设置Listview的高度
     */
    public void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    @Override
    public void onSuccessHttp(String responseInfo, int resultCode) {
        super.onSuccessHttp(responseInfo, resultCode);
        switch (resultCode){
            case UrlConfig.CAR_BILL_RECORD_DETAIL_CODE:
                mDialog.dismiss();
                LogUtils.d("获取账单详情成功："+responseInfo);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String result = obj.optString("result");
                    if(code == 0){
                        JSONObject obj1=obj.optJSONObject("result");
                        JSONArray jArray = obj.optJSONArray("result_Info");
                        if(jArray.length()>0){
                            tv_noPay.setVisibility(View.GONE);
                            List<BillVo> list=new ArrayList<>();
                            for (int i = 0; i < jArray.length(); i++){
                                BillVo billvo=new BillVo();
                                JSONObject obj2=jArray.optJSONObject(i);
                                billvo.setMoney(obj2.optString("je"));
                                billvo.setZfqd(obj2.optString("zfqd"));
                                billvo.setZfsj(obj2.optString("zfsj"));
                                list.add(billvo);
                            }
                            lv_bill.setAdapter(new LVbillAdapter(this, list));
                            setListViewHeight(lv_bill);
                        }
                        setDate(obj1);
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
        LogUtils.d("获取失败！");
        mDialog.dismiss();
    }
}
