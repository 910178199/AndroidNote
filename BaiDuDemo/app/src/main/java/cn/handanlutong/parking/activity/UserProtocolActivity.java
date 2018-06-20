package cn.handanlutong.parking.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.bean.CouponReceive;
import cn.handanlutong.parking.bean.ParkingActivityVo;
import cn.handanlutong.parking.customview.YWLoadingDialog;
import cn.handanlutong.parking.http.AnsynHttpRequest;
import cn.handanlutong.parking.http.HttpMethod;
import cn.handanlutong.parking.http.UrlConfig;
import cn.handanlutong.parking.utils.LogUtils;
import cn.handanlutong.parking.utils.NetWorkUtil;
import cn.handanlutong.parking.utils.StringUtil;
import cn.handanlutong.parking.utils.ToastUtil;

/**
 * Created by zhangyonggang on 2017/8/8.
 * html界面预览
 */

public class UserProtocolActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back_userprocotol;
    private WebView webview;
    private TextView textView;
    private String H5_url, title, hdgqsj, yhjUrl, fxUrl, mobileData;
    BridgeWebView web_zhuce_bridge;
    List<CouponReceive> list;
    Handler handler=new Handler();
    public YWLoadingDialog mDialog;
    @Override
    public void initView() {
        setContentView(R.layout.activity_userprotocol);
        iv_back_userprocotol = (ImageView) findViewById(R.id.iv_back_userprocotol);
        webview = (WebView) findViewById(R.id.webview);
        web_zhuce_bridge = (BridgeWebView) findViewById(R.id.web_zhuce_bridge);
        textView = (TextView) findViewById(R.id.textView);
        Intent intent = getIntent();
        String Protocol = intent.getStringExtra("Protocol");
        H5_url = intent.getStringExtra("H5_url");
        title = intent.getStringExtra("tv_title");
        hdgqsj = intent.getStringExtra("hdgqsj");
        yhjUrl = intent.getStringExtra("yhjurl");
        fxUrl = intent.getStringExtra("fxurl");
        textView.setText(title);
        //启用支持javascript
        WebSettings webSettings = web_zhuce_bridge.getSettings();
        webSettings.setJavaScriptEnabled(true); //设置可调用js方法
        //使用缓存
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //不使用缓存
//        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setAllowContentAccess(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        web_zhuce_bridge.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if( url.startsWith("http:") || url.startsWith("https:") ) {
                    return false;
                }
                // Otherwise allow the OS to handle things like tel, mailto, etc.
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity( intent );
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                try {
                    if (hdgqsj.equals("true")){
                        ToastUtil.makeShortText(UserProtocolActivity.this, "活动已结束");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        webview.loadUrl(H5_url);
        web_zhuce_bridge.loadUrl(H5_url);//加载外网html
//        web_zhuce_bridge.loadUrl("file:///android_asset/yhq_get.html");//加载本地html

        web_zhuce_bridge.setWebChromeClient(new WebChromeClient());
        web_zhuce_bridge.addJavascriptInterface(new Object(){
            @JavascriptInterface
            public void wangwei() {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:400-810-6188"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        },"wangwei");
        web_zhuce_bridge.addJavascriptInterface(new Object(){
            @JavascriptInterface
            public void wangwei2() {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:400-055-5886"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        },"wangwei2");
        web_zhuce_bridge.registerHandler("submit", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                Log.d("shiyong", "2222指定Handler接收来自web的数据：" + data);
                web_zhuce_bridge.loadUrl(H5_url);
//                web_zhuce_bridge.loadUrl("file:///android_asset/yhq_get.html");
                function.onCallBack("指定Handler收到Web发来的数据，回传数据给你");
                if (hdgqsj.equals("true")){
                    ToastUtil.makeShortText(UserProtocolActivity.this, "活动已结束");
                } else {
                    if (data.equals("")){
                        ToastUtil.makeShortText(UserProtocolActivity.this,"手机号不能为空");
                        return;
                    }
                    if (StringUtil.isMobileNO(data)){ //校验正确的手机号
                        postMoblieData(data);
                    }else{
                        ToastUtil.makeShortText(UserProtocolActivity.this, "请输入正确的手机号");
                    }
                }
            }
        });
        web_zhuce_bridge.addJavascriptInterface(new Object(){
            @JavascriptInterface
            public void hdnrtp1() {
                if (hdgqsj.equals("true")){
                    ToastUtil.makeShortText(UserProtocolActivity.this, "活动已结束");
                } else {
                    Intent intent1=new Intent(UserProtocolActivity.this, RechargeActivity.class);
                    intent1.putExtra("yue", "");
                    startActivity(intent1);
                }
            }
            @JavascriptInterface
            public void hdnrtp2() {
                if (hdgqsj.equals("true")){
                    ToastUtil.makeShortText(UserProtocolActivity.this, "活动已结束");
                } else {
                    Intent intent1=new Intent(UserProtocolActivity.this, UserProtocolActivity.class);
                    intent1.putExtra("H5_url", fxUrl);
                    intent1.putExtra("tv_title","活动规则");
                    intent1.putExtra("hdgqsj",hdgqsj);
                    startActivity(intent1);
                }
            }
            @JavascriptInterface
            public void hdnrtp4() {
                if (hdgqsj.equals("true")){
                    ToastUtil.makeShortText(UserProtocolActivity.this, "活动已结束");
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            showGZdialog();
                        }
                    });
                }
            }
            @JavascriptInterface
            public void monthCard() {
                if (hdgqsj.equals("true")){
                    ToastUtil.makeShortText(UserProtocolActivity.this, "活动已结束");
                } else {
                    Intent intent1=new Intent(UserProtocolActivity.this, MonthCardActivity.class);
                    startActivity(intent1);
                }
            }
        },"a");
//        try {
//            if (hdgqsj.equals("true")){
//                ToastUtil.makeShortText(UserProtocolActivity.this, "活动已结束");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 校验用户手机号
     * @param data
     */
    private void postMoblieData(String data) {
        if (NetWorkUtil.isNetworkConnected(UserProtocolActivity.this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                jsobj1.put("sjh", data);
                mobileData = data;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.UserMobileDetails(httpUtils, jsobj1, UserProtocolActivity.this, UrlConfig.USER_Mobile_jiance_CODE);
        } else {
            ToastUtil.makeShortText(UserProtocolActivity.this, "请检查网络！");
        }
    }

    /**
     * 用户领取优惠券
     * @param data
     */
    private void postData(String data) {
        mDialog = new YWLoadingDialog(this);
        mDialog.show();
        if (NetWorkUtil.isNetworkConnected(UserProtocolActivity.this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                jsobj1.put("sjh", data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.UserLQYHQ(httpUtils, jsobj1, UserProtocolActivity.this, UrlConfig.USER_LQ_YHQ_CODE);
        } else {
            ToastUtil.makeShortText(UserProtocolActivity.this, "请检查网络！");
        }
    }

    public class JavaObject{
        @JavascriptInterface
        public void wangwei() {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:400-810-6188"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
    public class JavaScriptObject {
        @JavascriptInterface
        public void hdnrtp1() {
            if (hdgqsj.equals("true")){
                ToastUtil.makeShortText(UserProtocolActivity.this, "活动已结束");
            }else{
                Intent intent1=new Intent(UserProtocolActivity.this, RechargeActivity.class);
                startActivity(intent1);
            }
        }

        @JavascriptInterface
        public void hdnrtp2() {
            if (hdgqsj.equals("true")){
                ToastUtil.makeShortText(UserProtocolActivity.this, "活动已结束");
            }else{
                Intent intent1=new Intent(UserProtocolActivity.this, UserProtocolActivity.class);
                intent1.putExtra("H5_url", fxUrl);
                intent1.putExtra("tv_title","活动规则");
                intent1.putExtra("hdgqsj", "false");
                startActivity(intent1);
            }
        }
        @JavascriptInterface
        public void hdnrtp4() {
            if (hdgqsj.equals("true")){
                ToastUtil.makeShortText(UserProtocolActivity.this, "活动已结束");
            } else {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        showGZdialog();
                    }
                });
            }
        }
    }

    @Override
    public void setLisener() {
        iv_back_userprocotol.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back_userprocotol:
                this.finish();
                break;
            default:
                break;
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onSuccessHttp(String responseInfo, int resultCode) {
        super.onSuccessHttp(responseInfo, resultCode);
        switch (resultCode){
            case UrlConfig.USER_Mobile_jiance_CODE:
                LogUtils.d("检测用户手机号："+responseInfo);
                try {
                    JSONObject obj= new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String result = obj.getString("result");
                    if(code == 0){
                        if (result.equals("false")){
                            ToastUtil.makeShortText(UserProtocolActivity.this,"您已经领取了，不要太贪心哟！");
                            return;
                        } else {
                            showChaiDialog(mobileData);//弹框
                        }
                    }else {
                        ToastUtil.makeShortText(UserProtocolActivity.this, result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case UrlConfig.USER_LQ_YHQ_CODE:
                mDialog.dismiss();
                LogUtils.d("用户领取优惠券："+responseInfo);
                try {
                    JSONObject jsonObject= new JSONObject(responseInfo);
                    int code = jsonObject.optInt("code");
                    String result = jsonObject.getString("result");
                    if (code == 0) {
                        Gson gson = new Gson();
                        list = gson.fromJson(result, new TypeToken<List<CouponReceive>>(){}.getType());
                        Intent intent=new Intent(UserProtocolActivity.this, LQcomplateActivity.class);
                        intent.putExtra("coupon", (Serializable) list);
                        startActivity(intent);
                    } else {
                        ToastUtil.makeShortText(UserProtocolActivity.this, result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }
    private void showGZdialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = getLayoutInflater().inflate(R.layout.dialog_guize, null);
        ImageView iv = (ImageView) view.findViewById(R.id.iv_dialog_guize);
        new BitmapUtils(UserProtocolActivity.this).display(iv, yhjUrl);
        dialog.show();
        dialog.setContentView(view);
    }
    private void showChaiDialog(final String data) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = getLayoutInflater().inflate(R.layout.dialog_chai, null);
        LinearLayout rel = (LinearLayout) view.findViewById(R.id.ll_chai);
        rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ImageView iv = (ImageView) view.findViewById(R.id.iv_dialog_chai);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                postData(data);
            }
        });
        dialog.show();
        dialog.setContentView(view);
    }

    @Override
    public void onFailureHttp(HttpException error, String msg, int resultCode) {
        super.onFailureHttp(error, msg, resultCode);
        LogUtils.d("访问失败");
        mDialog.dismiss();
    }
}
