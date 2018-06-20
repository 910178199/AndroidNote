package cn.handanlutong.parking.activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.adapter.BillRecordAdapter;
import cn.handanlutong.parking.bean.ParkingRecordHistoryVo;
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
 * Created by zhangyonggang on 2017/8/15.
 * 账单记录 列表
 */

public class BillRecordActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener2<ListView> {
    private ImageView iv_back_Bill;
    private PullToRefreshListView lv_Bill_Record;
    private SharedPreferenceUtil spUtil;
    private BillRecordAdapter mAdapter;
    private List<ParkingRecordHistoryVo> mList = null;
    private ArrayList<ParkingRecordHistoryVo> arrList = null;
    private boolean isRefresh = false;// false加载更多，true刷新
    private int pageNo = 1;
    private int totalPages;
    public YWLoadingDialog mDialog;

    @Override
    public void initView() {
        setContentView(R.layout.activity_bill_record);
        mList = new ArrayList<ParkingRecordHistoryVo>();
        arrList = new ArrayList<ParkingRecordHistoryVo>();
        lv_Bill_Record = (PullToRefreshListView) findViewById(R.id.lv_Bill_Record);
        mAdapter = new BillRecordAdapter(this, arrList);
        lv_Bill_Record.setAdapter(mAdapter);
        iv_back_Bill = (ImageView) findViewById(R.id.iv_back_Bill);
        View viewEmpty = LayoutInflater.from(this).inflate(R.layout.overtimetask_nulldata, null);
        lv_Bill_Record.setEmptyView(viewEmpty);
        getBillRecord();
    }

    /**
     * 获取账单记录
     */
    private void getBillRecord() {
        mDialog = new YWLoadingDialog(this);
        mDialog.show();
        if (NetWorkUtil.isNetworkConnected(this)) {
            spUtil = SharedPreferenceUtil.init(this, SharedPreferenceUtil.LOGIN_INFO, MODE_PRIVATE);
            JSONObject jsobj1 = new JSONObject();
            try {
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("page", pageNo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.AppUsergetBillRecords(httpUtils, jsobj1, this, UrlConfig.CAR_BILL_RECORD_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    @Override
    public void setLisener() {
        iv_back_Bill.setOnClickListener(this);
        lv_Bill_Record.setOnItemClickListener(this);
        lv_Bill_Record.setMode(PullToRefreshBase.Mode.BOTH);
        lv_Bill_Record.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back_Bill:
                this.finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onSuccessHttp(String responseInfo, int resultCode) {
        super.onSuccessHttp(responseInfo, resultCode);
        switch (resultCode) {
            case UrlConfig.CAR_BILL_RECORD_CODE:
                mDialog.dismiss();
                lv_Bill_Record.onRefreshComplete();
                LogUtils.d("获取账单信息成功：" + responseInfo);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String result = obj.optString("result");
                    if (code == 0) {
                        JSONObject obj1 = obj.optJSONObject("result");
                        if (obj1.has("totalCount")) {
                            int totalCount = obj1.optInt("totalCount");
                            totalPages = totalCount;
                            if (totalCount > 0) {
                                if (isRefresh) {
                                    arrList.clear();
                                }
                                if (mList.size() > 0) {
                                    mList.clear();
                                }
                                JSONArray jArray = obj1.optJSONArray("items");
                                for (int i = 0; i < jArray.length(); i++) {
                                    ParkingRecordHistoryVo billrecordvo = new ParkingRecordHistoryVo();
                                    JSONObject obj2 = jArray.optJSONObject(i);
                                    billrecordvo.setRwsj(obj2.optString("rwsj"));
                                    billrecordvo.setLwsj(obj2.optString("lwsj"));
                                    billrecordvo.setCcmc(obj2.optString("ccmc"));
                                    billrecordvo.setTcsc(obj2.optString("tcsc"));
                                    billrecordvo.setZje(obj2.optString("zje"));
                                    billrecordvo.setId(obj2.optLong("id"));
                                    billrecordvo.setSjly(obj2.optString("sjly"));
                                    billrecordvo.setTkje(obj2.optString("tkje"));
                                    billrecordvo.setZflx(obj2.optString("zflx"));
                                    mList.add(billrecordvo);
                                }
                                arrList.addAll(mList);
                                mAdapter.setData(arrList);
                                mAdapter.notifyDataSetChanged();
                            }
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
        lv_Bill_Record.onRefreshComplete();
        mDialog.dismiss();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ParkingRecordHistoryVo billrecordvo = arrList.get(position - 1);
        Intent intent = new Intent(this, BillDetailActivity.class);
        intent.putExtra("id", billrecordvo.getId());
        intent.putExtra("sjly", billrecordvo.getSjly());
        startActivity(intent);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        isRefresh = true;
        pageNo = 1;
        getBillRecord();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        isRefresh = false;
        if (pageNo < totalPages) {
            pageNo++;
            getBillRecord();
        } else {
            lv_Bill_Record.postDelayed(new Runnable() {
                @Override
                public void run() {
                    lv_Bill_Record.onRefreshComplete();
                    ToastUtil.makeShortText(BillRecordActivity.this, "全部数据已加载完毕！");
                }
            }, 800);
        }
    }
}
