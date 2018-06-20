package cn.handanlutong.parking.bean;

/**
 * 钱包余额
 * Created by zhangyonggang on 2017/6/13.
 */

public class MyYueBean {


    /**
     * result : {"cjsj":"2017-08-10 14:50:17","czje":0,"id":100029,"userid":"100029","zje":62800,"zsje":62800}
     * code : 0
     * type : 用户余额查询
     */

    private ResultBean result;
    private String code;
    private String type;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
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

    public static class ResultBean {

        private String cjsj;
        private int czje;
        private int id;
        private String userid;
        private int zje;
        private int zsje;

        public String getCjsj() {
            return cjsj;
        }

        public void setCjsj(String cjsj) {
            this.cjsj = cjsj;
        }

        public int getCzje() {
            return czje;
        }

        public void setCzje(int czje) {
            this.czje = czje;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public int getZje() {
            return zje;
        }

        public void setZje(int zje) {
            this.zje = zje;
        }

        public int getZsje() {
            return zsje;
        }

        public void setZsje(int zsje) {
            this.zsje = zsje;
        }
    }
}
