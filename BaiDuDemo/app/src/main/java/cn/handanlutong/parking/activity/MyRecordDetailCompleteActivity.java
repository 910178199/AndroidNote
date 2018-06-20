package cn.handanlutong.parking.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.handanlutong.parking.R;
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
import cn.handanlutong.parking.zoom.ImagPagerUtil;

/**
 * Created by zhangyonggang on 2017/11/29.
 * 已完成 停车记录详情
 */

public class MyRecordDetailCompleteActivity extends BaseActivity implements View.OnClickListener {
    private BitmapUtils bitmapUtils = null;
    private SharedPreferenceUtil spUtil;
    private long id;
    private String sjly, djlx;
    private ViewPagerAdapter adapter;
    private List<ImageView> imageList_orginal = new ArrayList<ImageView>();
    private ViewGroup ll_group;
    private ImageView[] dots;
    private ImagPagerUtil imagPagerUtil;
    private ArrayList<String> mList = new ArrayList<>();
    private List<Map<String, String>> mmList = new ArrayList<>();
    private ArrayList<String> imageUrl_List = new ArrayList<String>();
    private ImageView iv_back_parking_detail_complete;
    private TextView tv_Share, tv_ccdz, tv_rwsj, tv_lwsj, tv_zje, tv_shoufeibiaozhun, tv_rworlw, tv_CarNum, tv_cwbh, tv_tcsc;
    private ViewPager viewPager;
    private YWLoadingDialog mDialog;
    private ImageView view, iv_yueka;

    @Override
    public void initView() {
        setContentView(R.layout.activity_myrecord_detail_complete);
        spUtil = SharedPreferenceUtil.init(this, SharedPreferenceUtil.LOGIN_INFO, Activity.MODE_PRIVATE);
        bitmapUtils = new BitmapUtils(this);
        Intent intent = getIntent();
        id = intent.getLongExtra("id", 0);
        sjly = intent.getStringExtra("sjly");
        iv_back_parking_detail_complete = (ImageView) findViewById(R.id.iv_back_parking_detail_complete);
        tv_Share = (TextView) findViewById(R.id.tv_Share);
        tv_ccdz = (TextView) findViewById(R.id.tv_ccdz);
        tv_rwsj = (TextView) findViewById(R.id.tv_rwsj);
        tv_lwsj = (TextView) findViewById(R.id.tv_lwsj);
        tv_zje = (TextView) findViewById(R.id.tv_zje);
        tv_CarNum = (TextView) findViewById(R.id.tv_CarNum);
        tv_cwbh = (TextView) findViewById(R.id.tv_cwbh);
        tv_tcsc = (TextView) findViewById(R.id.tv_tcsc);
        tv_shoufeibiaozhun = (TextView) findViewById(R.id.tv_shoufeibiaozhun);
        tv_rworlw = (TextView) findViewById(R.id.tv_rworlw);
        iv_yueka = (ImageView) findViewById(R.id.iv_yueka);
        ll_group = (ViewGroup) findViewById(R.id.ll_viewGroup);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                setImageBackground(position % mList.size());
                Set set = mmList.get(position % mList.size()).keySet();
                Iterator iter = set.iterator();
                String key = null;
                while (iter.hasNext()) {
                    key = (String) iter.next();
                }
                if (key.equals("rwtp1Path") || key.equals("rwtp2Path")) {
                    tv_rworlw.setText("入场图片");
                } else if (key.equals("lwtp1Path") || key.equals("lwtp2Path")) {
                    tv_rworlw.setText("离场图片");
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
        getMyOrderDetail();
    }


    @Override
    public void setLisener() {
        iv_back_parking_detail_complete.setOnClickListener(this);
        tv_Share.setOnClickListener(this);
        tv_shoufeibiaozhun.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back_parking_detail_complete:
                this.finish();
                break;
            case R.id.tv_Share:
                postNeedHelpData();
                break;
            case R.id.tv_shoufeibiaozhun:
                getChargeStandard();
                break;
            default:
                break;
        }
    }

    /**
     * 需要帮助  调用接口
     */
    private void postNeedHelpData() {
        mDialog = new YWLoadingDialog(this);
        mDialog.show();
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            JSONObject jsobj2 = new JSONObject();
            try {
                jsobj2.put("tcjlId", id);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("parameter", jsobj2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.postNeedHelpData(httpUtils, jsobj1, this, UrlConfig.NEED_HELP_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    /**
     * 获取我的订单详情
     */
    public void getMyOrderDetail() {
        mDialog = new YWLoadingDialog(this);
        mDialog.show();
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            JSONObject jsobj2 = new JSONObject();
            try {
                jsobj2.put("id", id);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("parameter", jsobj2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.AppUsergetBillRecordDetails(httpUtils, jsobj1, this, UrlConfig.CAR_BILL_RECORD_DETAIL_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    /**
     * 收费标准
     */
    public void getChargeStandard() {
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                JSONObject jsobj2 = new JSONObject();
                jsobj2.put("id", djlx);

                jsobj1.put("parameter", jsobj2);
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.CCsf_GUIze(httpUtils, jsobj1, this, UrlConfig.CCsf_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }


    private void setImageBackground(int selectItems) {
        for (int j = 0; j < dots.length; j++) {
            if (j == selectItems) {
                dots[j].setBackgroundResource(R.drawable.dot_detail_focus);

            } else {
                dots[j].setBackgroundResource(R.drawable.dot_detail_normal);
            }
        }
    }


    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            if (imageList_orginal.size() == 1) {
                return 1;
            } else {
                return Integer.MAX_VALUE;
            }
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final int finalPosition = position;
            imageList_orginal.get(position%imageList_orginal.size()).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imagPagerUtil = new ImagPagerUtil(MyRecordDetailCompleteActivity.this, mList, mmList, finalPosition % mList.size() + 1);
                    imagPagerUtil.show();
                }
            });
            position %= imageList_orginal.size();

            if (position < 0) {
                position = imageList_orginal.size() + position;
            }
            view = imageList_orginal.get(position);
            ViewParent vp = view.getParent();
            if (vp != null) {
                ViewGroup parent = (ViewGroup) vp;
                parent.removeView(view);
            }
            container.addView(view, 0);
            return view;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (imagPagerUtil != null) {
            imagPagerUtil.stop();
        }
    }


    @Override
    public void onSuccessHttp(String responseInfo, int resultCode) {
        super.onSuccessHttp(responseInfo, resultCode);
        switch (resultCode) {
            case UrlConfig.CAR_BILL_RECORD_DETAIL_CODE:
                mDialog.dismiss();
                LogUtils.d("已完成获取订单详情成功："+responseInfo);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String result = obj.optString("result");
                    if (code == 0) {
                        JSONObject obj1 = obj.optJSONObject("result");
                        djlx = obj1.optString("djlx");
                        tv_ccdz.setText(obj1.optString("ccmc"));
                        tv_cwbh.setText(obj1.optString("cwbh"));
                        tv_CarNum.setText(obj1.optString("hphm"));
                        tv_rwsj.setText(obj1.optString("rwsj"));
                        tv_tcsc.setText(obj1.optString("tcsc"));
                        tv_lwsj.setText(obj1.optString("lwsj"));
                        double zje_money = (double) Integer.parseInt(obj1.optString("zje")) / 100;
                        String zje = String.format("%.2f", zje_money);
                        tv_zje.setText("￥" + zje);

                        if (obj1.has("lwtp1Path")) {
                            mList.add(obj1.optString("lwtp1Path"));

                            Map<String, String> map3 = new HashMap<>();
                            map3.put("lwtp1Path", obj1.optString("lwtp1Path"));
                            mmList.add(map3);
                        }
                        if (obj1.has("lwtp2Path")) {
                            mList.add(obj1.optString("lwtp2Path"));

                            Map<String, String> map4 = new HashMap<>();
                            map4.put("lwtp2Path", obj1.optString("lwtp2Path"));
                            mmList.add(map4);
                        }
                        if (obj1.has("rwtp1Path")) {
                            mList.add(obj1.optString("rwtp1Path"));
                            Map<String, String> map1 = new HashMap<>();
                            map1.put("rwtp1Path", obj1.optString("rwtp1Path"));
                            mmList.add(map1);
                        }
                        if (obj1.has("rwtp2Path")) {
                            mList.add(obj1.optString("rwtp2Path"));

                            Map<String, String> map2 = new HashMap<>();
                            map2.put("rwtp2Path", obj1.optString("rwtp2Path"));
                            mmList.add(map2);
                        }

                        if (mList.size() == 2) {
                            imageUrl_List.add(mList.get(0));
                            imageUrl_List.add(mList.get(1));
                            imageUrl_List.add(mList.get(0));
                            imageUrl_List.add(mList.get(1));
                        } else {
                            imageUrl_List = mList;
                        }

                        for (int i = 0; i < imageUrl_List.size(); i++) {
                            ImageView imageView = new ImageView(this);
                            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                            imageView.setImageResource(R.mipmap.iv_maining_addcar);
                            bitmapUtils.display(imageView, imageUrl_List.get(i));
                            imageList_orginal.add(imageView);
                        }
                        if (obj1.optString("zflx").equals("5")){ //支付类型 1充值 2消费 3退款 4赠送 5包期
                            iv_yueka.setVisibility(View.VISIBLE);
                        }else{
                            iv_yueka.setVisibility(View.GONE);
                        }
                        dots = new ImageView[mList.size()];
                        for (int i = 0; i < dots.length; i++) {
                            ImageView imageView = new ImageView(this);
                            imageView.setLayoutParams(new ViewGroup.LayoutParams(8, 8));
                            dots[i] = imageView;
                            if (i == 0) {
                                dots[i].setBackgroundResource(R.drawable.dot_detail_focus);
                                Set set = mmList.get(i).keySet();
                                Iterator iter = set.iterator();
                                String key = null;
                                while (iter.hasNext()) {
                                    key = (String) iter.next();
                                }
                                if (key.equals("rwtp1Path") || key.equals("rwtp2Path")) {
                                    tv_rworlw.setText("入场图片");
                                } else if (key.equals("lwtp1Path") || key.equals("lwtp2Path")) {
                                    tv_rworlw.setText("离场图片");
                                }
                            } else {
                                dots[i].setBackgroundResource(R.drawable.dot_detail_normal);
                            }

                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT));
                            layoutParams.leftMargin = 5;
                            layoutParams.rightMargin = 5;
                            ll_group.addView(imageView, layoutParams);
                        }

                        adapter = new ViewPagerAdapter();
                        viewPager.setAdapter(adapter);
//                        viewPager.setCurrentItem((mList.size()) * 100);
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
                        ToastUtil.makeShortText(this, result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case UrlConfig.CCsf_CODE:
                LogUtils.d("获取收费标准成功：" + responseInfo);
//                dialog.dismiss();
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String result = obj.optString("result");
                    if (code == 0) {
                        JSONObject obj1 = obj.optJSONObject("result");
                        int sflx = obj1.optInt("sflx");
                        final AlertDialog dialo = new AlertDialog.Builder(this).create();
                        dialo.show();
                        dialo.getWindow().setContentView(R.layout.guize_dialog);
                        RelativeLayout rl = (RelativeLayout) dialo.getWindow().findViewById(R.id.rl_guize);
                        TextView btn_know = (TextView) dialo.getWindow().findViewById(R.id.tv_bt);
                        TextView wsn_know = (TextView) dialo.getWindow().findViewById(R.id.tv_ws);
                        TextView zhu = (TextView) dialo.getWindow().findViewById(R.id.tv_zhu);
                        rl.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialo.dismiss();
                            }
                        });
                        if (sflx == 3) {
                            double money = (double) obj1.optInt("btdwjg") / 100;
                            wsn_know.setText("按次收费：" + String.format("%.2f", money) + "元/次");
                        } else if (sflx == 2) {
                            double money = (double) obj1.optInt("btdwjg") / 100;
                            wsn_know.setText("全天：" + String.format("%.2f", money) + "元/" + obj1.optString("btdwsc") + "分钟");
                            zhu.setText("注：" + obj1.optString("bz"));
                        } else if (sflx == 1) {
                            double money1 = (double) obj1.optInt("btdwjg") / 100;
                            double money2 = (double) obj1.optInt("wsdwjg") / 100;
                            btn_know.setText("日间（" + obj1.optString("btsjd") + "） " + String.format("%.2f", money1) + "元/" + obj1.optString("btdwsc") + "分钟");
                            wsn_know.setText("夜间（" + obj1.optString("wssjd") + "） " + String.format("%.2f", money2) + "元/" + obj1.optString("wsdwsc") + "分钟");
                            zhu.setText("注：" + obj1.optString("bz"));
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
                        ToastUtil.makeShortText(this, result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case UrlConfig.NEED_HELP_CODE:
                LogUtils.d("点击需要帮助成功：" + responseInfo);
                mDialog.dismiss();
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String result = obj.optString("result");
                    String message = obj.optString("message");
                    if (code == 0) {
                        Intent intent = new Intent(this, NeedHelpActivity.class);
                        intent.putExtra("jlid", id);
                        startActivity(intent);
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
                        if (!StringUtil.isEmpty(message)) {
                            ToastUtil.makeShortText(this, message);
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
    public void onFailureHttp(HttpException error, String msg, int resultCode) {
        super.onFailureHttp(error, msg, resultCode);
        LogUtils.d("访问失败");
        mDialog.dismiss();
    }
}
