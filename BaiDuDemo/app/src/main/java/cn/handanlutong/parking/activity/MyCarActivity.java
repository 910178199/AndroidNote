package cn.handanlutong.parking.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.exception.HttpException;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.adapter.MyCarAdapter;
import cn.handanlutong.parking.bean.MyCarBean;
import cn.handanlutong.parking.bean.MyCarBean.ResultBean;
import cn.handanlutong.parking.customview.NoRollSwipeMenuListView;
import cn.handanlutong.parking.customview.YWLoadingDialog;
import cn.handanlutong.parking.customview.swipemenulistview.SwipeMenu;
import cn.handanlutong.parking.customview.swipemenulistview.SwipeMenuCreator;
import cn.handanlutong.parking.customview.swipemenulistview.SwipeMenuItem;
import cn.handanlutong.parking.customview.swipemenulistview.SwipeMenuListView;
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
 * 我的车辆
 * Created by zhangyonggang on 2017/4/12.
 */

public class MyCarActivity extends BaseActivity implements View.OnClickListener, PullToRefreshBase.OnRefreshListener2<ScrollView> {
    private PullToRefreshScrollView mScrollView;
    private ImageView iv_back_AddCar;
    private TextView tv_AddCar, tv_car_num_max;
    private NoRollSwipeMenuListView mListView;
    private List<ResultBean> mList;
    private MyCarAdapter adapter;
    private static int ADD_CAR_CODE = 0x1;
    private SharedPreferenceUtil spUtil;
    private int position;
    private LinearLayout ll_MyCarNodata;
    public YWLoadingDialog mDialog;
    @Override
    public void initView() {
        setContentView(R.layout.activity_mycar);
        iv_back_AddCar = (ImageView) findViewById(R.id.iv_back_AddCar);
        tv_AddCar = (TextView) findViewById(R.id.tv_AddCar);
        tv_car_num_max = (TextView) findViewById(R.id.tv_car_num_max);
        ll_MyCarNodata = (LinearLayout) findViewById(R.id.ll_MyCarNodata);
        mScrollView = (PullToRefreshScrollView) findViewById(R.id.mScrollView);
        mList = new ArrayList<ResultBean>();

        mListView = (NoRollSwipeMenuListView) findViewById(R.id.ls_MyCar);

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem delItem = new SwipeMenuItem(getApplicationContext());
                delItem.setBackground(R.color.del_may_car);
                delItem.setWidth(dp2px(75));
//                delItem.setHeigh(dp2px(100));
                delItem.setTitle("删除");
                delItem.setTitleSize(14);
                delItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(delItem);
            }
        };
        mListView.setMenuCreator(creator);
        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                MyCarActivity.this.position = position;
                showAlertDialog(R.layout.dialog_personal_info2, null, position);
            }
        });
    }

    public void showAlertDialog(int layout, String result, int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);//设置外部不可点击
        dialog.setCancelable(false);
        View view;
        switch (layout) {
            case R.layout.dialog_personal_info2:
                view = getLayoutInflater().inflate(layout, null);
                dialog.show();
                dialog.setContentView(view);
                TextView tv_AddCar_tishiyu2 = (TextView) view.findViewById(R.id.tv_AddCar_tishiyu2);
                TextView tv_Cancle2 = (TextView) view.findViewById(R.id.tv_Cancle);
                TextView tv_Sure2 = (TextView) view.findViewById(R.id.tv_Sure);
                if (mList.get(index).getBdzt().equals("0")){ //未认证
                    tv_AddCar_tishiyu2.setText("此操作会影响对您的停车服务");
                    tv_Sure2.setText("确定");
                    tv_Sure2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) { //调用删除接口
                            DeleteCar(mList.get(position).getId());
                            dialog.dismiss();
                        }
                    });
                }else if (mList.get(index).getBdzt().equals("1")){ //审核中
                    tv_AddCar_tishiyu2.setText("车辆认证中…不能解绑删除");
                    tv_Sure2.setText("联系客服");
                    tv_Sure2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:400-810-6188"));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    });
                }else if (mList.get(index).getBdzt().equals("2")){ //已认证
                    tv_AddCar_tishiyu2.setText("认证车辆，不能解绑删除");
                    tv_Sure2.setText("联系客服");
                    tv_Sure2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:400-810-6188"));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    });
                }
                tv_Cancle2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                break;
            case R.layout.dialog_personal_info3:
                view = getLayoutInflater().inflate(layout, null);
                dialog.show();
                dialog.setContentView(view);
                TextView tv_AddCar_tishiyu3 = (TextView) view.findViewById(R.id.tv_AddCar_tishiyu2);
                TextView tv_Cancle3 = (TextView) view.findViewById(R.id.tv_Cancle);
                TextView tv_Sure3 = (TextView) view.findViewById(R.id.tv_Sure);
                tv_Cancle3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                tv_AddCar_tishiyu3.setText("" + result);
                tv_Sure3.setText("去支付");
                tv_Sure3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Intent intent = new Intent(MyCarActivity.this, TingcheJiLuActivity.class);
                        startActivity(intent);
                    }
                });
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCarList();
    }

    /**
     * 删除车辆
     *
     */
    private void DeleteCar(Long id) {
        mDialog = new YWLoadingDialog(this);
        mDialog.show();
        if (NetWorkUtil.isNetworkConnected(this)) {
            spUtil = SharedPreferenceUtil.init(this, SharedPreferenceUtil.LOGIN_INFO, MODE_PRIVATE);
            JSONObject jsobj1 = new JSONObject();
            try {
                JSONObject jsobj2 = new JSONObject();
                jsobj2.put("id", id);

                jsobj1.put("parameter", jsobj2);
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.AppUsergetDeleteCars(httpUtils, jsobj1, this, UrlConfig.CAR_DELETE_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    /**
     * 我的车辆列表
     */
    private void getCarList() {
        mDialog = new YWLoadingDialog(this);
        mDialog.show();
        if (NetWorkUtil.isNetworkConnected(this)) {
            spUtil = SharedPreferenceUtil.init(this, SharedPreferenceUtil.LOGIN_INFO, MODE_PRIVATE);
            JSONObject jsobj1 = new JSONObject();
            try {
                JSONObject jsobj2 = new JSONObject();

                jsobj1.put("parameter", jsobj2);
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.AppUsergetCarLists(httpUtils, jsobj1, this, UrlConfig.CAR_GETLIST_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }


    @Override
    public void setLisener() {
        iv_back_AddCar.setOnClickListener(this);
        tv_AddCar.setOnClickListener(this);
        mScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mScrollView.setOnRefreshListener(this);
    }

    @Override
    public void onSuccessHttp(String responseInfo, int resultCode) {
        super.onSuccessHttp(responseInfo, resultCode);
        switch (resultCode) {
            case UrlConfig.CAR_GETLIST_CODE:
                mDialog.dismiss();
                mScrollView.onRefreshComplete();
                LogUtils.d("我的车辆列表：" + responseInfo);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String result = obj.optString("result");
                    if (code == 0) {
                        JSONArray array = obj.optJSONArray("result");
                        if (array.length() > 0) {
                            MyCarBean mycarbean = AnsynHttpRequest.parser.fromJson(responseInfo, MyCarBean.class);
                            mList = mycarbean.getResult();
                            ll_MyCarNodata.setVisibility(View.GONE);
                            mListView.setVisibility(View.VISIBLE);
//                            tv_car_num_max.setVisibility(View.VISIBLE);
                            mScrollView.setVisibility(View.VISIBLE);
                            adapter = new MyCarAdapter(this, mList);
                            mListView.setAdapter(adapter);
                            setListViewHeight(mListView);
                        } else {
                            ll_MyCarNodata.setVisibility(View.VISIBLE);
                            mListView.setVisibility(View.GONE);
//                            tv_car_num_max.setVisibility(View.GONE);
                            mScrollView.setVisibility(View.GONE);
                        }
                    } else if (code == 3001) {
                        ll_MyCarNodata.setVisibility(View.VISIBLE);
//                        tv_car_num_max.setVisibility(View.GONE);
                        mListView.setVisibility(View.GONE);
                        mScrollView.setVisibility(View.GONE);
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
            case UrlConfig.CAR_DELETE_CODE:
                mDialog.dismiss();
                LogUtils.d("删除车辆：" + responseInfo);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code1 = obj.optInt("code");
                    String message = obj.optString("message");
                    String result = obj.optString("result");
                    if (code1 == 0) {
                        mList.remove(position);
                        getCarList();
                        adapter.notifyDataSetChanged();
                        ToastUtil.makeShortText(this, "删除成功");
                        if(mList.size()==0){
                            ll_MyCarNodata.setVisibility(View.VISIBLE);
                            mListView.setVisibility(View.GONE);
                            mScrollView.setVisibility(View.GONE);
//                            tv_car_num_max.setVisibility(View.GONE);
                        }else {
                            ll_MyCarNodata.setVisibility(View.GONE);
                            mListView.setVisibility(View.VISIBLE);
                            mScrollView.setVisibility(View.VISIBLE);
//                            tv_car_num_max.setVisibility(View.VISIBLE);
                        }
                    } else if (code1 == 1001){ //版本更新 弹框
                        JSONObject obj1 = obj.optJSONObject("result");
                        int newversionNo = Integer.parseInt(obj1.optString("versionNo"));
                        if (newversionNo > JieKouDiaoYongUtils.getVersionCode(this)) {
                            UpdateManager mUpdateManager = new UpdateManager(this);
                            mUpdateManager.showNoticeDialog(obj1.optString("versionPath"), newversionNo, obj1.optString("versionDescription"));
                        }
                    } else if (code1 == 1002){ //退出登录 弹框
                        JieKouDiaoYongUtils.loginTanKuan(this);
                    } else if (code1 == 3008){ //去支付 弹框
                        showAlertDialog(R.layout.dialog_personal_info3, result, position);
                    } else if (code1 == 3009) { //去支付 弹框
                        showAlertDialog(R.layout.dialog_personal_info3, result, position);
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

    /**
     * 设置Listview的高度
     */
    private void setListViewHeight(SwipeMenuListView listView) {
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
    public void onFailureHttp(HttpException error, String msg, int resultCode) {
        super.onFailureHttp(error, msg, resultCode);
        LogUtils.d("访问失败");
        mDialog.dismiss();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        getCarList();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back_AddCar:
                this.finish();
                break;
            case R.id.tv_AddCar:
                if (mList.size() > 1) {
                    ToastUtil.makeShortText(this, "最多添加2个车牌号码");
                    return;
                }
                Intent intentAddCar = new Intent(this, AddCarActivity.class);
                startActivity(intentAddCar);
                break;
            default:
                break;
        }
    }

    // 设置侧滑出来部分的宽度，否没有划出的效果
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

}
