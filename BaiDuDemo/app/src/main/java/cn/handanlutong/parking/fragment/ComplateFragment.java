package cn.handanlutong.parking.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.activity.MyCarActivity;
import cn.handanlutong.parking.activity.MyRecordDetailActivity;
import cn.handanlutong.parking.activity.MyRecordDetailCompleteActivity;
import cn.handanlutong.parking.adapter.MyOrderAdapter;
import cn.handanlutong.parking.bean.ParkingRecordHistoryVo;
import cn.handanlutong.parking.customview.NoRollSwipeMenuListView;
import cn.handanlutong.parking.customview.YWLoadingDialog;
import cn.handanlutong.parking.customview.swipemenulistview.SwipeMenu;
import cn.handanlutong.parking.customview.swipemenulistview.SwipeMenuCreator;
import cn.handanlutong.parking.customview.swipemenulistview.SwipeMenuItem;
import cn.handanlutong.parking.customview.swipemenulistview.SwipeMenuListView;
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
 * 已完成
 * Created by zhangyonggang on 2017/4/12.
 */

public class ComplateFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener2<ScrollView>, AdapterView.OnItemClickListener {
    private PullToRefreshScrollView lv_Expense_Recharge;
    private boolean isPrepared;
    private SharedPreferenceUtil spUtil;
    private List<ParkingRecordHistoryVo> mList = null;
    private List<ParkingRecordHistoryVo> arrList = null;
    private MyOrderAdapter mAdapter;
    private boolean isRefresh = false;// false加载更多，true刷新
    private int pageNo = 1;
    private int pageFlag = 0;
    private int totalPages;
    YWLoadingDialog mDialog;
    private Context ctx;
    private boolean itemOnclik = true;
    private NoRollSwipeMenuListView noRollSwipeMenuListView;
    private int position;
    private View viewEmpty;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.ctx = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tingche_jilu_liebiao, null);
        initView(view);
        return view;
    }

    private void initSwipeMenuCreator() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem delItem = new SwipeMenuItem(getActivity());
                delItem.setBackground(R.color.del_may_car);
                delItem.setWidth(dp2px(75));
//                delItem.setHeigh(dp2px(100));
                delItem.setTitle("删除");
                delItem.setTitleSize(14);
                delItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(delItem);
            }
        };
        noRollSwipeMenuListView.setMenuCreator(creator);
        noRollSwipeMenuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                ComplateFragment.this.position = position;
                showAlertDialog(R.layout.dialog_personal_info2, null, position);
            }
        });
    }

    private void showAlertDialog(int dialog_personal_info2, Object o, final int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);//设置外部不可点击
        dialog.setCancelable(false);
        View view = getActivity().getLayoutInflater().inflate(dialog_personal_info2, null);
        dialog.show();
        dialog.setContentView(view);
        TextView tv_count = (TextView) view.findViewById(R.id.tv_AddCar_tishiyu2);
        tv_count.setText("确定删除此条停车记录吗？");
        TextView tv_Cancle2 = (TextView) view.findViewById(R.id.tv_Cancle);
        TextView tv_Sure2 = (TextView) view.findViewById(R.id.tv_Sure);
        tv_Cancle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_Sure2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除 已完成停车记录
                removeCompleteCar(mList.get(position).getId());
                dialog.dismiss();
            }
        });
    }

    private void removeCompleteCar(Long id) {
        if (NetWorkUtil.isNetworkConnected(getActivity())) {
            JSONObject jsobj1 = new JSONObject();
            try {
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(getActivity()));
                jsobj1.put("authKey", spUtil.getString("authkey"));
                jsobj1.put("userId", spUtil.getInt("id"));

                JSONObject jsobj2 = new JSONObject();
                jsobj2.put("id", id);
                jsobj1.put("parameter", jsobj2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.postShanChuWanChengJiluData(httpUtils, jsobj1, this, UrlConfig.STOP_CAR_TOTAL_AMOUNT_ARREARS_DELECT);
        } else {
            ToastUtil.makeShortText(getActivity(), "请检查网络！");
        }
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
        itemOnclik = true;
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        if (pageNo == pageFlag) {
            return;
        }
        getExpenseList();
        initSwipeMenuCreator();
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
            HttpMethod.AppUsergetBillRecords(httpUtils, jsobj1, this, UrlConfig.CAR_BILL_RECORD_CODE);
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
            case UrlConfig.CAR_BILL_RECORD_CODE:
                mDialog.dismiss();
                lv_Expense_Recharge.onRefreshComplete();
                LogUtils.d("获取已完成订单成功：" + responseInfo);
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
                                    billrecordvo.setLwsjSFM(obj2.optString("lwsjSFM"));
                                    billrecordvo.setLwsjYR(obj2.optString("lwsjYR"));
                                    billrecordvo.setCcmc(obj2.optString("ccmc"));
                                    billrecordvo.setTcsc(obj2.optString("tcsc"));
                                    billrecordvo.setZje(obj2.optString("zje"));
                                    billrecordvo.setId(obj2.optLong("id"));
                                    billrecordvo.setSjly(obj2.optString("sjly"));
                                    billrecordvo.setIsPay(obj2.optString("isPay"));
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

            case UrlConfig.STOP_CAR_TOTAL_AMOUNT_ARREARS_DELECT:
                LogUtils.e("删除完成停车记录:" + responseInfo);
                JSONObject obj = null;
                try {
                    obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    if (0 == code) {
                        mList.remove(position);
                        getExpenseList();
                        ToastUtil.makeShortText(getActivity(), "删除成功");
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
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        isRefresh = true;
        pageNo = 1;
        getExpenseList();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        isRefresh = false;
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

    @Override
    public void onFailureHttp(HttpException error, String msg, int resultCode) {
        super.onFailureHttp(error, msg, resultCode);
        lv_Expense_Recharge.onRefreshComplete();
        mDialog.dismiss();
        LogUtils.d("访问失败");
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (itemOnclik) {
            ParkingRecordHistoryVo billrecordvo = arrList.get(position);
            Intent intent = new Intent(getActivity(), MyRecordDetailCompleteActivity.class);
            intent.putExtra("id", billrecordvo.getId());
            intent.putExtra("sjly", billrecordvo.getSjly());
            startActivity(intent);
            itemOnclik = false;
        }
    }

    // 设置侧滑出来部分的宽度，否没有划出的效果
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}
