package cn.handanlutong.parking.utils;


import com.google.gson.Gson;
import cn.handanlutong.parking.bean.LoginInfoBean;


/**
 * Created by zhangyonggang on 2017/6/8.
 */

public class ParserJsonBean {
    public static Gson parser = new Gson();
    public static LoginInfoBean parserLogin(String strJson) {
        Gson gson = new Gson();
        LoginInfoBean logininfobean=gson.fromJson(strJson,LoginInfoBean.class);
        LogUtils.d("logininfobean  is :"+logininfobean.toString());
        return null;
    }
}
