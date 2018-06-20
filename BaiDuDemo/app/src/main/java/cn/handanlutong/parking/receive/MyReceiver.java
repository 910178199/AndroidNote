package cn.handanlutong.parking.receive;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.handanlutong.parking.activity.MainActivity;
import cn.handanlutong.parking.bean.CarPlace;
import cn.handanlutong.parking.bean.FirstEvent;
import cn.handanlutong.parking.utils.JieKouDiaoYongUtils;
import cn.handanlutong.parking.utils.LogUtils;
import cn.handanlutong.parking.utils.StringUtil;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by ww on 2017/3/24.
 * <p>
 * 自定义接收器
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";
    private PendingIntent pit;
    CarPlace place;
    final String HOME_DIALOG_REASON = "homereason";
    final String HOME_DIALOG_REASON_HOME = "homekey";


    @Override
    public void onReceive(Context context, Intent intent) {
        pit = PendingIntent.getActivity(context, 1, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
        String Intent_Action = intent.getAction();
        if ("cn.handanlutong.parking".equals(Intent_Action)) {
            Log.e("BroadcastReceiver", "onReceive");
        }
        if (Intent_Action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
            String reason = intent.getStringExtra(HOME_DIALOG_REASON);
            if (reason != null && reason.equals(HOME_DIALOG_REASON_HOME)) {
                Toast.makeText(context, "点击Home键", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle, context));
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            MyReceiver(context, bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
            //打开自定义的Activity
            Intent i = new Intent(context, MainActivity.class);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(i);
            //点击一个通知之后取消掉所有notification
            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            nm.cancelAll();
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle, final Context context) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }
                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Log.d("jpush", "" + json.toString());
                    Iterator<String> it = json.keys();
                    Log.d("jpush", "" + it.toString());
                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                        Log.d("jpush", "" + json.optString(myKey));
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }
            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
            if (key.equals("cn.jpush.android.ALERT")) {
                Log.d("jpush", "1" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    //send msg to MainActivity
    private void MyReceiver(final Context context, Bundle bundle) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String data = new String();
        data = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        Log.d("jpush", "*********" + data);
        Map<String, CarPlace> map = new HashMap<String, CarPlace>();
        Gson gson = new Gson();
        place = gson.fromJson(bundle.getString(JPushInterface.EXTRA_MESSAGE), CarPlace.class);
        LogUtils.d("JPushInterface.EXTRA_MESSAGE  is :" + JPushInterface.EXTRA_MESSAGE);
        map.put("message", place);
        EventBus.getDefault().post(new FirstEvent("wangwei", place));
        if (place.getStatus().equals("4")) {//单点登录
            SharedPreferences sharedPreferences = context.getSharedPreferences("share_app", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isFirstRun", true);
            editor.commit();
            boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", false);
            if (isFirstRun) {
                SharedPreferences sharedPreferences2 = context.getSharedPreferences("share_app", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                editor2.putBoolean("isFirstRun", false);
                editor2.commit();
                final SharedPreferences sp = context.getSharedPreferences("login_sp", Context.MODE_PRIVATE);
                String salt = sp.getString("authkey", "");
                Log.d("jpush", "00000000" + salt + "-----------new" + place.getAuthkey());
                if (!StringUtil.isEmpty(salt) && !place.getAuthkey().equals(salt)) {
                    JieKouDiaoYongUtils.loginTanKuan(context);
                }
            } else {
                //false  不做任何处理
            }
        } else if (place.getStatus().equals("8101")) {//已现金支付通知
            EventBus.getDefault().post(new FirstEvent("message3", place));
        } else if (place.getStatus().equals("2001")) {//正常入场通知 违法入场不刷新
            EventBus.getDefault().post(new FirstEvent("message1", place));
        } else if (place.getStatus().equals("2101") || place.getStatus().equals("2102") || place.getStatus().equals("2103")) {//正常离场通知
            EventBus.getDefault().post(new FirstEvent("message0", place));
        } else if (place.getStatus().equals("4001") || place.getStatus().equals("4002") || place.getStatus().equals("4003")){ //月卡入场
            EventBus.getDefault().post(new FirstEvent("monthCardIn", place));
        } else if (place.getStatus().equals("4101") || place.getStatus().equals("4103") || place.getStatus().equals("4104")){ //月卡离场
            EventBus.getDefault().post(new FirstEvent("monthCardOut", place));
        } else if (place.getStatus().equals("4102")){ //月卡补差价
            EventBus.getDefault().postSticky(new FirstEvent("monthCard", place)); //发送粘性消息 全局都可以收到
        }
    }

    /**
     * 判断程序是否打开 * @returnn
     */
    public static boolean isRunningInForeground(Context context) {
        boolean isActivityFound = false;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> services = activityManager.getRunningTasks(1);
        if (services.get(0).topActivity.getPackageName().toString().equalsIgnoreCase(context.getPackageName().toString())) {
            isActivityFound = true;
        }
        return isActivityFound;
    }
}
