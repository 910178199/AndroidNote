package cn.handanlutong.parking.apshare;

import android.Manifest;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.activity.BaseActivity;
import cn.handanlutong.parking.apshare.model.Defaultcontent;
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
 * Created by wangfei on 16/11/10.
 */
public class ShareMenuActivity extends BaseActivity {

    private UMShareListener mShareListener;
    private ShareAction mShareAction;
    private SharedPreferenceUtil spUtil;
    private String nr_url,hdms,hdbt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.umeng_blue));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this, mPermissionList, 123);
        }

        setContentView(R.layout.umeng_menu);

        findViewById(R.id.iv_back_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mShareListener = new CustomShareListener(this);
        /*增加自定义按钮的分享面板*/
        mShareAction = new ShareAction(ShareMenuActivity.this).setDisplayList(
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
                SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)
//                .addButton("umeng_sharebutton_copy", "umeng_sharebutton_copy", "umeng_socialize_copy", "umeng_socialize_copy")
//                .addButton("umeng_sharebutton_copyurl", "umeng_sharebutton_copyurl", "umeng_socialize_copyurl", "umeng_socialize_copyurl")
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                        LogUtils.d("snsPlatform is: " + snsPlatform.mShowWord + ";;share_media is:" + share_media);
                        if (snsPlatform.mShowWord.equals("umeng_sharebutton_copy")) {
                            Toast.makeText(ShareMenuActivity.this, "复制文本按钮", Toast.LENGTH_LONG).show();
                        } else if (snsPlatform.mShowWord.equals("umeng_sharebutton_copyurl")) {
                            Toast.makeText(ShareMenuActivity.this, "复制链接按钮", Toast.LENGTH_LONG).show();

                        } else if (share_media == SHARE_MEDIA.SMS) {
                            new ShareAction(ShareMenuActivity.this).withText("来自分享面板标题")
                                    .setPlatform(share_media)
                                    .setCallback(mShareListener)
                                    .share();
                        } else {
                            UMWeb web = new UMWeb(nr_url);
                            web.setTitle(hdbt);
                            web.setDescription(hdms);
                            web.setThumb(new UMImage(ShareMenuActivity.this, R.mipmap.logo));
                            new ShareAction(ShareMenuActivity.this).withMedia(web)
                                    .setPlatform(share_media)
                                    .setCallback(mShareListener)
                                    .share();
                        }
                    }
                });
        /*无自定按钮的分享面板*/
//        mShareAction = new ShareAction(ShareMenuActivity.this).setDisplayList(
//                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE,
//                SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)
//                .withText(Defaultcontent.text + "来自友盟自定义分享面板")
//                .setCallback(mShareListener);
//
        findViewById(R.id.umeng_menu_bottom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareAction.open();
            }
        });
//        findViewById(R.id.umeng_menu_center).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ShareBoardConfig config = new ShareBoardConfig();
//                config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_CENTER);
//                config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR); // 圆角背景
////                config.setTitleVisibility(false); // 隐藏title
////                config.setCancelButtonVisibility(false); // 隐藏取消按钮
//                mShareAction.open(config);
//
//            }
//        });

//        findViewById(R.id.umeng_menu_bottom2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ShareBoardConfig config = new ShareBoardConfig();
//                config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_NONE);
//                mShareAction.open(config);
//
//            }
//        });
//
//        findViewById(R.id.umeng_menu_center2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ShareBoardConfig config = new ShareBoardConfig();
//                config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_CENTER);
//                config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_NONE);
//                mShareAction.open(config);
//            }
//        });

        getInvitedUrl();
    }

    /**
     * 分享 接口调用
     */
    private void getInvitedUrl() {
        if (NetWorkUtil.isNetworkConnected(this)) {
            spUtil = SharedPreferenceUtil.init(this, SharedPreferenceUtil.LOGIN_INFO, MODE_PRIVATE);
            JSONObject jsobj1 = new JSONObject();
            try {
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
                jsobj1.put("appType", UrlConfig.android_type);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.AppUserGetShareFriendUrl(httpUtils, jsobj1, this, UrlConfig.GET_INVITED_FRIEND_URL_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    private static class CustomShareListener implements UMShareListener {

        private WeakReference<ShareMenuActivity> mActivity;

        private CustomShareListener(ShareMenuActivity activity) {
            mActivity = new WeakReference(activity);
        }

        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {

            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(mActivity.get(), "收藏成功", Toast.LENGTH_SHORT).show();
            } else {
                if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                        && platform != SHARE_MEDIA.EMAIL
                        && platform != SHARE_MEDIA.FLICKR
                        && platform != SHARE_MEDIA.FOURSQUARE
                        && platform != SHARE_MEDIA.TUMBLR
                        && platform != SHARE_MEDIA.POCKET
                        && platform != SHARE_MEDIA.PINTEREST

                        && platform != SHARE_MEDIA.INSTAGRAM
                        && platform != SHARE_MEDIA.GOOGLEPLUS
                        && platform != SHARE_MEDIA.YNOTE
                        && platform != SHARE_MEDIA.EVERNOTE) {
                    Toast.makeText(mActivity.get(), "分享成功", Toast.LENGTH_SHORT).show();
                }

            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                    && platform != SHARE_MEDIA.EMAIL
                    && platform != SHARE_MEDIA.FLICKR
                    && platform != SHARE_MEDIA.FOURSQUARE
                    && platform != SHARE_MEDIA.TUMBLR
                    && platform != SHARE_MEDIA.POCKET
                    && platform != SHARE_MEDIA.PINTEREST

                    && platform != SHARE_MEDIA.INSTAGRAM
                    && platform != SHARE_MEDIA.GOOGLEPLUS
                    && platform != SHARE_MEDIA.YNOTE
                    && platform != SHARE_MEDIA.EVERNOTE) {
                Toast.makeText(mActivity.get(), "分享失败", Toast.LENGTH_SHORT).show();
                if (t != null) {
                    LogUtils.d("throw:" + t.getMessage());
                }
            }

        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {

            Toast.makeText(mActivity.get(), "分享取消", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 屏幕横竖屏切换时避免出现window leak的问题
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mShareAction.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //   UMShareAPI.get(this).release();
    }

    @Override
    public void onSuccessHttp(String responseInfo, int resultCode) {
        super.onSuccessHttp(responseInfo, resultCode);
        switch (resultCode) {
            case UrlConfig.GET_INVITED_FRIEND_URL_CODE:
                LogUtils.d("获取分享好友URl成功：" + responseInfo);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String message = obj.optString("message");
                    if (code == 0) {
                        JSONObject obj1=obj.optJSONObject("result");
                        nr_url = obj1.optString("nr");
                        hdms = obj1.optString("hdms");
                        hdbt = obj1.optString("hdbt");
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
                        if (! StringUtil.isEmpty(message)){
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
    public void onFailureHttp(HttpException error, String msg) {
        super.onFailureHttp(error, msg);
        LogUtils.d("获取分享好友URl失败");
    }
}
