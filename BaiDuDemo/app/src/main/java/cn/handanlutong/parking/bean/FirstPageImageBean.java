package cn.handanlutong.parking.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangyonggang on 2017/8/29.
 */

public class FirstPageImageBean implements Serializable{

    /**
     * xhd : 4
     * result : [{"cjsj":"2017-8-15 13:32:01","hdid":"2","id":1,"lx":"android","lxsfqy":"1","sfglhd":"","sfxs":"1","tpdz":"http://120.77.202.83:6499/TTparkImg/appstyle/android/A/bannerer0-1503631816684.png","tpsx":"1","wzlx":"首页"},{"cjsj":"2017-8-15 13:32:01","hdid":"1","id":22,"lx":"android","lxsfqy":"1","sfxs":"1","tpdz":"http://120.77.202.83:6499/TTparkImg/appstyle/android/A/bannerer1-1503477862620.jpg","tpsx":"2","wzlx":"首页"},{"cjsj":"2017-8-15 13:32:01","hdid":"21","id":23,"lx":"android","lxsfqy":"1","sfxs":"1","tpdz":"http://120.77.202.83:6499/TTparkImg/appstyle/android/A/bannerer0-1503567486260.png","tpsx":"3","wzlx":"首页"}]
     * zje : 56305
     * code : 0000
     * type : 获取App样式
     */

    private int xhd;
    private int zje;
    private String code;
    private String type;
    private List<ResultBean> result;

    public int getXhd() {
        return xhd;
    }

    public void setXhd(int xhd) {
        this.xhd = xhd;
    }

    public int getZje() {
        return zje;
    }

    public void setZje(int zje) {
        this.zje = zje;
    }

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
         * 图片地址
         */
        private String tpdz;
        /**
         * 图片顺序
         */
        private String tpsx;
        /**
         * 活动内容
         */
        private String nr;
        private String hdid;
        private String sfgq;

        public String getSfgq() {
            return sfgq;
        }

        public void setSfgq(String sfgq) {
            this.sfgq = sfgq;
        }

        public String getHdid() {
            return hdid;
        }

        public void setHdid(String hdid) {
            this.hdid = hdid;
        }

        public String getTpdz() {
            return tpdz;
        }
        public void setTpdz(String tpdz) {
            this.tpdz = tpdz;
        }
        public String getTpsx() {
            return tpsx;
        }
        public void setTpsx(String tpsx) {
            this.tpsx = tpsx;
        }
        public String getNr() {
            return nr;
        }
        public void setNr(String nr) {
            this.nr = nr;
        }
    }
}
