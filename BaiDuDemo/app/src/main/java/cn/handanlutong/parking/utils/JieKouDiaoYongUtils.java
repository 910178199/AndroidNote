package cn.handanlutong.parking.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Set;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.activity.BaseActivity;
import cn.handanlutong.parking.activity.LoginActivity;
import cn.handanlutong.parking.http.UrlConfig;
import cn.handanlutong.parking.zoom.DataCleanManager;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import static android.content.ContentValues.TAG;

/**
 * Created by ww on 2017/11/21.
 */

public class JieKouDiaoYongUtils {

    private static final String[] PERMISSION_EXTERNAL_STORAGE = new String[] {
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int REQUEST_EXTERNAL_STORAGE = 100;

    /**
     * 单点登录 弹框
     */
    public static void loginTanKuan(final Context context){
        final Dialog dialo = new Dialog(context, R.style.Dialog);
        dialo.setCanceledOnTouchOutside(false);//设置外部不可点击
        dialo.setCancelable(false);
        dialo.setContentView(R.layout.mobile_dialog);
//        dialo.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST); //安卓某些机型不可点击
//        dialo.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        TextView tv = (TextView) dialo.findViewById(R.id.mobile_tv);
        tv.setText("您的账号已在其他设备登录");
        JPushInterface.setAlias(context, "5555", new TagAliasCallback() {

            @Override
            public void gotResult(int arg0, String arg1, Set<String> arg2) {
            }
        });
        Button btn_know = (Button) dialo.findViewById(R.id.btn_know);
        btn_know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UrlConfig.isSetDialogShow = true;
                DataCleanManager.clearAllCache(context);//清空缓存
                SharedPreferenceUtil spUtil = SharedPreferenceUtil.init(context,SharedPreferenceUtil.LOGIN_INFO, Activity.MODE_PRIVATE);
                spUtil.removeCurrentUserInfo();
                SharedPreferenceUtil spUtil2 = SharedPreferenceUtil.init(context, SharedPreferenceUtil.FaPiaoMore, Activity.MODE_PRIVATE);
                spUtil2.removeCurrentUserInfo();
                dialo.dismiss();
                BaseActivity.killAll();
                JPushInterface.setAlias(context, "5555", new TagAliasCallback() {

                    @Override
                    public void gotResult(int arg0, String arg1, Set<String> arg2) {
                    }
                });
                Intent intent = new Intent(context, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });
        dialo.show();
    }

    /**
     * 获取Android版本号
     * @param context
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(
                    "cn.handanlutong.parking", 0).versionName;
            Log.d("mainin", verName + "");
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }
        return verName;
    }

    /**
     * 检查当前程序的版本号
     *
     * @return
     */
    public static int getVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        //getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        LogUtils.d("当前程序的版本号：" + packInfo.versionCode);
        return packInfo.versionCode;
    }

    //按钮 不能在一秒之内 重复点击
    public static abstract class NoDoubleClickListener implements View.OnClickListener {

        public static final int MIN_CLICK_DELAY_TIME = 1000;
        private long lastClickTime = 0;

        @Override
        public void onClick(View v) {
            long currentTime = Calendar.getInstance().getTimeInMillis();
            if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                lastClickTime = currentTime;
                onNoDoubleClick(v);
            }
        }

        protected abstract void onNoDoubleClick(View v);
    }

    /**
     * 设置禁止输入表情符号
     */
    public static void NoShuRuBiaoQingFuHao(final Context context, final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editText.setCursorVisible(true);
            }

            @Override
            public void afterTextChanged(Editable s) {
                editText.setCursorVisible(true);
                int index = editText.getSelectionStart() - 1;
                if (index > 0) {
                    if (StringUtil.isEmojiCharacter(s.charAt(index))) {
                        Editable edit = editText.getText();
                        edit.delete(s.length() - 2, s.length());
                        ToastUtil.makeShortText(context, "不支持输入表情符号");
                    }
                }
            }
        });
    }

    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    public static void verifyStoragePermissions(Activity activity) {
        int permissionWrite = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permissionWrite != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSION_EXTERNAL_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }
}
