package cn.handanlutong.parking.bean;

/**
 * Created by ww on 2017/9/14.
 */

public class TestDemo {
    private String key;
    private String date;
    public static String ROOTS_URL;
    public static String PAYS_URL;

    public static String getRootsUrl() {
        return ROOTS_URL;
    }

    public static void setRootsUrl(String rootsUrl) {
        ROOTS_URL = rootsUrl;
    }

    public static String getPaysUrl() {
        return PAYS_URL;
    }

    public static void setPaysUrl(String paysUrl) {
        PAYS_URL = paysUrl;
    }

    public TestDemo(){

    }
    public TestDemo(String key, String date) {
        this.key = key;
        this.date = date;
    }

    @Override
    public String toString() {
        return "TestDemo{" +
                "key=" + key +
                ", date='" + date + '\'' +
                '}';
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
