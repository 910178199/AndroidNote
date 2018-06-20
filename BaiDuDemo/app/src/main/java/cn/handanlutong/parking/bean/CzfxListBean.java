package cn.handanlutong.parking.bean;

import java.util.List;

/**
 * Created by zhangyonggang on 2017/8/25.
 */

public class CzfxListBean {

    /**
     * {"result":[{"czje":3000,"fxje":0,"hdid":"1","id":1,"px":"1","sfsdsy":"0"},{"czje":5000,"fxje":1000,"hdid":"1","id":2,"px":"2","sfsdsy":"0"},{"czje":10000,"fxje":2000,"hdid":"1","id":3,"px":"3","sfsdsy":"0"},{"czje":20000,"fxje":4000,"hdid":"1","id":4,"px":"4","sfsdsy":"0"},{"czje":30000,"fxje":10000,"hdid":"1","id":5,"px":"5","sfsdsy":"0"},{"czje":50000,"fxje":20000,"hdid":"1","id":6,"px":"6","sfsdsy":"0"}],"code":"0000","message":"操作成功"}
     * code : 0000
     * type : 获取充值返现
     */

    private List<DataBean> result;
    private String code;
    private String message;

    public List<DataBean> getResult() {
        return result;
    }

    public void setResult(List<DataBean> result) {
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * czje : 100
         * fxje : 100
         * hdid : 1
         * id : 1
         * px : 1
         * sfsdsy : 0
         */

        private int czje;
        private String fxje;
        private String hdid;
        private int id;
        private String px;
        private String sfsdsy;

        public int getCzje() {
            return czje;
        }

        public void setCzje(int czje) {
            this.czje = czje;
        }

        public String getFxje() {
            return fxje;
        }

        public void setFxje(String fxje) {
            this.fxje = fxje;
        }

        public String getHdid() {
            return hdid;
        }

        public void setHdid(String hdid) {
            this.hdid = hdid;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPx() {
            return px;
        }

        public void setPx(String px) {
            this.px = px;
        }

        public String getSfsdsy() {
            return sfsdsy;
        }

        public void setSfsdsy(String sfsdsy) {
            this.sfsdsy = sfsdsy;
        }
    }
}
