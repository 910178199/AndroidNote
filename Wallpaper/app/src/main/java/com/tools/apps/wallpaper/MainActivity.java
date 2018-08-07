package com.tools.apps.wallpaper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrixColorFilter;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tools.apps.wallpaper.common.FreedomImageView;
import com.tools.apps.wallpaper.example.login.ExampleActivity1;
import com.tools.apps.wallpaper.example.login.ExampleActivity2;
import com.tools.apps.wallpaper.example.login.ExampleActivity3;
import com.tools.apps.wallpaper.example.login.ExampleActivity4;
import com.tools.apps.wallpaper.utils.AnimUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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
}
