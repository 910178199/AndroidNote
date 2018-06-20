/*
 * MessageUserInfo.java
 * Copyright(C) 20xx-2015 xxxxxx公司
 * All rights reserved.
 * -----------------------------------------------
 * 2018-01-09 Created
 */
package cn.handanlutong.parking.bean;

/**
 * null
 * 
 * @author 
 * @version 1.0 2018-01-09
 */
public class MessageUserInfo {

    /**
     * 记录ID
     */
    private Long id;
    /**
     * 接收人
     */
    private Long userId;
    /**
     * 标题
     */
    private String bt;
    /**
     * 内容
     */
    private String nr;
    /**
     * 内容类型
     */
    private String nrlx;
    /**
     * 图片
     */
    private String tpdz;
    /**
     * 推送时间
     */
    private String tssj;
    /**
     * 是否显示
     */
    private String sfxs;
    /**
     * 是否被查看过
     */
    private String sfbckg;
    /**
     * 创建时间
     */
    private String cjsj;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getBt() {
        return bt;
    }
    public void setBt(String bt) {
        this.bt = bt == null ? null : bt.trim();
    }
    public String getNr() {
        return nr;
    }
    public void setNr(String nr) {
        this.nr = nr == null ? null : nr.trim();
    }
    public String getNrlx() {
        return nrlx;
    }
    public void setNrlx(String nrlx) {
        this.nrlx = nrlx == null ? null : nrlx.trim();
    }
    public String getTpdz() {
        return tpdz;
    }
    public void setTpdz(String tpdz) {
        this.tpdz = tpdz == null ? null : tpdz.trim();
    }
    public String getTssj() {
        return tssj;
    }
    public void setTssj(String tssj) {
        this.tssj = tssj == null ? null : tssj.trim();
    }
    public String getSfxs() {
        return sfxs;
    }
    public void setSfxs(String sfxs) {
        this.sfxs = sfxs == null ? null : sfxs.trim();
    }
    public String getSfbckg() {
        return sfbckg;
    }
    public void setSfbckg(String sfbckg) {
        this.sfbckg = sfbckg == null ? null : sfbckg.trim();
    }
    public String getCjsj() {
        return cjsj;
    }
    public void setCjsj(String cjsj) {
        this.cjsj = cjsj == null ? null : cjsj.trim();
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", bt=").append(bt);
        sb.append(", nr=").append(nr);
        sb.append(", nrlx=").append(nrlx);
        sb.append(", tpdz=").append(tpdz);
        sb.append(", tssj=").append(tssj);
        sb.append(", sfxs=").append(sfxs);
        sb.append(", sfbckg=").append(sfbckg);
        sb.append(", cjsj=").append(cjsj);
        sb.append("]");
        return sb.toString();
    }
}