package com.libs.crashinterception;

import android.content.Context;
import android.content.Intent;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

public class CrashInterception implements Thread.UncaughtExceptionHandler {

    private Context mContext;
    private Thread.UncaughtExceptionHandler mExceptionHandler;

    private static final CrashInterception ourInstance = new CrashInterception();

    public static CrashInterception getInstance() {
        return ourInstance;
    }

    private CrashInterception() {
    }

    private Builder mBuilder;

    public Builder init(Context mContext) {
        this.mContext = mContext;
        mExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mBuilder = new Builder();
        return mBuilder;
    }

    public interface OnCrashListener {
        void onCrash(Thread t, Throwable ex, CrashModel model);
    }


    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (mBuilder == null) return;
        CrashModel crashModel = parseCrash(e);
        if (mBuilder.mOnCrashListener != null) {
            mBuilder.mOnCrashListener.onCrash(t, e, crashModel);
        }
        //跳转到崩溃拦截页面，否则交给系统处理
        if (mBuilder.mEnable) {
            handleException(crashModel);
        } else {
            if (mExceptionHandler != null) {
                mExceptionHandler.uncaughtException(t, e);
            }
        }

    }

    private void handleException(CrashModel crashModel) {
        if (mBuilder.mEnable && mBuilder.mShowCrashMessageActivity) {
            Intent intent = new Intent(mContext, CrashActivity.class);
            intent.putExtra(CrashActivity.CRASH_MODEL, crashModel);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    private CrashModel parseCrash(Throwable throwable) {
        CrashModel crashModel = new CrashModel();
        crashModel.setEx(throwable);
        crashModel.setTime(new Date().getTime());
        StringBuilder sb = new StringBuilder();

        //格式化输出
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        pw.flush();

        String exceptiongMsg = throwable.getMessage();
        String exceptionType = throwable.getClass().getName();

        sb.append(throwable.getMessage());
        sb.append("\n");
        if (throwable.getStackTrace() != null && throwable.getStackTrace().length > 0) {
            StackTraceElement element = throwable.getStackTrace()[0];
            crashModel.setExceptionMsg(exceptiongMsg);
            crashModel.setLineNumber(element.getLineNumber());
            crashModel.setFileName(element.getFileName());
            crashModel.setMethodName(element.getMethodName());
            crashModel.setExceptionType(exceptionType);
        }
        crashModel.setFullException(sw.toString());
        return crashModel;
    }


    public class Builder {
        //设置是否捕获异常，不弹出崩溃框
        private boolean mEnable;
        //设置是否显示崩溃信息展示页面
        private boolean mShowCrashMessageActivity;
        //是否回调异常信息，友盟等第三方崩溃信息收集平台会用到
        private OnCrashListener mOnCrashListener;

        public Builder setEnable(boolean mEnable) {
            this.mEnable = mEnable;
            return mBuilder;
        }

        public Builder setShowCrashMessageActivity(boolean isShowCrashMessageActivity) {
            this.mShowCrashMessageActivity = isShowCrashMessageActivity;
            return mBuilder;
        }

        public void setOnCrashListener(OnCrashListener mOnCrashListener) {
            this.mOnCrashListener = mOnCrashListener;
        }
    }

}
