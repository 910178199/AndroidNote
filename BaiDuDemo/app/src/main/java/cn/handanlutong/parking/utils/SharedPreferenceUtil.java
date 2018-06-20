package cn.handanlutong.parking.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.iflytek.cloud.thirdparty.K;
import com.iflytek.cloud.thirdparty.V;

import java.util.Map;

/**
 *  sharedPreference信息保存类
 * Created by zhangyonggang on 2017/6/7.
 */

public class SharedPreferenceUtil {
    public static final String LOGIN_INFO = "login_sp";//登录保存信息
    public static final String ACTION_LIST = "ACTION_LIST";//活动列表
    public static final String FaPiaoMore = "FaPiaoMore";//发票 添加 更多页面
    public static final String MessageSystem = "MessageSystem";//发票 添加 更多页面

    private SharedPreferences sp = null;
    private static SharedPreferenceUtil spUtil = null;

    public static SharedPreferenceUtil init(Context context, String sp_name, int mode) {
        spUtil = new SharedPreferenceUtil(context, sp_name, mode);
        return spUtil;
    }

    public SharedPreferenceUtil(Context context, String sp_name, int mode) {
        super();
        sp = context.getSharedPreferences(LOGIN_INFO, mode);
    }

    public String getString(String key) {
        return sp.getString(key, "");
    }

    public int getInt(String key){
        return sp.getInt(key, 0);
    }

    public void put(String key, String value){
        Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void putInt(String key, int value){
        Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void removeCurrentUserInfo(){
        Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

}
