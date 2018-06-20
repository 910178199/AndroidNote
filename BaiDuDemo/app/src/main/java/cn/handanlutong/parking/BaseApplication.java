package cn.handanlutong.parking;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.os.Vibrator;

import com.baidu.mapapi.SDKInitializer;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import cn.handanlutong.parking.baidu.service.LocationService;
import cn.handanlutong.parking.utils.NavigateUtils;
import cn.jpush.android.api.JPushInterface;

public class BaseApplication extends Application {
    public LocationService locationService;
    public Vibrator mVibrator;
    private static BaseApplication application;
    public static final String TTSAppID = "9774708";

    {
        //微信和QQ开放平台对应的AppId和Appkey
        PlatformConfig.setWeixin("wx542c9c76b9331d84", "fcfd0fdaf848cc7a57af9461d9b86ee8");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        /***
         * 初始化定位sdk，建议在Application中创建
         */
        locationService = new LocationService(getApplicationContext());
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        SDKInitializer.initialize(getApplicationContext());
        application = this;

        //初始化极光推送sdk
        JPushInterface.setDebugMode(false);//正式版的时候设置false，关闭调试
        JPushInterface.init(getApplicationContext());

        //开启debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
        Config.DEBUG = true;
        UMShareAPI.get(this);

        PlatformConfig.setWeixin("wx542c9c76b9331d84", "534d39622df801861e49be25eb3e9ab2");
        PlatformConfig.setQQZone("1106242822", "zgeLS6emkNgVfsMF");
    }

    public static Context getApplication() {
        return application;
    }
}
