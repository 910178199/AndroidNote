package cn.handanlutong.parking.bean;

import java.util.List;

/**
 * 充值记录列表
 * Created by zhangyonggang on 2017/6/13.
 */

public class RchargeRecordListBean {


    /**
     * result : {"items":[{"ddbh":"CZ_170829110956703","dsfddbh":"4008122001201708298887157895","id":1040,"je":100,"nc":"黄黄","sfsc":"0","sjh":"13681378873","userid":"100029","zffs":"2","zfqd":"2","zfsj":"2017-08-29 11:10:03"},{"ddbh":"ZS_170829111003466","id":1044,"je":500,"sfsc":"0","userid":"100029","zffs":"-1","zfqd":"-2","zfsj":"2017-08-29 11:10:03"},{"ddbh":"ZS_170829101804423","id":1039,"je":500,"sfsc":"0","userid":"100029","zffs":"-1","zfqd":"-2","zfsj":"2017-08-29 10:18:04"},{"ddbh":"CZ_170829100007836","dsfddbh":"2017082921001004910256970867","id":993,"je":100,"nc":"黄黄","sfsc":"0","sjh":"13681378873","userid":"100029","zffs":"2","zfqd":"3","zfsj":"2017-08-29 10:18:04"}],"totalCount":4}
     * code : 0
     * type : 查看我的充值记录
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
        /**
         * items : [{"ddbh":"CZ_170829110956703","dsfddbh":"4008122001201708298887157895","id":1040,"je":100,"nc":"黄黄","sfsc":"0","sjh":"13681378873","userid":"100029","zffs":"2","zfqd":"2","zfsj":"2017-08-29 11:10:03"},{"ddbh":"ZS_170829111003466","id":1044,"je":500,"sfsc":"0","userid":"100029","zffs":"-1","zfqd":"-2","zfsj":"2017-08-29 11:10:03"},{"ddbh":"ZS_170829101804423","id":1039,"je":500,"sfsc":"0","userid":"100029","zffs":"-1","zfqd":"-2","zfsj":"2017-08-29 10:18:04"},{"ddbh":"CZ_170829100007836","dsfddbh":"2017082921001004910256970867","id":993,"je":100,"nc":"黄黄","sfsc":"0","sjh":"13681378873","userid":"100029","zffs":"2","zfqd":"3","zfsj":"2017-08-29 10:18:04"}]
         * totalCount : 4
         */

        private int totalCount;
        private List<ItemsBean> items;

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * ddbh : CZ_170829110956703
             * dsfddbh : 4008122001201708298887157895
             * id : 1040
             * je : 100
             * nc : 黄黄
             * sfsc : 0
             * sjh : 13681378873
             * userid : 100029
             * zffs : 2
             * zfqd : 2
             * zfsj : 2017-08-29 11:10:03
             */

            private String ddbh;
            private String dsfddbh;
            private int id;
            private int je;
            private String nc;
            private String sfsc;
            private String sjh;
            private String userid;
            private String zffs;
            private String zfqd;
            private String zfsj;
            /**
             * 支付类型
             */
            private String zflx;

            public String getZflx() {
                return zflx;
            }

            public void setZflx(String zflx) {
                this.zflx = zflx;
            }

            public String getDdbh() {
                return ddbh;
            }

            public void setDdbh(String ddbh) {
                this.ddbh = ddbh;
            }

            public String getDsfddbh() {
                return dsfddbh;
            }

            public void setDsfddbh(String dsfddbh) {
                this.dsfddbh = dsfddbh;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getJe() {
                return je;
            }

            public void setJe(int je) {
                this.je = je;
            }

            public String getNc() {
                return nc;
            }

            public void setNc(String nc) {
                this.nc = nc;
            }

            public String getSfsc() {
                return sfsc;
            }

            public void setSfsc(String sfsc) {
                this.sfsc = sfsc;
            }

            public String getSjh() {
                return sjh;
            }

            public void setSjh(String sjh) {
                this.sjh = sjh;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public String getZffs() {
                return zffs;
            }

            public void setZffs(String zffs) {
                this.zffs = zffs;
            }

            public String getZfqd() {
                return zfqd;
            }

            public void setZfqd(String zfqd) {
                this.zfqd = zfqd;
            }

            public String getZfsj() {
                return zfsj;
            }

            public void setZfsj(String zfsj) {
                this.zfsj = zfsj;
            }
        }
    }
}
