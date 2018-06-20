package cn.handanlutong.parking.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ww on 2017/4/25.
 */

public class GarageBean {

    /**
     * result : [{"ccbh":"00001","ccdz":"西三旗生态园","cclb":"1","ccmc":"西三旗生态园","ccms":"","ccqc":"海东升","cczbX":"116.346791","cczbY":"40.053445","cczt":"0","cjsj":"2017-04-01 10:57:56","djlx":"29","id":28,"jssj":"2017-04-29","kbwsl":"4","kssj":"2017-04-04","sfsc":"0","ssqy":"北京市-市辖区-海淀区","xzqh":"110108","zbwsl":"4"},{"ccbh":"801","ccdz":"西小口路","cclb":"1","ccmc":"精英智通西三旗办公区停车场","ccms":"","ccqc":"西三旗办公区停车场","cczbX":"116.345647","cczbY":"40.053241","cczt":"2","cjsj":"2017-04-05 15:20:20","djlx":"31","id":30,"jssj":"","kbwsl":"17","kssj":"","sfsc":"0","ssqy":"北京市-市辖区-海淀区","xzqh":"110108","zbwsl":"17"},{"ccbh":"0001","ccdz":"悦秀路","cclb":"1","ccmc":"物美停车场","ccms":"","ccqc":"物美","cczbX":"116.343611","cczbY":"40.053197","cczt":"0","cjsj":"2017-04-07 20:30:52","djlx":"28","id":32,"jssj":"","kbwsl":"4","kssj":"","sfsc":"0","ssqy":"北京市-市辖区-海淀区","xzqh":"110108","zbwsl":"4"},{"ccbh":"00009","ccdz":"西三旗生态园","cclb":"1","ccmc":"海升停车场测试点","ccms":"","ccqc":"海升","cczbX":"116.346791","cczbY":"40.053445","cczt":"0","cjsj":"2017-04-17 10:12:12","djlx":"26","id":42,"jssj":"","kbwsl":"2","kssj":"","sfsc":"0","ssqy":"北京市-市辖区-海淀区","xzqh":"110108","zbwsl":"3"}]
     * code : 0
     * type : 查询范围内停车场
     */

    private int code;
    private String type;
    private List<ResultBean> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable {
        /**
         * ccbh : 00001
         * ccdz : 西三旗生态园
         * cclb : 1
         * ccmc : 西三旗生态园
         * ccms :
         * ccqc : 海东升
         * cczbX : 116.346791
         * cczbY : 40.053445
         * cczt : 0
         * cjsj : 2017-04-01 10:57:56
         * djlx : 29
         * id : 28
         * jssj : 2017-04-29
         * kbwsl : 4
         * kssj : 2017-04-04
         * sfsc : 0
         * ssqy : 北京市-市辖区-海淀区
         * xzqh : 110108
         * zbwsl : 4
         */

        private String ccbh;//车场编号
        private String ccdz;//车场地址
        private String cclb;//车场类别
        private String ccmc;//车场名称
        private String ccms;//车场描述
        private String ccqc;//车场全称
        private String cczbX;// 坐标纬度
        private String cczbY;// 坐标经度
        private String cczt;//车场状态
        private String cjsj;//创建时间
        private String djlx;//定价类型
        private int id;
        private String jssj;//结束时间
        private String kbwsl;//空泊位数
        private String kssj;//开始时间
        private String sfsc;//
        private String ssqy;//所属区域
        private String xzqh;//行政区划
        private String zbwsl;//总泊位数
        private String ccjl;//车场距离
        private String cctp;//车场图片
        private String totalCount;//附近车场总数

        public String getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(String totalCount) {
            this.totalCount = totalCount;
        }

        public String getCctp() {
            return cctp;
        }

        public void setCctp(String cctp) {
            this.cctp = cctp;
        }

        public String getCcjl() {
            return ccjl;
        }

        public void setCcjl(String ccjl) {
            this.ccjl = ccjl;
        }

        public String getCcbh() {
            return ccbh;
        }

        public void setCcbh(String ccbh) {
            this.ccbh = ccbh;
        }

        public String getCcdz() {
            return ccdz;
        }

        public void setCcdz(String ccdz) {
            this.ccdz = ccdz;
        }

        public String getCclb() {
            return cclb;
        }

        public void setCclb(String cclb) {
            this.cclb = cclb;
        }

        public String getCcmc() {
            return ccmc;
        }

        public void setCcmc(String ccmc) {
            this.ccmc = ccmc;
        }

        public String getCcms() {
            return ccms;
        }

        public void setCcms(String ccms) {
            this.ccms = ccms;
        }

        public String getCcqc() {
            return ccqc;
        }

        public void setCcqc(String ccqc) {
            this.ccqc = ccqc;
        }

        public String getCczbX() {
            return cczbX;
        }

        public void setCczbX(String cczbX) {
            this.cczbX = cczbX;
        }

        public String getCczbY() {
            return cczbY;
        }

        public void setCczbY(String cczbY) {
            this.cczbY = cczbY;
        }

        public String getCczt() {
            return cczt;
        }

        public void setCczt(String cczt) {
            this.cczt = cczt;
        }

        public String getCjsj() {
            return cjsj;
        }

        public void setCjsj(String cjsj) {
            this.cjsj = cjsj;
        }

        public String getDjlx() {
            return djlx;
        }

        public void setDjlx(String djlx) {
            this.djlx = djlx;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getJssj() {
            return jssj;
        }

        public void setJssj(String jssj) {
            this.jssj = jssj;
        }

        public String getKbwsl() {
            return kbwsl;
        }

        public void setKbwsl(String kbwsl) {
            this.kbwsl = kbwsl;
        }

        public String getKssj() {
            return kssj;
        }

        public void setKssj(String kssj) {
            this.kssj = kssj;
        }

        public String getSfsc() {
            return sfsc;
        }

        public void setSfsc(String sfsc) {
            this.sfsc = sfsc;
        }

        public String getSsqy() {
            return ssqy;
        }

        public void setSsqy(String ssqy) {
            this.ssqy = ssqy;
        }

        public String getXzqh() {
            return xzqh;
        }

        public void setXzqh(String xzqh) {
            this.xzqh = xzqh;
        }

        public String getZbwsl() {
            return zbwsl;
        }

        public void setZbwsl(String zbwsl) {
            this.zbwsl = zbwsl;
        }
    }
}
