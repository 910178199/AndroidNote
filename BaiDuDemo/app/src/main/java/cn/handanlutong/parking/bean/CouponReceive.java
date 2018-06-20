/*
 * CouponReceive.java
 * Copyright(C) 20xx-2015 xxxxxx鍏徃
 * All rights reserved.
 * -----------------------------------------------
 * 2017-08-28 Created
 */
package cn.handanlutong.parking.bean;

import java.io.Serializable;

/**
 * null
 *
 * @author
 * @version 1.0 2017-08-28
 */
public class CouponReceive implements Serializable {

    /**
     * 用户领取表记录ID
     */
    private Long id;
    /**
     * 优惠劵记录数量ID
     */
    private String yhjslid;
    /**
     * 关联优惠劵ID
     */
    private String yhjid;
    /**
     * 优惠劵序列号
     */
    private String yhjxlh;
    /**
     * 用户ID
     */
    private String userid;
    /**
     * 领取的优惠劵是否被使用 0 否 1 是
     */
    private String sfbsy;
    /**
     * 优惠劵金额(分)
     */
    private Integer yhjje;
    /**
     * 优惠劵有效期（天数）
     */
    private String yhjyxq;
    /**
     * 优惠劵领取时间
     */
    private String yhjlqsj;
    /**
     * 记录创建时间
     */
    private String jlcjsj;
    /**
     * 优惠劵的过期时间（具体）= 领取时间+有效期天数
     */
    private String jlgqsj;
    /**
     * 满XX可用(分)
     */
    private Integer tjje;

    /**
     * 优惠券url
     * @return
     */
    private String yhjbjturl;
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
    private ParkActivity vo;//对象

    public ParkActivity getVo() {
        return vo;
    }

    public void setVo(ParkActivity vo) {
        this.vo = vo;
    }
    public String getYhjbjturl() {
        return yhjbjturl;
    }

    public void setYhjbjturl(String yhjbjturl) {
        this.yhjbjturl = yhjbjturl;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getYhjslid() {
        return yhjslid;
    }
    public void setYhjslid(String yhjslid) {
        this.yhjslid = yhjslid == null ? null : yhjslid.trim();
    }
    public String getYhjid() {
        return yhjid;
    }
    public void setYhjid(String yhjid) {
        this.yhjid = yhjid == null ? null : yhjid.trim();
    }
    public String getYhjxlh() {
        return yhjxlh;
    }
    public void setYhjxlh(String yhjxlh) {
        this.yhjxlh = yhjxlh == null ? null : yhjxlh.trim();
    }
    public String getUserid() {
        return userid;
    }
    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }
    public String getSfbsy() {
        return sfbsy;
    }
    public void setSfbsy(String sfbsy) {
        this.sfbsy = sfbsy == null ? null : sfbsy.trim();
    }
    public Integer getYhjje() {
        return yhjje;
    }
    public void setYhjje(Integer yhjje) {
        this.yhjje = yhjje;
    }
    public String getYhjyxq() {
        return yhjyxq;
    }
    public void setYhjyxq(String yhjyxq) {
        this.yhjyxq = yhjyxq == null ? null : yhjyxq.trim();
    }
    public String getYhjlqsj() {
        return yhjlqsj;
    }
    public void setYhjlqsj(String yhjlqsj) {
        this.yhjlqsj = yhjlqsj == null ? null : yhjlqsj.trim();
    }
    public String getJlcjsj() {
        return jlcjsj;
    }
    public void setJlcjsj(String jlcjsj) {
        this.jlcjsj = jlcjsj == null ? null : jlcjsj.trim();
    }
    public String getJlgqsj() {
        return jlgqsj;
    }
    public void setJlgqsj(String jlgqsj) {
        this.jlgqsj = jlgqsj == null ? null : jlgqsj.trim();
    }
    public Integer getTjje() {
        return tjje;
    }
    public void setTjje(Integer tjje) {
        this.tjje = tjje;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", yhjslid=").append(yhjslid);
        sb.append(", yhjid=").append(yhjid);
        sb.append(", yhjxlh=").append(yhjxlh);
        sb.append(", userid=").append(userid);
        sb.append(", sfbsy=").append(sfbsy);
        sb.append(", yhjje=").append(yhjje);
        sb.append(", yhjyxq=").append(yhjyxq);
        sb.append(", yhjlqsj=").append(yhjlqsj);
        sb.append(", jlcjsj=").append(jlcjsj);
        sb.append(", jlgqsj=").append(jlgqsj);
        sb.append(", tjje=").append(tjje);
        sb.append("]");
        return sb.toString();
    }
    public class ParkActivity implements Serializable{
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