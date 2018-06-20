/*
 * ParkingActivity.java
 * Copyright(C) 20xx-2015 xxxxxx鍏徃
 * All rights reserved.
 * -----------------------------------------------
 * 2017-08-17 Created
 */
package cn.handanlutong.parking.bean;

/**
 * null
 * 
 * @author 
 * @version 1.0 2017-08-17
 */
public class ParkingActivityVo {

    /**
     * 活动ID
     */
    private String id;
    /**
     * 活动创建时间
     */
    private String hdcjsj;
    /**
     * 活动过期时间
     */
    private String hdgqsj;
    /**
     * 活动开始时间
     */
    private String hdsksj;
    /**
     * 活动标题
     */
    private String hdbt;
    /**
     * 活动内容
     */
    private String nr;
    /**
     * 活动标题图片
     */
    private String hdbttp;
    /**
     * 活动内容图片1
     */
    private String hdnrtp1;
    /**
     * 活动内容图片2
     */
    private String hdnrtp2;
    /**
     * 活动内容图片3
     */
    private String hdnrtp3;
    /**
     * 活动内容图片4
     */
    private String hdnrtp4;
    /**
     * 活动描述
     */
    private String hdms;
    /**
     * 活动时间单位（天）
     */
    private String hdsjdw;
    /**
     * 活动是否启用
     */
    private String hdsfqy;
    /**
     * 活动是否开启
     */
    private String hdsfkq;
    /**
     * 活动是否开启
     */
    private String authKey;
    private String userId;
    private String sfgq;
    private ParkActivity activityVo;

    public ParkActivity getActivityVo() {
        return activityVo;
    }

    public void setActivityVo(ParkActivity activityVo) {
        this.activityVo = activityVo;
    }

    public String getSfgq() {
        return sfgq;
    }

    public void setSfgq(String sfgq) {
        this.sfgq = sfgq;
    }

    public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAuthKey() {
		return authKey;
	}
	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}
	public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getHdcjsj() {
        return hdcjsj;
    }
    public void setHdcjsj(String hdcjsj) {
        this.hdcjsj = hdcjsj == null ? null : hdcjsj.trim();
    }
    public String getHdgqsj() {
        return hdgqsj;
    }
    public void setHdgqsj(String hdgqsj) {
        this.hdgqsj = hdgqsj == null ? null : hdgqsj.trim();
    }
    public String getHdsksj() {
        return hdsksj;
    }
    public void setHdsksj(String hdsksj) {
        this.hdsksj = hdsksj == null ? null : hdsksj.trim();
    }
    public String getHdbt() {
        return hdbt;
    }
    public void setHdbt(String hdbt) {
        this.hdbt = hdbt == null ? null : hdbt.trim();
    }
    public String getNr() {
        return nr;
    }
    public void setNr(String nr) {
        this.nr = nr == null ? null : nr.trim();
    }
    public String getHdbttp() {
        return hdbttp;
    }
    public void setHdbttp(String hdbttp) {
        this.hdbttp = hdbttp == null ? null : hdbttp.trim();
    }
    public String getHdnrtp1() {
        return hdnrtp1;
    }
    public void setHdnrtp1(String hdnrtp1) {
        this.hdnrtp1 = hdnrtp1 == null ? null : hdnrtp1.trim();
    }
    public String getHdnrtp2() {
        return hdnrtp2;
    }
    public void setHdnrtp2(String hdnrtp2) {
        this.hdnrtp2 = hdnrtp2 == null ? null : hdnrtp2.trim();
    }
    public String getHdnrtp3() {
        return hdnrtp3;
    }
    public void setHdnrtp3(String hdnrtp3) {
        this.hdnrtp3 = hdnrtp3 == null ? null : hdnrtp3.trim();
    }
    public String getHdnrtp4() {
        return hdnrtp4;
    }
    public void setHdnrtp4(String hdnrtp4) {
        this.hdnrtp4 = hdnrtp4 == null ? null : hdnrtp4.trim();
    }
    public String getHdms() {
        return hdms;
    }
    public void setHdms(String hdms) {
        this.hdms = hdms == null ? null : hdms.trim();
    }
    public String getHdsjdw() {
        return hdsjdw;
    }
    public void setHdsjdw(String hdsjdw) {
        this.hdsjdw = hdsjdw == null ? null : hdsjdw.trim();
    }
    public String getHdsfqy() {
        return hdsfqy;
    }
    public void setHdsfqy(String hdsfqy) {
        this.hdsfqy = hdsfqy == null ? null : hdsfqy.trim();
    }
    public String getHdsfkq() {
        return hdsfkq;
    }
    public void setHdsfkq(String hdsfkq) {
        this.hdsfkq = hdsfkq == null ? null : hdsfkq.trim();
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", hdcjsj=").append(hdcjsj);
        sb.append(", hdgqsj=").append(hdgqsj);
        sb.append(", hdsksj=").append(hdsksj);
        sb.append(", hdbt=").append(hdbt);
        sb.append(", nr=").append(nr);
        sb.append(", hdbttp=").append(hdbttp);
        sb.append(", hdnrtp1=").append(hdnrtp1);
        sb.append(", hdnrtp2=").append(hdnrtp2);
        sb.append(", hdnrtp3=").append(hdnrtp3);
        sb.append(", hdnrtp4=").append(hdnrtp4);
        sb.append(", hdms=").append(hdms);
        sb.append(", hdsjdw=").append(hdsjdw);
        sb.append(", hdsfqy=").append(hdsfqy);
        sb.append(", hdsfkq=").append(hdsfkq);
        sb.append("]");
        return sb.toString();
    }
    public class ParkActivity{
        private String hdbttp;
        private String nr;

        public String getHdbttp() {
            return hdbttp;
        }

        public void setHdbttp(String hdbttp) {
            this.hdbttp = hdbttp;
        }

        public String getNr() {
            return nr;
        }

        public void setNr(String nr) {
            this.nr = nr;
        }
    }
}