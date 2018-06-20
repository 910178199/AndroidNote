package cn.handanlutong.parking.bean;
/**
 * Created by ww on 2017/4/21.
 */

public class FirstEvent {
    private String mMsg;
    private CarPlace carPlace;
    public FirstEvent(String msg, CarPlace place) {
        this.mMsg = msg;
        this.carPlace = place;
    }
    public String getMsg(){
        return mMsg;
    }

    public void setmMsg(String mMsg) {
        this.mMsg = mMsg;
    }

    public CarPlace getCarPlace() {
        return carPlace;
    }
}
