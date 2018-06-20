package cn.handanlutong.parking.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 我的车辆列表
 * Created by zhangyonggang on 2017/4/12.
 */

public class MyCarBean implements Serializable{


    /**
     * result : [{"clzt":"0","hphm":"冀D11111","id":5008,"lrrq":"2017-08-10 17:04:00","zdzf":"1"},{"clzt":"0","hphm":"京D33333","id":5009,"lrrq":"2017-08-10 17:17:28","zdzf":"1"}]
     * code : 0
     * type : 用户查询绑定车辆
     */

    private String code;
    private String type;
    private List<ResultBean> result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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

    public static class ResultBean implements Serializable{
        /**
         * clzt : 0
         * hphm : 冀D11111
         * id : 5008
         * lrrq : 2017-08-10 17:04:00
         * zdzf : 1
         */

        private String clzt;
        private String hphm;
        private Long id;
        private String lrrq;
        private String zdzf;
        private String bdzt; //2 已认证 0 未认证 1审核中
        private String type; // 1审核中  2找回审核中
        private String cpys;//车牌颜色
        private int syts;//月卡剩余天数
        /**
         * 号牌状态 0非占用 1占用
         */
        private String hpzt;
        /**
         * 包期状态 0免费 1收费 2包期 3 包期过期 4包期禁用
         */
        private String bqzt;
        /**
         * 是否可以续费 0否 1是
         */
        private int sfxf;


        public int getSfxf() {
            return sfxf;
        }

        public void setSfxf(int sfxf) {
            this.sfxf = sfxf;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getBqzt() {
            return bqzt;
        }

        public void setBqzt(String bqzt) {
            this.bqzt = bqzt;
        }

        public String getHpzt() {
            return hpzt;
        }

        public void setHpzt(String hpzt) {
            this.hpzt = hpzt;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getBdzt() {
            return bdzt;
        }

        public void setBdzt(String bdzt) {
            this.bdzt = bdzt;
        }

        public String getClzt() {
            return clzt;
        }

        public void setClzt(String clzt) {
            this.clzt = clzt;
        }

        public String getHphm() {
            return hphm;
        }

        public void setHphm(String hphm) {
            this.hphm = hphm;
        }

        public String getLrrq() {
            return lrrq;
        }

        public void setLrrq(String lrrq) {
            this.lrrq = lrrq;
        }

        public String getZdzf() {
            return zdzf;
        }

        public void setZdzf(String zdzf) {
            this.zdzf = zdzf;
        }

        public String getCpys() {
            return cpys;
        }

        public void setCpys(String cpys) {
            this.cpys = cpys;
        }

        public int getSyts() {
            return syts;
        }

        public void setSyts(int syts) {
            this.syts = syts;
        }
    }
}
