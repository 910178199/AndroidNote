package com.app.tools.crashinterceptions.test;

import android.app.Application;

import com.app.tools.crashinterceptions.CrashInterception;
import com.app.tools.crashinterceptions.CrashModel;


public class MyApplication extends Application {

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
    }
}
