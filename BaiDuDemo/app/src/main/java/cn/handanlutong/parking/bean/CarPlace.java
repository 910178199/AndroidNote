package cn.handanlutong.parking.bean;

/**
 * Created by ww on 2017/4/14.
 */

public class CarPlace {
    private String ccmc;//车场名称
    private String hphm;//号牌号码
    private String lwsj;//结束时间 离位时间
    private String pay;//支付  -1
    private String tcsc;//停车时长
    private String ddje;//订单金额

    private String cphm;//车牌号码
    private String rwsj;//开始时间 入位时间
    private String tckmc;//停车场名称
    private String jfsj;//计费时间
    private String status;//状态
    private String authkey;//唯一标识
    private String phoneNo;//手机号

    public String getLwsj() {
        return lwsj;
    }

    public void setLwsj(String lwsj) {
        this.lwsj = lwsj;
    }

    public String getRwsj() {
        return rwsj;
    }

    public void setRwsj(String rwsj) {
        this.rwsj = rwsj;
    }

    public String getDdje() {
        return ddje;
    }

    public void setDdje(String ddje) {
        this.ddje = ddje;
    }

    public String getCcmc() {
        return ccmc;
    }

    public void setCcmc(String ccmc) {
        this.ccmc = ccmc;
    }

    public String getHphm() {
        return hphm;
    }

    public void setHphm(String hphm) {
        this.hphm = hphm;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getTcsc() {
        return tcsc;
    }

    public void setTcsc(String tcsc) {
        this.tcsc = tcsc;
    }

    public String getAuthkey() {
        return authkey;
    }

    public void setAuthkey(String authkey) {
        this.authkey = authkey;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getCphm() {
        return cphm;
    }

    public void setCphm(String cphm) {
        this.cphm = cphm;
    }

    public String getTckmc() {
        return tckmc;
    }

    public void setTckmc(String tckmc) {
        this.tckmc = tckmc;
    }

    public String getJfsj() {
        return jfsj;
    }

    public void setJfsj(String jfsj) {
        this.jfsj = jfsj;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
