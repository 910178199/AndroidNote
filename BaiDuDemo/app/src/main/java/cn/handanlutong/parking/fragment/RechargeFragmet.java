package cn.handanlutong.parking.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.adapter.ExpenseAndRechargeAdapter;
import cn.handanlutong.parking.bean.RchargeRecordListBean;
import cn.handanlutong.parking.customview.YWLoadingDialog;
import cn.handanlutong.parking.http.AnsynHttpRequest;
import cn.handanlutong.parking.http.HttpMethod;
import cn.handanlutong.parking.http.UrlConfig;
import cn.handanlutong.parking.utils.JieKouDiaoYongUtils;
import cn.handanlutong.parking.utils.LogUtils;
import cn.handanlutong.parking.utils.NetWorkUtil;
import cn.handanlutong.parking.utils.SharedPreferenceUtil;
import cn.handanlutong.parking.utils.ToastUtil;
import cn.handanlutong.parking.utils.UiUtils;
import cn.handanlutong.parking.utils.UpdateManager;

/**
 * 充值明细
 * Created by zhangyonggang on 2017/4/12.
 */

public class RechargeFragmet extends BaseFragment implements PullToRefreshBase.OnRefreshListener2<ListView>{
    private PullToRefreshListView lv_Expense_Recharge;
    private ExpenseAndRechargeAdapter adapter;
    private boolean isPrepared;
    private SharedPreferenceUtil spUtil;
    private List<RchargeRecordListBean.ResultBean.ItemsBean> arrList;
    private boolean isRefresh = false;// false加载更多，true刷新
    private int pageNo = 1;
    private int totalPages;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense, null);
        isPrepared = true;
        initView(view);
        lazyLoad();
        return view;
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        isPrepared = false;
        pageNo = 1;
        arrList.clear();
        getRechargeList();
    }

    private void getRechargeList() {
        if (NetWorkUtil.isNetworkConnected(UiUtils.getApplication())) {
            spUtil = SharedPreferenceUtil.init(UiUtils.getApplication(), SharedPreferenceUtil.LOGIN_INFO, Activity.MODE_PRIVATE);
            JSONObject jsobj1 = new JSONObject();
            try {
                jsobj1.put("page", pageNo);
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(getActivity()));
                jsobj1.put("authKey", spUtil.getString("authkey"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.AppUserRechargeGetList(httpUtils, jsobj1, this, UrlConfig.RECHARGE_PAYIN_CODE);
        } else {
            ToastUtil.makeShortText(UiUtils.getApplication(), "请检查网络！");
        }
    }

    private void initView(View view) {
        lv_Expense_Recharge = (PullToRefreshListView) view.findViewById(R.id.lv_Expense_Recharge);
        View viewEmpty = LayoutInflater.from(getActivity()).inflate(R.layout.nodata_mywalletmingxi, null);
        lv_Expense_Recharge.setEmptyView(viewEmpty);
        arrList = new ArrayList<>();
        adapter = new ExpenseAndRechargeAdapter(arrList, getActivity(), "recharge");
        lv_Expense_Recharge.setAdapter(adapter);
        lv_Expense_Recharge.setMode(PullToRefreshBase.Mode.BOTH);
        lv_Expense_Recharge.setOnRefreshListener(this);
    }

    @Override
    public void onSuccessHttp(String responseInfo, int resultCode) {
        super.onSuccessHttp(responseInfo, resultCode);
        switch (resultCode) {
            case UrlConfig.RECHARGE_PAYIN_CODE:
                lv_Expense_Recharge.onRefreshComplete();
                LogUtils.d("获取充值明细成功：" + responseInfo);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String result = obj.optString("result");
                    if (code == 0) {
                        RchargeRecordListBean rchargerecordlistbean = AnsynHttpRequest.parser.fromJson(responseInfo, RchargeRecordListBean.class);
                        int totalCount = rchargerecordlistbean.getResult().getTotalCount();
                        totalPages = totalCount;
                        if (totalCount >= 0) {
                            if (isRefresh) {
                                arrList.clear();
                            }
                            List<RchargeRecordListBean.ResultBean.ItemsBean> mList = rchargerecordlistbean.getResult().getItems();
                            arrList.addAll(mList);
                            adapter.setDate(arrList);
                            adapter.notifyDataSetChanged();
                        }
                    } else if (code == 1001){ //版本更新 弹框
                        JSONObject obj1 = obj.optJSONObject("result");
                        int newversionNo = Integer.parseInt(obj1.optString("versionNo"));
                        if (newversionNo > JieKouDiaoYongUtils.getVersionCode(getActivity())) {
                            UpdateManager mUpdateManager = new UpdateManager(getActivity());
                            mUpdateManager.showNoticeDialog(obj1.optString("versionPath"), newversionNo, obj1.optString("versionDescription"));
                        }
                    } else if (code == 1002){ //退出登录 弹框
                        JieKouDiaoYongUtils.loginTanKuan(getActivity());
                    } else {
                        ToastUtil.makeShortText(getActivity(), result);
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
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogUtils.d("onHiddenChanged");
        if (hidden){
            pageNo = 1;
            arrList.clear();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.d("onResume");
    }

    @Override
    public void onFailureHttp(HttpException error, String msg) {
        super.onFailureHttp(error, msg);
        LogUtils.d("访问失败");
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        isRefresh = true;
        pageNo = 1;
        getRechargeList();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        isRefresh = false;
        if (pageNo < totalPages) {
            pageNo++;
            getRechargeList();
        } else {
            lv_Expense_Recharge.postDelayed(new Runnable() {
                @Override
                public void run() {
                    lv_Expense_Recharge.onRefreshComplete();
                    ToastUtil.makeShortText(getActivity(), "全部数据已加载完毕！");
                }
            }, 800);
        }
    }

    @Override
    public void onFailureHttp(HttpException error, String msg, int resultCode) {
        super.onFailureHttp(error, msg, resultCode);
        LogUtils.d("访问失败");
    }
}
