package cn.handanlutong.parking.http;


import com.lidroid.xutils.exception.HttpException;

/**
 * 请求的回调接口
 * Created by zhangyonggang on 2017/4/6.
 */

public interface ObserverCallBack {
    void onStartHttp();

    void onLoadingHttp(long total, long current, boolean isUploading);

    void onSuccessHttp(String responseInfo, int resultCode);

    void onFailureHttp(HttpException error, String msg);

    void onFailureHttp(HttpException error, String msg, int resultCode);

    void onSuccessHttp(String result, int i, Object extraData);
}
