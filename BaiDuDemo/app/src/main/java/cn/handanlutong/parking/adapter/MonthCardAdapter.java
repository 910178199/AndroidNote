package cn.handanlutong.parking.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.activity.BuyMonthCardActivity;
import cn.handanlutong.parking.bean.LicensePlateCardVo;
import cn.handanlutong.parking.customview.YWLoadingDialog;
import cn.handanlutong.parking.http.HttpMethod;
import cn.handanlutong.parking.http.ObserverCallBack;
import cn.handanlutong.parking.http.UrlConfig;
import cn.handanlutong.parking.utils.JieKouDiaoYongUtils;
import cn.handanlutong.parking.utils.LogUtils;
import cn.handanlutong.parking.utils.NetWorkUtil;
import cn.handanlutong.parking.utils.SharedPreferenceUtil;
import cn.handanlutong.parking.utils.StringUtil;
import cn.handanlutong.parking.utils.ToastUtil;
import cn.handanlutong.parking.utils.UpdateManager;

/**
 * Created by ww on 2017/8/16.
 * 月卡列表的adapter
 */

public class MonthCardAdapter extends BaseAdapter implements ObserverCallBack {
    protected HttpUtils httpUtils;// 网络访问声明
    Context mcontext;
    List<LicensePlateCardVo> mlist;
    private int selected=-1;
    private SharedPreferenceUtil spUtil;
    private Long carId;
    private int carFlag;
    public MonthCardAdapter(Context context, List<LicensePlateCardVo> list, int flag){
        this.mcontext = context;
        this.mlist = list;
        spUtil = SharedPreferenceUtil.init(mcontext, SharedPreferenceUtil.LOGIN_INFO, Activity.MODE_PRIVATE);
        httpUtils = new HttpUtils();
        carFlag = flag;
    }
    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(mcontext, R.layout.item_month_card, null);
            viewHolder.item_month_type = (TextView) convertView.findViewById(R.id.item_month_type);
            viewHolder.item_month_sysj = (TextView) convertView.findViewById(R.id.item_month_sysj);
            viewHolder.item_month_hphm = (TextView) convertView.findViewById(R.id.item_month_hphm);
            viewHolder.btn_month_xufei = (Button) convertView.findViewById(R.id.btn_month_xufei);
            viewHolder.ll_monthcard = (LinearLayout) convertView.findViewById(R.id.ll_monthcard);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (mlist.get(position).getSyts() == 0){
            viewHolder.ll_monthcard.setBackgroundResource(R.mipmap.bg_moren_monthcard_gq);
            viewHolder.item_month_sysj.setVisibility(View.GONE);
            viewHolder.item_month_type.setText("已过期");
            viewHolder.item_month_type.setTextColor(Color.parseColor("#CDE5DE"));
            viewHolder.item_month_hphm.setTextColor(Color.parseColor("#CDE5DE"));
        }else if (mlist.get(position).getBqzt().equals("2")){
            viewHolder.ll_monthcard.setBackgroundResource(R.mipmap.bg_moren_monthcard);
            viewHolder.item_month_sysj.setVisibility(View.VISIBLE);
            viewHolder.item_month_type.setText("使用中");
            viewHolder.item_month_type.setTextColor(Color.parseColor("#FFFFFF"));
            viewHolder.item_month_hphm.setTextColor(Color.parseColor("#FFFFFF"));
        }else if (mlist.get(position).getBqzt().equals("4")){
            viewHolder.ll_monthcard.setBackgroundResource(R.mipmap.bg_moren_monthcard_gq);
            viewHolder.item_month_sysj.setVisibility(View.VISIBLE);
            viewHolder.item_month_type.setText("已禁用");
            viewHolder.item_month_type.setTextColor(Color.parseColor("#CDE5DE"));
            viewHolder.item_month_hphm.setTextColor(Color.parseColor("#CDE5DE"));
        }
        viewHolder.item_month_hphm.setText("" + mlist.get(position).getHphm());
        viewHolder.item_month_sysj.setText("剩余" + mlist.get(position).getSyts() + "天");
        if (mlist.get(position).getSfxf() == 1){ //可以续费
            viewHolder.btn_month_xufei.setVisibility(View.VISIBLE);
        } else {
            viewHolder.btn_month_xufei.setVisibility(View.GONE);
        }
        viewHolder.btn_month_xufei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carFlag = 1;
                postMonthCardBuyData();
                carId = mlist.get(position).getId();
            }
        });
        if (position == selected){

        }
        return convertView;
    }

    class ViewHolder{
        TextView item_month_type, item_month_sysj, item_month_hphm;
        Button btn_month_xufei;
        LinearLayout ll_monthcard;
    }

    public void setSelectedPosition(int position) {
        this.selected=position;
    }

    /**
     * 购买月卡 接口请求
     */
    private void postMonthCardBuyData() {
        if (NetWorkUtil.isNetworkConnected(mcontext)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(mcontext));
                jsobj1.put("authKey", spUtil.getString("authkey"));
                jsobj1.put("userId", spUtil.getInt("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.postMonthCardBuyData(httpUtils, jsobj1, this, UrlConfig.USER_Month_Card_Buy_CODE);
        } else {
            ToastUtil.makeShortText(mcontext, "请检查网络！");
        }
    }

    @Override
    public void onSuccessHttp(String responseInfo, int resultCode) {
        switch (resultCode){
            case UrlConfig.USER_Month_Card_Buy_CODE:
            LogUtils.d("去购买月卡：" + responseInfo);
            try {
                JSONObject obj= new JSONObject(responseInfo);
                int code =obj.optInt("code");
                String result=obj.optString("result");
                String message=obj.optString("message");
                if (code == 0){
                    Intent intent = new Intent(mcontext, BuyMonthCardActivity.class);
                    intent.putExtra("carid", carId);
                    intent.putExtra("carFlag", carFlag);
                    mcontext.startActivity(intent);
                } else if (code == 1001){ //版本更新 弹框
                    JSONObject obj1 = obj.optJSONObject("result");
                    int newversionNo = Integer.parseInt(obj1.optString("versionNo"));
                    if (newversionNo > JieKouDiaoYongUtils.getVersionCode(mcontext)) {
                        UpdateManager mUpdateManager = new UpdateManager(mcontext);
                        mUpdateManager.showNoticeDialog(obj1.optString("versionPath"), newversionNo, obj1.optString("versionDescription"));
                    }
                } else if (code == 1002){ //退出登录 弹框
                    JieKouDiaoYongUtils.loginTanKuan(mcontext);
                } else{
                    if (! StringUtil.isEmpty(message)){
                        ToastUtil.makeShortText(mcontext, message);
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
        LogUtils.d("获取失败");
    }

    @Override
    public void onFailureHttp(HttpException error, String msg, int resultCode) {

    }

    @Override
    public void onSuccessHttp(String result, int i, Object extraData) {

    }

    @Override
    public void onStartHttp() {

    }

    @Override
    public void onLoadingHttp(long total, long current, boolean isUploading) {

    }
}
