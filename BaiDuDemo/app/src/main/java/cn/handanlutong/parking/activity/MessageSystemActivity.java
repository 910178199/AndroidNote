package cn.handanlutong.parking.activity;

import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.bean.BillVo;
import cn.handanlutong.parking.bean.FirstEvent;
import cn.handanlutong.parking.bean.MessageUserInfo;
import cn.handanlutong.parking.bean.ParkingActivityVo;
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
 * Created by ww on 2018/1/8.
 */

public class MessageSystemActivity extends BaseActivity implements View.OnClickListener{

    private RelativeLayout back;
    private SharedPreferenceUtil spUtil;
    private LinearLayout ll_message_tongzhi, ll_message_huodong;
    private TextView tv_msg_huodong_sj, tv_msg_huodong_bt, tv_msg_xiaoxi_sj, tv_msg_xiaoxi_bt;
    private ImageView iv_msg_xhd1, iv_msg_xhd2;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_message_system);
        spUtil = SharedPreferenceUtil.init(this, SharedPreferenceUtil.LOGIN_INFO, MODE_PRIVATE);
        back = (RelativeLayout) findViewById(R.id.back);
        ll_message_tongzhi = (LinearLayout) findViewById(R.id.ll_message_tongzhi);
        ll_message_huodong = (LinearLayout) findViewById(R.id.ll_message_huodong);
        tv_msg_xiaoxi_sj = (TextView) findViewById(R.id.tv_msg_xiaoxi_sj);
        tv_msg_xiaoxi_bt = (TextView) findViewById(R.id.tv_msg_xiaoxi_bt);
        tv_msg_huodong_sj = (TextView) findViewById(R.id.tv_msg_huodong_sj);
        tv_msg_huodong_bt = (TextView) findViewById(R.id.tv_msg_huodong_bt);
        iv_msg_xhd1 = (ImageView) findViewById(R.id.iv_msg_xhd1);
        iv_msg_xhd2 = (ImageView) findViewById(R.id.iv_msg_xhd2);
        postMsgSysData();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    /**
     * 总 消息模块接口请求
     */
    private void postMsgSysData() {
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
                jsobj1.put("userId", spUtil.getInt("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.postMsgSysData(httpUtils, jsobj1, this, UrlConfig.GET_Msg_Sys_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    @Override
    public void setLisener() {
        super.setLisener();
        back.setOnClickListener(this);
        ll_message_huodong.setOnClickListener(this);
        ll_message_tongzhi.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.ll_message_huodong:
                iv_msg_xhd2.setVisibility(View.GONE);
                Intent intent1 = new Intent(this, ActionListActivity.class);
                startActivity(intent1);
                break;
            case R.id.ll_message_tongzhi:
                iv_msg_xhd1.setVisibility(View.GONE);
                Intent intent = new Intent(this, MyNewsActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onSuccessHttp(String responseInfo, int resultCode) {
        super.onSuccessHttp(responseInfo, resultCode);
        switch (resultCode) {
            case UrlConfig.GET_Msg_Sys_CODE:
                LogUtils.d("获取我的消息所有模块：" + responseInfo);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String result_info = obj.optString("result_info");
                    String result_activity = obj.optString("result_activity");
                    String result = obj.optString("result");
                    int result_count = obj.optInt("result_count");
                    int activity_count = obj.optInt("activity_count");
                    if (code == 0) {
                        if (result_count > 0){
                            iv_msg_xhd1.setVisibility(View.VISIBLE);
                        }else{
                            iv_msg_xhd1.setVisibility(View.GONE);
                        }
                        if (activity_count > 0){
                            iv_msg_xhd2.setVisibility(View.VISIBLE);
                        }else{
                            iv_msg_xhd2.setVisibility(View.GONE);
                        }
                        Gson gson = new Gson();
                        MessageUserInfo mes_info = gson.fromJson(result_info, MessageUserInfo.class);
                        if (! StringUtil.isEmpty(mes_info.getBt())){
                            tv_msg_xiaoxi_bt.setText("" + mes_info.getBt());
                            tv_msg_xiaoxi_sj.setText("" + mes_info.getCjsj());
                        }else{
                            tv_msg_xiaoxi_bt.setText("无时无刻的安全保障");
                            tv_msg_xiaoxi_sj.setText("");
                        }
                        ParkingActivityVo park_activity = gson.fromJson(result_activity, ParkingActivityVo.class);
                        if (! StringUtil.isEmpty(park_activity.getNr())){
                            tv_msg_huodong_bt.setText("" + park_activity.getHdbt());
                            tv_msg_huodong_sj.setText("" + park_activity.getHdsksj());
                        }else{
                            tv_msg_huodong_bt.setText("活动内容");
                            tv_msg_huodong_sj.setText("");
                        }
                    } else if (code == 1001) { //版本更新 弹框
                        JSONObject obj1 = obj.optJSONObject("result");
                        int newversionNo = Integer.parseInt(obj1.optString("versionNo"));
                        if (newversionNo > JieKouDiaoYongUtils.getVersionCode(this)) {
                            UpdateManager mUpdateManager = new UpdateManager(this);
                            mUpdateManager.showNoticeDialog(obj1.optString("versionPath"), newversionNo, obj1.optString("versionDescription"));
                        }
                    } else if (code == 1002) { //退出登录 弹框
                        JieKouDiaoYongUtils.loginTanKuan(this);
                    } else {
                        if (!StringUtil.isEmpty(result)) {
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

    @Subscribe(sticky = true)    //看下 `@Subscribe` 源码知道 `sticky` 默认是 `false`
    public void onEvent(FirstEvent event) {
        String msg = "onEventMainThread收到了消息：" + event.getMsg();
        Log.d("harvic", msg);
//        ToastUtil.showToast(this,""+msg);
        if (event.getMsg().equals("wangwei")){
            iv_msg_xhd1.setVisibility(View.VISIBLE);
            postMsgSysData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        postMsgSysData();
    }
}
