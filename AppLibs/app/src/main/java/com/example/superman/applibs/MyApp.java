package com.example.superman.applibs;

import android.app.Application;

import com.libs.crashinterception.CrashInterception;
import com.libs.crashinterception.CrashModel;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;


public class MyApp extends Application {

    private static RefWatcher sRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();

        CrashInterception.getInstance().init(this)
                //设置是否捕获异常，不弹出崩溃框
                .setEnable(true)
                //设置是否显示崩溃信息展示页面
                .setShowCrashMessageActivity(true)
                //是否回调异常信息，友盟等第三方崩溃信息收集平台会用到
                .setOnCrashListener(new CrashInterception.OnCrashListener() {
                    @Override
                    public void onCrash(Thread t, Throwable ex, CrashModel model) {

                    }
                });

        //LeakCanary初始化
        sRefWatcher = LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher() {
        return sRefWatcher;
    }
}
