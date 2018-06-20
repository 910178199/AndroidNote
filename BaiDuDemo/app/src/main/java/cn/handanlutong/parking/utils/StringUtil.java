package cn.handanlutong.parking.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.ta.utdid2.android.utils.StringUtils;

import org.apache.shiro.crypto.hash.Md5Hash;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangyonggang on 2017/6/7.
 */

public class StringUtil {
    public static final int HASH_ITERATIONS = 1;
    /**
     * 判断是否是手机号
     *
     * @param phoneNumber
     * @return
     */
        public static boolean isPhoneNumber(String phoneNumber) {
        boolean isValid = false;
//        String expression = "^1[3|5|8][0-9]{9}$";
//        String expression = "^1(3[0-9]|5[0-35-9]|8[0-9]|4[5]|7[678])\\d{8}$";
        String expression = "^1(3[0-9]|5[0-35-9]|8[0-9]|4[57]|7[678])\\d{8}$";
        CharSequence inputStr = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
    /**
     * 判断给定字符串是否空白串 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     */
    public static boolean isEmpty(CharSequence input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }


    public static boolean isEmojiCharacter(char codePoint) {
        return !((codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD) || ((codePoint >= 0x20) && codePoint <= 0xD7FF)) || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }


    /**
     * 将分转换为元,去掉￥的结果
     * @return
     */
    public static String convertFentoYuanWithout(int y) {
        if (y < 100) {
            if( y < 10 ){
                return "0.0" + y;
            }
            return "0." + y;
        } else if (y % 100 == 0) {
            return y / 100 + ".00";
        } else {
            if( (y % 100) < 10 ){
                return y / 100 + ".0" + y % 100;
            }
            return y / 100 + "." + y % 100;
        }
    }


    /**
     * 将view转换成bitmap
     *
     * @param view
     * @return
     */
    public static Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();

        return bitmap;
    }

    /**
     * 显示键盘
     *
     * @param context
     * @param editText
     */
    public static void showKeyBoard(Activity context, EditText editText) {
        editText.requestFocus();
        InputMethodManager inputManager = (InputMethodManager) editText
                .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editText, 0);
    }
    /**
     * 缩回系统的软键盘,如果是弹出状态，则缩回去
     *
     * @param context
     */
    public static void hindKeyBoard(Activity context) {
        View view = context.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    /**
     * 手机号用****号隐藏中间数字
     *
     * @param phone
     * @return
     */
    public static String settingphone(String phone) {
        String phone_s = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        return phone_s;
    }

    /**
     *  手机号正则表达式  校验
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        String regExp = "^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$";
        Matcher m = p.matcher(mobiles);
        System.out.println(m.matches() + "---");
        return m.matches();
    }

    public static boolean isActivityRunning(Context mContext, String activityClassName){
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> info = activityManager.getRunningTasks(1);
        if(info != null && info.size() > 0){
            ComponentName component = info.get(0).topActivity;
            String str=component.getClassName();
            if(activityClassName.equals(component.getClassName())){
                return true;
            }
        }
        return false;
    }

    /**
     * 隐藏 车牌 中间 几位
     */
    public static String CarHPHM(String str){
        String str1=str.substring(2,5);
        String str2=str.replaceAll(str1,"***");
        return str2;
    }

    /**
     * 将米转换成km
     * @return
     */
    public static String convertMtoKM(int m) {
        if (m < 1000) {
            return m + "米";
        } else if (m % 1000 == 0) {
            int k1 = m / 1000;
            return k1 + "公里";
        } else {
            int k2 = m / 1000;
            int meter = m % 1000;
            int k3;
            if (meter % 10 == 0) {
                if (meter % 100 == 0) {
                    k3 = meter / 100;
                } else {
                    k3 = meter / 10;
                }
            } else {
                k3 = meter;
            }
            // returnn k2 + "." + k3 +"km";
            return (Math.round(Float.parseFloat(k2 + "." + k3)) + "公里");
        }
    }

    //生成编号
    public static String GetBH(){//15位
        //当前日期(yyMMddHHmmss)+随机生成(100-999)
        return StringUtil.GetNumber().substring(2, 14)+(int)(Math.random()*900+100);
    }

    //生成流水号
    public static String GetNumber(){
        Date dt= new Date();
        //创建时间
        return (new SimpleDateFormat("yyyyMMddHHmmss")).format(dt);
    }

    /**
     * 获得散列2次的 MD5
     * @param source 原文
     * @param salt 盐
     * @return MD5密文
     * @date 2016-3-8下午8:46:05
     */
    public static String getMD5(String source, String salt) {
        Md5Hash md5Hash = new Md5Hash(source, salt, StringUtil.HASH_ITERATIONS);
        return md5Hash.toString().toUpperCase();
    }

}
