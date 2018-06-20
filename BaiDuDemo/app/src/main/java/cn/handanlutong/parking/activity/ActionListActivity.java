package cn.handanlutong.parking.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.adapter.ActionLBAdapter;
import cn.handanlutong.parking.bean.ParkingActivityVo;
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

/**
 * 活动列表页面
 * Created by zhangyonggang on 2017/9/1.
 */

public class ActionListActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ImageView iv_back_ActionCenter;
    private SharedPreferenceUtil spUtil;
    private ActionLBAdapter adapter;
    private ListView lv_actionlb;
    LinearLayout ll_no_action;
    private List<ParkingActivityVo> list;
    public YWLoadingDialog mDialog;
    @Override
    public void initView() {
        setContentView(R.layout.activity_actionlb);
        spUtil = SharedPreferenceUtil.init(this, SharedPreferenceUtil.LOGIN_INFO, Activity.MODE_PRIVATE);
        ll_no_action = (LinearLayout) findViewById(R.id.ll_no_action);
        iv_back_ActionCenter= (ImageView) findViewById(R.id.iv_back_ActionCenter);
        lv_actionlb= (ListView) findViewById(R.id.lv_actionlb);
        getActingList();
    }

    /**
     * 获取活动列表
     */
    private void getActingList() {
        mDialog = new YWLoadingDialog(this);
        mDialog.show();
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.AppUserGetActionList(httpUtils, jsobj1, this, UrlConfig.USER_GETACTIONLIST_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }


    @Override
    public void setLisener() {
        iv_back_ActionCenter.setOnClickListener(this);
        lv_actionlb.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back_ActionCenter:
                this.finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onSuccessHttp(String responseInfo, int resultCode) {
        super.onSuccessHttp(responseInfo, resultCode);
        switch (resultCode){
            case UrlConfig.USER_GETACTIONLIST_CODE:
                mDialog.dismiss();
                LogUtils.d("活动列表：" + responseInfo);
                try {
                    JSONObject obj= new JSONObject(responseInfo);
                    int code =obj.optInt("code");
                    String result=obj.optString("result");
                    if(code == 0){
                        list= AnsynHttpRequest.parser.fromJson(result, new TypeToken<List<ParkingActivityVo>>(){}.getType());
                        if (list.size() == 0){
                            lv_actionlb.setVisibility(View.GONE);
                            ll_no_action.setVisibility(View.VISIBLE);
                        }else {
                            lv_actionlb.setVisibility(View.VISIBLE);
                            ll_no_action.setVisibility(View.GONE);
                            adapter = new ActionLBAdapter(this, list);
                            lv_actionlb.setAdapter(adapter);
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
                    } else{
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
        mDialog.dismiss();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent = new Intent(this, UserProtocolActivity.class);
        intent.putExtra("H5_url",list.get(position).getNr());
        intent.putExtra("hdid", list.get(position).getId());
        if (list.get(position).getActivityVo()!=null){
            intent.putExtra("yhjurl", list.get(position).getActivityVo().getHdbttp());
        }
        if (list.get(position).getActivityVo()!=null){
            intent.putExtra("fxurl", list.get(position).getActivityVo().getNr());
        }
        intent.putExtra("tv_title","活动详情");
        intent.putExtra("hdgqsj",list.get(position).getSfgq());
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getActingList();
    }
}
