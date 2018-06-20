package cn.handanlutong.parking.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.adapter.FaPiaoAdapter;
import cn.handanlutong.parking.bean.ParkingFP;
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

public class FaPiaoHistoryActivity extends BaseActivity implements View.OnClickListener, PullToRefreshBase.OnRefreshListener2<ListView>{
    private PullToRefreshListView mScrollView;
    private RelativeLayout back;
    private LinearLayout ll_MyCarNodata;
    public YWLoadingDialog mDialog;
    private SharedPreferenceUtil spUtil;
    private int pageNo = 1;
    private int totalPages;
    private boolean isRefresh = false;// false加载更多，true刷新
    private List<ParkingFP> mlist;
    private List<ParkingFP> arraylist = new ArrayList<>();
    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_fapiao_history);
        spUtil = SharedPreferenceUtil.init(this, SharedPreferenceUtil.LOGIN_INFO, MODE_PRIVATE);
        back = (RelativeLayout) findViewById(R.id.back);
        mScrollView = (PullToRefreshListView) findViewById(R.id.mScrollView);
        ll_MyCarNodata = (LinearLayout) findViewById(R.id.ll_MyCarNodata);
        postFaPiaoHistoryData();
    }

    @Override
    public void setLisener() {
        super.setLisener();
        back.setOnClickListener(this);
        mScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        mScrollView.setOnRefreshListener(this);
    }

    /**
     * 获取 开发票历史记录
     */
    private void postFaPiaoHistoryData() {
        mDialog = new YWLoadingDialog(this);
        mDialog.show();
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                jsobj1.put("page", pageNo);
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.postFaPiaoHistoryData(httpUtils, jsobj1, this, UrlConfig.GET_FaPiao_History_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onSuccessHttp(String responseInfo, int resultCode) {
        super.onSuccessHttp(responseInfo, resultCode);
        switch (resultCode) {
            case UrlConfig.GET_FaPiao_History_CODE:
                mDialog.dismiss();
                mScrollView.onRefreshComplete();
                LogUtils.d("获取发票历史记录：" + responseInfo);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String result = obj.optString("result");
                    if (code == 0) {
                        Gson gson = new Gson();
                        JSONObject obj1 = obj.optJSONObject("result");
                        int z_totalCount = obj1.optInt("totalCount");
                        JSONArray array = obj1.optJSONArray("items");
                        int totalCount = z_totalCount;
                        totalPages = totalCount;
                        if (totalCount >= 0) {
                            if (array.length() > 0){
                                ll_MyCarNodata.setVisibility(View.GONE);
                                mScrollView.setVisibility(View.VISIBLE);
                                if (isRefresh) {
                                    arraylist.clear();
                                }
                                mlist = gson.fromJson(array.toString(), new TypeToken<List<ParkingFP>>(){}.getType());
                                arraylist.addAll(mlist);
                                mScrollView.setAdapter(new FaPiaoAdapter(this, arraylist));
                            } else {
                                ll_MyCarNodata.setVisibility(View.VISIBLE);
                                mScrollView.setVisibility(View.GONE);
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
    public void onFailureHttp(HttpException error, String msg) {
        super.onFailureHttp(error, msg);
        LogUtils.d("获取失败");
        mDialog.dimissFail();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        isRefresh = true;
        pageNo = 1;
        postFaPiaoHistoryData();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        isRefresh = false;
        if (pageNo < totalPages) {
            pageNo++;
            postFaPiaoHistoryData();
        } else {
            mScrollView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScrollView.onRefreshComplete();
                    ToastUtil.makeShortText(FaPiaoHistoryActivity.this, "全部数据已加载完毕！");
                }
            }, 800);
        }
    }
}
