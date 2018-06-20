package cn.handanlutong.parking.http;


import android.util.Log;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.http.client.multipart.HttpMultipartMode;
import com.lidroid.xutils.http.client.multipart.MultipartEntity;
import com.lidroid.xutils.http.client.multipart.content.FileBody;
import cn.handanlutong.parking.utils.LogUtils;


import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangyonggang on 2017/6/7.
 */

public class AnsynHttpRequest {
    static final int POST = 1; // post 提交
    static final int GET = 2; // get 提交
    static final int DELETE = 3; // get 提交
    public static String PARAM="param";
    private static RequestParams params;
    public static Gson parser = new Gson();

    /***
     * get和post请求方法
     *
     * @param sendType
     *            请求类型：get和post

     * @param url
     *            请求地址

     * @param callBack
     *            异步回调

     * @param i
     *            请求的方法对应的int值（整个项目中唯一不重复的）
     */
    static void requestGetOrPost(final int sendType, String url, Map<String,String> map, final ObserverCallBack callBack, HttpUtils httpUtils, final int i) {
        requestGetOrPost(sendType, url, map, callBack,httpUtils, i, null);
    }

    static void requestGetOrPosts(final int sendType, String url, JSONObject jobj, final ObserverCallBack callBack, HttpUtils httpUtils, final int i) {
        requestGetOrPosts(sendType, url, jobj, callBack,httpUtils, i, null);
    }




    /***
     * get和post请求方法
     *
     * @param sendType
     *            请求类型：get和post

     * @param url
     *            请求地址

     * @param callBack
     *            异步回调

     * @param i
     *            请求的方法对应的int值（整个项目中唯一不重复的）
     * @param extraData
     *            用于传递某些额外的参数.
     */
    static void requestGetOrPost(final int sendType, String url,  Map<String,String> map,
                                 final ObserverCallBack callBack,HttpUtils httpUtils, final int i, final Object extraData) {
        httpUtils.configCurrentHttpCacheExpiry(10 * 1000);
        url = Utf8URLencode(url);
        params = new RequestParams();
        LogUtils.d("Url::" + url);
        LogUtils.d("qqqqmap求参数："+map.toString());
        switch (sendType) {
            case POST:
                if (null != map && !map.isEmpty()){
                    for (Map.Entry<String,String> entry : map.entrySet()){
                        params.addBodyParameter(entry.getKey(),entry.getValue());
                        LogUtils.d("entry.getKey()："+entry.getKey());
                        LogUtils.d("entry.getValue()："+entry.getValue());
                    }
                }
                httpUtils.send(HttpRequest.HttpMethod.POST, url, params,
                        new RequestCallBack<String>() {

                            @Override
                            public void onStart() {
                                callBack.onStartHttp();
                            }

                            @Override
                            public void onLoading(long total, long current,
                                                  boolean isUploading) {
                                callBack.onLoadingHttp(total, current, isUploading);
                            }

                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                Log.e("Log", "POST访问成功==" + responseInfo.result);
                                if (extraData == null) {
                                    callBack.onSuccessHttp(responseInfo.result, i);
                                } else {
                                    callBack.onSuccessHttp(responseInfo.result, i, extraData);
                                }
                            }

                            @Override
                            public void onFailure(HttpException error, String msg) {
                                Log.e("Log",
                                        "POST访问失败==" + error.getExceptionCode()
                                                + ":" + msg);
//                                callBack.onFailureHttp(error, msg);
                                callBack.onFailureHttp(error, msg,i);
                            }
                        });
                break;
            case GET:

                break;
            case DELETE:
                break;
            default:
                break;
        }

    }

    static void requestGetOrPosts(final int sendType, String url,  JSONObject jobj,
                                 final ObserverCallBack callBack,HttpUtils httpUtils, final int i, final Object extraData) {
        httpUtils.configCurrentHttpCacheExpiry(10 * 1000);
        url = Utf8URLencode(url);
        params = new RequestParams();
        LogUtils.d("Url::" + url);
        LogUtils.d("WWWW求参数："+jobj.toString());
        switch (sendType) {
            case POST:
                params.addHeader("Content-Type", "application/json;charset=UTF-8");
                try {
                    params.setBodyEntity(new StringEntity(jobj.toString(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                httpUtils.send(HttpRequest.HttpMethod.POST, url, params,
                        new RequestCallBack<String>() {

                            @Override
                            public void onStart() {
                                callBack.onStartHttp();
                            }

                            @Override
                            public void onLoading(long total, long current,
                                                  boolean isUploading) {
                                callBack.onLoadingHttp(total, current, isUploading);
                            }

                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                Log.e("Log", "POST访问成功==" + responseInfo.result);
                                if (extraData == null) {
                                    callBack.onSuccessHttp(responseInfo.result, i);
                                } else {
                                    callBack.onSuccessHttp(responseInfo.result, i, extraData);
                                }
                            }

                            @Override
                            public void onFailure(HttpException error, String msg) {
                                Log.e("Log",
                                        "POST访问失败==" + error.getExceptionCode()
                                                + ":" + msg);
//                                callBack.onFailureHttp(error, msg);
                                callBack.onFailureHttp(error, msg,i);
                            }
                        });
                break;
            case GET:

                break;
            case DELETE:
                break;
            default:
                break;
        }

    }

    /**
     * Utf8URL编码
     *
     * @return
     */
    static String Utf8URLencode(String text) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c >= 0 && c <= 255) {
                result.append(c);
            } else {
                byte[] b = new byte[0];
                try {
                    b = Character.toString(c).getBytes("UTF-8");
                } catch (Exception ex) {
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0)
                        k += 256;
                    result.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return result.toString();
    }


    /**
     * 上传头像
     * @param url
     * @param files
     * @param callBack
     * @param resultCode
     */
    public static void uploadFile(final String url, final List<File> files,
                                  final ObserverCallBack callBack, final int resultCode) {

        new Thread(new Runnable() {

            @Override
            public void run() {
                RequestParams params = new RequestParams();
                MultipartEntity entity = new MultipartEntity(
                        HttpMultipartMode.BROWSER_COMPATIBLE);
                if (files != null) {

                    for (int i = 0; i < files.size(); i++) {

                        FileBody fileBody = new FileBody(files.get(i),
                                "image/jpg");
                        entity.addPart("claimPictureFile", fileBody);

                    }
                }

                params.setBodyEntity(entity);
                HttpUtils http = new HttpUtils();
                http.send(HttpRequest.HttpMethod.POST, url, params,
                        new RequestCallBack<String>() {

                            @Override
                            public void onStart() {
                                callBack.onStartHttp();
                            }

                            @Override
                            public void onLoading(long total, long current,
                                                  boolean isUploading) {
                                callBack.onLoadingHttp(total, current,isUploading);
                            }

                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                callBack.onSuccessHttp(responseInfo.result,
                                        resultCode);
                                Log.e("Log", "POST访问成功==" + responseInfo.result);
                            }

                            @Override
                            public void onFailure(HttpException error,
                                                  String msg) {
                                callBack.onFailureHttp(error, msg);
                                Log.e("Log","POST访问失败==" + error.getExceptionCode() + ":" + msg);
                            }
                        });
            }
        }).start();

    }
}
