package cn.handanlutong.parking.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.exception.HttpException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.activity.MyRecordDetailActivity;
import cn.handanlutong.parking.activity.TingcheJiLuActivity;
import cn.handanlutong.parking.adapter.MyOrderAdapter;
import cn.handanlutong.parking.bean.FirstEvent;
import cn.handanlutong.parking.bean.ParkingRecordHistoryVo;
import cn.handanlutong.parking.customview.NoRollSwipeMenuListView;
import cn.handanlutong.parking.customview.YWLoadingDialog;
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
 * 未完成
 * Created by zhangyonggang on 2017/4/12.
 */

@SuppressLint("ValidFragment")
public class NoComplateFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener2<ScrollView>, AdapterView.OnItemClickListener {
    private PullToRefreshScrollView lv_Expense_Recharge;
    private boolean isPrepared;
    private SharedPreferenceUtil spUtil;
    private MyOrderAdapter mAdapter;
    private List<ParkingRecordHistoryVo> mList = null;
    private List<ParkingRecordHistoryVo> arrList = null;
    private boolean isRefresh = false;// false加载更多，true刷新
    private int pageNo = 1;
    private int pageFlag = 0;
    private int totalPages;
    private YWLoadingDialog mDialog;
    private View viewEmpty;
    private Context ctx;
    private boolean itemOnclick = true;
    private NoRollSwipeMenuListView noRollSwipeMenuListView;

    private TingcheJiLuActivity.onPullUpDownRefreshListener onPullUpDownRefreshListener;

    public NoComplateFragment(TingcheJiLuActivity.onPullUpDownRefreshListener onPullUpDownRefreshListener) {
        this.onPullUpDownRefreshListener = onPullUpDownRefreshListener;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.ctx = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tingche_jilu_liebiao, null);
        initView(view);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        isPrepared = true;
        lazyLoad();
    }


    @Override
    public void onStop() {
        super.onStop();
        isPrepared = false;
        pageFlag = 0;
        pageNo = 1;
        mList.clear();
        arrList.clear();
        itemOnclick = true;
    }


    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        if (pageFlag == pageNo) {
            return;
        }
        getExpenseList();
    }

    private void getExpenseList() {
        mDialog = new YWLoadingDialog(ctx);
        mDialog.show();
        if (NetWorkUtil.isNetworkConnected(UiUtils.getApplication())) {
            spUtil = SharedPreferenceUtil.init(UiUtils.getApplication(), SharedPreferenceUtil.LOGIN_INFO, Activity.MODE_PRIVATE);
            JSONObject jsobj1 = new JSONObject();
            try {
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(ctx));
                jsobj1.put("authKey", spUtil.getString("authkey"));
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("page", pageNo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.AppUsergetOrderRecords(httpUtils, jsobj1, this, UrlConfig.CAR_ORDER_RECORD_CODE);
        } else {
            ToastUtil.makeShortText(UiUtils.getApplication(), "请检查网络！");
        }
    }

    private void initView(View view) {
        lv_Expense_Recharge = (PullToRefreshScrollView) view.findViewById(R.id.lv_MyOrder);
        noRollSwipeMenuListView = (NoRollSwipeMenuListView) view.findViewById(R.id.ls_MyCar);
        mList = new ArrayList<>();
        arrList = new ArrayList<>();
        mAdapter = new MyOrderAdapter(UiUtils.getApplication(), arrList);
        noRollSwipeMenuListView.setAdapter(mAdapter);
        viewEmpty = view.findViewById(R.id.myorder_empty);
        lv_Expense_Recharge.setMode(PullToRefreshBase.Mode.BOTH);
        lv_Expense_Recharge.setOnRefreshListener(this);
        noRollSwipeMenuListView.setOnItemClickListener(this);
        noRollSwipeMenuListView.setSelector(R.color.transparent);
    }

    @Override
    public void onSuccessHttp(String responseInfo, int resultCode) {
        super.onSuccessHttp(responseInfo, resultCode);
        switch (resultCode) {
            case UrlConfig.CAR_ORDER_RECORD_CODE:
                mDialog.dismiss();
                lv_Expense_Recharge.onRefreshComplete();
                LogUtils.d("获取未完成订单成功：" + responseInfo);
                pageFlag = pageNo;
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
                                    billrecordvo.setRwsjSFM(obj2.optString("rwsjSFM"));
                                    billrecordvo.setRwsjYR(obj2.optString("rwsjYR"));
                                    billrecordvo.setCcmc(obj2.optString("ccmc"));
                                    billrecordvo.setTcsc(obj2.optString("tcsc"));
                                    billrecordvo.setIsPay(obj2.optString("isPay"));
                                    billrecordvo.setZje(obj2.optString("zje"));
                                    billrecordvo.setId(obj2.optLong("id"));
                                    billrecordvo.setSjly(obj2.optString("sjly"));
                                    billrecordvo.setZflx(obj2.optString("zflx"));
                                    mList.add(billrecordvo);
                                }
                                lv_Expense_Recharge.setVisibility(View.VISIBLE);
                                viewEmpty.setVisibility(View.GONE);
                                noRollSwipeMenuListView.setVisibility(View.VISIBLE);
                                arrList.addAll(mList);
                                mAdapter.setData(arrList);
                                setListViewHeight(noRollSwipeMenuListView);
                                mAdapter.notifyDataSetChanged();
                            } else {
                                if (isRefresh) {
                                    arrList.clear();
                                }
                                mAdapter.notifyDataSetChanged();
                                viewEmpty.setVisibility(View.VISIBLE);
                                lv_Expense_Recharge.setVisibility(View.GONE);
                                noRollSwipeMenuListView.setVisibility(View.GONE);
                            }
                        }
                    } else if (code == 1001) { //版本更新 弹框
                        JSONObject obj1 = obj.optJSONObject("result");
                        int newversionNo = Integer.parseInt(obj1.optString("versionNo"));
                        if (newversionNo > JieKouDiaoYongUtils.getVersionCode(getActivity())) {
                            UpdateManager mUpdateManager = new UpdateManager(getActivity());
                            mUpdateManager.showNoticeDialog(obj1.optString("versionPath"), newversionNo, obj1.optString("versionDescription"));
                        }
                    } else if (code == 1002) { //退出登录 弹框
                        JieKouDiaoYongUtils.loginTanKuan(getActivity());
                    } else {
                        ToastUtil.makeShortText(UiUtils.getApplication(), result);
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
        lv_Expense_Recharge.onRefreshComplete();
        mDialog.dismiss();
        LogUtils.d("访问失败");
    }

    @Subscribe(sticky = true)
    public void onEvent(FirstEvent event) {
        String msg = "onEventMainThread收到了消息：" + event.getMsg();
        LogUtils.d("msg is :" + msg);
        if (event.getMsg().equals("message3")) {
            isRefresh = true;
            getExpenseList();
        } else if (event.getMsg().equals("message1")) {
            isRefresh = true;
            getExpenseList();
        } else if (event.getMsg().equals("message0")) {
            isRefresh = true;
            getExpenseList();
        } else if (event.getMsg().equals("monthCardIn")) {
            isRefresh = true;
            getExpenseList();
        } else if (event.getMsg().equals("monthCarOut")) {
            isRefresh = true;
            getExpenseList();
        } else if (event.getMsg().equals("monthCar")) {
            isRefresh = true;
            getExpenseList();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (itemOnclick) {
            ParkingRecordHistoryVo billrecordvo = arrList.get(position);
            Intent intent = new Intent(getActivity(), MyRecordDetailActivity.class);
            intent.putExtra("id", billrecordvo.getId());
            intent.putExtra("sjly", billrecordvo.getSjly());
            startActivity(intent);
            itemOnclick = false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        isRefresh = true;
        pageNo = 1;
        getExpenseList();
        this.onPullUpDownRefreshListener.isDownRefreshListener(true);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        isRefresh = false;
        this.onPullUpDownRefreshListener.isUpRefreshListener(true);
        if (pageNo < totalPages) {
            pageNo++;
            getExpenseList();
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
}
