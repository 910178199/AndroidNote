package cn.handanlutong.parking.bean;

/**
 * Created by ww on 2017/4/24.
 */

public class HistoryBean {
    public int _id;
    /** 地址 */
    public String address;
    public String city;
    public String district;
    public HistoryBean() {
        super();
    }

    public HistoryBean(String address, String city, String district) {
        super();
        this.address = address;
        this.city = city;
        this.district =district;
    }

    @Override
    public String toString() {
        return "HistoryBean [_id=" + _id + ", address=" + address + "]";
    }
}
