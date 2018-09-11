package com.tools.apps.wallpaper;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrixColorFilter;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.library.networklibrary.HttpUtils;
import com.library.networklibrary.observer.CommonObserver;
import com.library.networklibrary.observer.DataObserver;
import com.library.networklibrary.bean.BaseData;
import com.library.networklibrary.download.DownloadObserver;
import com.library.networklibrary.interceptor.Transformer;
import com.tools.apps.wallpaper.DesignPattern.prototype_model.WordDocument;
import com.tools.apps.wallpaper.bean.TestBean;
import com.tools.apps.wallpaper.bean.TestNewsBean;
import com.tools.apps.wallpaper.common.FreedomImageView;
import com.tools.apps.wallpaper.example.login.ExampleActivity4;
import com.tools.apps.wallpaper.http.ApiService;
import com.tools.apps.wallpaper.utils.AnimUtils;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FreedomImageView freedomImageView = findViewById(R.id.fiv);
        ImageView img = findViewById(R.id.img);
        Button mvpDemp = findViewById(R.id.MvpDemo);
        mvpDemp.setOnClickListener(this);

        final String url = "http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg";

        loadRegularPhoto(this, freedomImageView, true, url, true);

        Bitmap saturationBitmap = AnimUtils.
                setBitmapSaturation(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher), 0, 33, 33);
        img.setImageBitmap(saturationBitmap);

//        getWechatApi();

        int[] nums = {3, 2, 3};
        int target = 6;
        int[] sum = twoSum(nums, target);
        Log.e("sum", "" + sum[0] + ":" + sum[1]);


        /**
         * 原型模式
         */
        WordDocument wordDocument = new WordDocument();
        wordDocument.setmText("文档1");
        wordDocument.addImages("图片1");
        wordDocument.addImages("图片2");
        wordDocument.addImages("图片3");
        wordDocument.showDoc();

        //克隆副本
        WordDocument wordDocument2 = wordDocument.clone();
        wordDocument2.showDoc();
        wordDocument2.setmText("修改过的文档");
        wordDocument2.addImages("img");
        wordDocument2.showDoc();

        wordDocument.showDoc();



        /**
         * 网络框架使用
         */
        HttpUtils
                .createApi(ApiService.class)
                .getTestData()
                .compose(Transformer.<TestBean>switchSchedulers())
                .subscribe(new CommonObserver<TestBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        //错误处理
                        Log.d(TAG, "onSuccess: " + errorMsg);
                    }

                    @Override
                    protected void onSuccess(TestBean bookBean) {
                        //业务处理
                        Log.d(TAG, "onSuccess: " + bookBean.toString());
                    }
                });

        HttpUtils
                .createApi(ApiService.class)
                .getNewsTestData()
                .compose(Transformer.<BaseData<TestNewsBean>>switchSchedulers())
                .subscribe(new DataObserver<TestNewsBean>() {
                    @Override
                    protected void onError(String errorMsg) {
                        //错误处理
                        Log.e(TAG, "onSuccess: " + errorMsg);
                    }

                    @Override
                    protected void onSuccess(TestNewsBean data) {
                        //业务处理
                        Log.e(TAG, "onSuccess: " + data.toString());
                    }
                });

        String urls = "https://t.alipayobjects.com/L1/71/100/and/alipay_wap_main.apk";
        final String fileName = "alipay.apk";

        HttpUtils
                .downloadFile(urls)
                .subscribe(new DownloadObserver(fileName) {
                    @Override
                    public void onComplete() {

                    }

                    //可以通过配置tag用于取消下载请求
                    @Override
                    protected String setTag() {
                        return "download";
                    }

                    @Override
                    protected void onSuccess(long byteRead, long contentLength, int progress, boolean done, String filePath) {
                        Log.e(TAG, "onSuccess: " + "下 载中：" + progress + "%");
                        if (done) {
                            Log.e(TAG, "onSuccess: " + "下载文件路径：" + filePath);
                        }
                    }

                    @Override
                    protected void onError(String errorMsg) {
                    }

                });

    }

    /**
     * 跳转到微信
     */
    private void getWechatApi() {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // TODO: handle exception
        }
    }


    private static void loadRegularPhoto(final Context mContext, final ImageView imageView, boolean saturation, final String url, boolean execute) {
        if (!TextUtils.isEmpty(url)) {

            /**
             * 缩略图配置
             */
            final DrawableRequestBuilder<String> thumbnailRequest = Glide.with(mContext)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE);

            if (saturation) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP & saturation) {
                    AnimUtils.ObservableColorMatrix observableColorMatrix = new AnimUtils.ObservableColorMatrix();
                    observableColorMatrix.setSaturation(0);
                    imageView.setColorFilter(new ColorMatrixColorFilter(observableColorMatrix));
                }
            }


            DrawableRequestBuilder<String> regularRequest = Glide
                    .with(mContext)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .override(1000, 1000)
                    //先加载缩略图
                    .thumbnail(thumbnailRequest);

            if (execute) {
                regularRequest.into(imageView);
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.MvpDemo:
                startActivity(new Intent(this, ExampleActivity4.class));
                break;
        }
    }


    public int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    public int[] twoSums(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }

}
