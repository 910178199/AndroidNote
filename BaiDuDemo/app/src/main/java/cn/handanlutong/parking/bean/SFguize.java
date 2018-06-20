package cn.handanlutong.parking.bean;

/**
 * Created by ww on 2017/4/7.
 */

public class SFguize {
    private int btdwjg;//白天单位价格
    private int btdwsc;//白天单位时长
    private int btzgjg;//白天最高价格
    private String bz;//标注
    private String btsjd;//白天时间段
    private int mfsc;//免费时长 空值或者0，代表没有
    private int qbjg;//起步时长  空值或者0，代表没有
    private int qbsj;//起步时间
    private int sfje;//收费金额
    private int sflx;//收费类型   1.时段  2.全日  3.按次
    private String sfmc;//收费名称
    private int wsdwjg;//晚上单位价格
    private int wsdwsc;//晚上单位时长
    private String wssjd;//晚上时间段
    private int wszgjg;//晚上最高价格

    public int getWsdwsc() {
        return wsdwsc;
    }

    public void setWsdwsc(int wsdwsc) {
        this.wsdwsc = wsdwsc;
    }

    public String getSfmc() {
        return sfmc;
    }

    public void setSfmc(String sfmc) {
        this.sfmc = sfmc;
    }

    public int getSfje() {
        return sfje;
    }

    public void setSfje(int sfje) {
        this.sfje = sfje;
    }

    public int getQbsj() {
        return qbsj;
    }

    public void setQbsj(int qbsj) {
        this.qbsj = qbsj;
    }

    public int getBtdwsc() {
        return btdwsc;
    }

    public void setBtdwsc(int btdwsc) {
        this.btdwsc = btdwsc;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public int getBtdwjg() {
        return btdwjg;
    }

    public void setBtdwjg(int btdwjg) {
        this.btdwjg = btdwjg;
    }

    public int getBtzgjg() {
        return btzgjg;
    }

    public void setBtzgjg(int btzgjg) {
        this.btzgjg = btzgjg;
    }

    public String getBtsjd() {
        return btsjd;
    }

    public void setBtsjd(String btsjd) {
        this.btsjd = btsjd;
    }

    public int getMfsc() {
        return mfsc;
    }

    public void setMfsc(int mfsc) {
        this.mfsc = mfsc;
    }

    public int getQbjg() {
        return qbjg;
    }

    public void setQbjg(int qbjg) {
        this.qbjg = qbjg;
    }

    public int getSflx() {
        return sflx;
    }

    public void setSflx(int sflx) {
        this.sflx = sflx;
    }

    public int getWsdwjg() {
        return wsdwjg;
    }

    public void setWsdwjg(int wsdwjg) {
        this.wsdwjg = wsdwjg;
    }

    public String getWssjd() {
        return wssjd;
    }

    public void setWssjd(String wssjd) {
        this.wssjd = wssjd;
    }

    public int getWszgjg() {
        return wszgjg;
    }

    public void setWszgjg(int wszgjg) {
        this.wszgjg = wszgjg;
    }
}
