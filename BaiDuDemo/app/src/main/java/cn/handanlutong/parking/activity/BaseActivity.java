package cn.handanlutong.parking.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;

import cn.handanlutong.parking.customview.YWLoadingDialog;
import cn.handanlutong.parking.http.ObserverCallBack;

import java.util.LinkedList;
import java.util.List;


/**
 * Activity基类
 * Created by zhangyonggang on 2017/4/6.
 */

public class BaseActivity extends FragmentActivity implements ObserverCallBack{
    protected HttpUtils httpUtils;// 网络访问声明
    // 管理运行的所有的activity
    public final static List<BaseActivity> mActivities = new LinkedList<BaseActivity>();
    public static BaseActivity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        synchronized (mActivities) {
            mActivities.add(this);
        }
        httpUtils = new HttpUtils();
        initView();
        initData();
        setLisener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        activity=this;
    }
    @Override
    protected void onPause() {
        super.onPause();
        activity=null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized (mActivities) {
            mActivities.remove(this);
        }
    }

    public static void killAll() {
        // 复制了一份mActivities 集合
        List<BaseActivity> copy;
        synchronized (mActivities) {


            copy = new LinkedList<BaseActivity>(mActivities);
        }
        for (BaseActivity activity : copy) {
            activity.finish();
        }
        // 杀死当前的进程
//        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public void setLisener() {
    }

    public void initData() {
    }

    public void initView() {
    }


    @Override
    public void onStartHttp() {
    }

    @Override
    public void onLoadingHttp(long total, long current, boolean isUploading) {

    }

    @Override
    public void onSuccessHttp(String responseInfo, int resultCode) {

    }

    @Override
    public void onFailureHttp(HttpException error, String msg) {

    }

    @Override
    public void onFailureHttp(HttpException error, String msg, int resultCode) {
    }

    @Override
    public void onSuccessHttp(String result, int i, Object extraData) {

    }
}
