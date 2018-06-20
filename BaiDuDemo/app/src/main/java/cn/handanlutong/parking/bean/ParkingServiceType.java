/*
 * ParkingServiceType.java
 * Copyright(C) 20xx-2015 xxxxxx鍏徃
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
public class ParkingServiceType {

    /**
     * 记录ID
     */
    private Long id;
    /**
     * 类型描述
     */
    private String lxms;
    /**
     * 排序
     */
    private String px;
    /**
     * 反馈数量统计
     */
    private String sltj;
    /**
     * 创建时间
     */
    private String cjsj;
    /**
     * 是否删除
     */
    private String sfsc;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getLxms() {
        return lxms;
    }
    public void setLxms(String lxms) {
        this.lxms = lxms == null ? null : lxms.trim();
    }
    public String getPx() {
        return px;
    }
    public void setPx(String px) {
        this.px = px == null ? null : px.trim();
    }
    public String getSltj() {
        return sltj;
    }
    public void setSltj(String sltj) {
        this.sltj = sltj == null ? null : sltj.trim();
    }
    public String getCjsj() {
        return cjsj;
    }
    public void setCjsj(String cjsj) {
        this.cjsj = cjsj == null ? null : cjsj.trim();
    }
    public String getSfsc() {
        return sfsc;
    }
    public void setSfsc(String sfsc) {
        this.sfsc = sfsc == null ? null : sfsc.trim();
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", lxms=").append(lxms);
        sb.append(", px=").append(px);
        sb.append(", sltj=").append(sltj);
        sb.append(", cjsj=").append(cjsj);
        sb.append(", sfsc=").append(sfsc);
        sb.append("]");
        return sb.toString();
    }
}