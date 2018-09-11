package com.tools.apps.wallpaper.bean;

import java.util.List;

public class TestNewsBean {

    private List<TechBean> tech;
    private List<AutoBean> auto;
    private List<MoneyBean> money;
    private List<SportsBean> sports;
    private List<DyBean> dy;
    private List<WarBean> war;
    private List<EntBean> ent;
    private List<ToutiaoBean> toutiao;

    public List<TechBean> getTech() {
        return tech;
    }

    public void setTech(List<TechBean> tech) {
        this.tech = tech;
    }

    public List<AutoBean> getAuto() {
        return auto;
    }

    public void setAuto(List<AutoBean> auto) {
        this.auto = auto;
    }

    public List<MoneyBean> getMoney() {
        return money;
    }

    public void setMoney(List<MoneyBean> money) {
        this.money = money;
    }

    public List<SportsBean> getSports() {
        return sports;
    }

    public void setSports(List<SportsBean> sports) {
        this.sports = sports;
    }

    public List<DyBean> getDy() {
        return dy;
    }

    public void setDy(List<DyBean> dy) {
        this.dy = dy;
    }

    public List<WarBean> getWar() {
        return war;
    }

    public void setWar(List<WarBean> war) {
        this.war = war;
    }

    public List<EntBean> getEnt() {
        return ent;
    }

    public void setEnt(List<EntBean> ent) {
        this.ent = ent;
    }

    public List<ToutiaoBean> getToutiao() {
        return toutiao;
    }

    public void setToutiao(List<ToutiaoBean> toutiao) {
        this.toutiao = toutiao;
    }

    public static class TechBean {
        /**
         * liveInfo : null
         * tcount : 87
         * picInfo : [{"ref":null,"width":null,"url":"http://cms-bucket.nosdn.127.net/2018/09/11/11c78aebbf6d4d31a5cbd647854f7ee3.png","height":null}]
         * docid : DRD26M2G00097U7R
         * videoInfo : null
         * channel : tech
         * link : https://3g.163.com/all/article/DRD26M2G00097U7R.html
         * source : 网易科技报道
         * title : 无人商店走上香港街头  消费者却褒贬不一
         * type : doc
         * imgsrcFrom : null
         * imgsrc3gtype : 1
         * unlikeReason : 重复、旧闻/6,内容质量差/6
         * digest : 网易科技讯9月11日消息，据国外媒体报道，在“智慧城市”计划
         * typeid :
         * addata : null
         * tag :
         * category : 科技
         * ptime : 2018-09-11 03:02:32
         */

        private Object liveInfo;
        private int tcount;
        private String docid;
        private Object videoInfo;
        private String channel;
        private String link;
        private String source;
        private String title;
        private String type;
        private Object imgsrcFrom;
        private int imgsrc3gtype;
        private String unlikeReason;
        private String digest;
        private String typeid;
        private Object addata;
        private String tag;
        private String category;
        private String ptime;
        private List<PicInfoBean> picInfo;

        public Object getLiveInfo() {
            return liveInfo;
        }

        public void setLiveInfo(Object liveInfo) {
            this.liveInfo = liveInfo;
        }

        public int getTcount() {
            return tcount;
        }

        public void setTcount(int tcount) {
            this.tcount = tcount;
        }

        public String getDocid() {
            return docid;
        }

        public void setDocid(String docid) {
            this.docid = docid;
        }

        public Object getVideoInfo() {
            return videoInfo;
        }

        public void setVideoInfo(Object videoInfo) {
            this.videoInfo = videoInfo;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getImgsrcFrom() {
            return imgsrcFrom;
        }

        public void setImgsrcFrom(Object imgsrcFrom) {
            this.imgsrcFrom = imgsrcFrom;
        }

        public int getImgsrc3gtype() {
            return imgsrc3gtype;
        }

        public void setImgsrc3gtype(int imgsrc3gtype) {
            this.imgsrc3gtype = imgsrc3gtype;
        }

        public String getUnlikeReason() {
            return unlikeReason;
        }

        public void setUnlikeReason(String unlikeReason) {
            this.unlikeReason = unlikeReason;
        }

        public String getDigest() {
            return digest;
        }

        public void setDigest(String digest) {
            this.digest = digest;
        }

        public String getTypeid() {
            return typeid;
        }

        public void setTypeid(String typeid) {
            this.typeid = typeid;
        }

        public Object getAddata() {
            return addata;
        }

        public void setAddata(Object addata) {
            this.addata = addata;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getPtime() {
            return ptime;
        }

        public void setPtime(String ptime) {
            this.ptime = ptime;
        }

        public List<PicInfoBean> getPicInfo() {
            return picInfo;
        }

        public void setPicInfo(List<PicInfoBean> picInfo) {
            this.picInfo = picInfo;
        }

        public static class PicInfoBean {
            /**
             * ref : null
             * width : null
             * url : http://cms-bucket.nosdn.127.net/2018/09/11/11c78aebbf6d4d31a5cbd647854f7ee3.png
             * height : null
             */

            private Object ref;
            private Object width;
            private String url;
            private Object height;

            public Object getRef() {
                return ref;
            }

            public void setRef(Object ref) {
                this.ref = ref;
            }

            public Object getWidth() {
                return width;
            }

            public void setWidth(Object width) {
                this.width = width;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public Object getHeight() {
                return height;
            }

            public void setHeight(Object height) {
                this.height = height;
            }
        }
    }

    public static class AutoBean {
        /**
         * liveInfo : null
         * tcount : 318
         * picInfo : [{"ref":null,"width":null,"url":"http://cms-bucket.nosdn.127.net/2018/09/11/7f5d1b60a5374b07a77fb9a0001a165b.jpeg","height":null}]
         * docid : DRDGVBTE0008856V
         * videoInfo : null
         * channel : auto
         * link : https://3g.163.com/all/article/DRDGVBTE0008856V.html
         * source : 网易汽车
         * title : 尺寸空间碾压同级 试驾比亚迪最美轿车
         * type : doc
         * imgsrcFrom : null
         * imgsrc3gtype : 1
         * unlikeReason : 重复、旧闻/6,内容质量差/6
         * digest : 沉寂许久的比亚迪似乎在近两年重新找回了状态，因为陆续推出的比
         * typeid :
         * addata : null
         * tag :
         * category : 汽车
         * ptime : 2018-09-11 07:20:40
         */

        private Object liveInfo;
        private int tcount;
        private String docid;
        private Object videoInfo;
        private String channel;
        private String link;
        private String source;
        private String title;
        private String type;
        private Object imgsrcFrom;
        private int imgsrc3gtype;
        private String unlikeReason;
        private String digest;
        private String typeid;
        private Object addata;
        private String tag;
        private String category;
        private String ptime;
        private List<PicInfoBeanX> picInfo;

        public Object getLiveInfo() {
            return liveInfo;
        }

        public void setLiveInfo(Object liveInfo) {
            this.liveInfo = liveInfo;
        }

        public int getTcount() {
            return tcount;
        }

        public void setTcount(int tcount) {
            this.tcount = tcount;
        }

        public String getDocid() {
            return docid;
        }

        public void setDocid(String docid) {
            this.docid = docid;
        }

        public Object getVideoInfo() {
            return videoInfo;
        }

        public void setVideoInfo(Object videoInfo) {
            this.videoInfo = videoInfo;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getImgsrcFrom() {
            return imgsrcFrom;
        }

        public void setImgsrcFrom(Object imgsrcFrom) {
            this.imgsrcFrom = imgsrcFrom;
        }

        public int getImgsrc3gtype() {
            return imgsrc3gtype;
        }

        public void setImgsrc3gtype(int imgsrc3gtype) {
            this.imgsrc3gtype = imgsrc3gtype;
        }

        public String getUnlikeReason() {
            return unlikeReason;
        }

        public void setUnlikeReason(String unlikeReason) {
            this.unlikeReason = unlikeReason;
        }

        public String getDigest() {
            return digest;
        }

        public void setDigest(String digest) {
            this.digest = digest;
        }

        public String getTypeid() {
            return typeid;
        }

        public void setTypeid(String typeid) {
            this.typeid = typeid;
        }

        public Object getAddata() {
            return addata;
        }

        public void setAddata(Object addata) {
            this.addata = addata;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getPtime() {
            return ptime;
        }

        public void setPtime(String ptime) {
            this.ptime = ptime;
        }

        public List<PicInfoBeanX> getPicInfo() {
            return picInfo;
        }

        public void setPicInfo(List<PicInfoBeanX> picInfo) {
            this.picInfo = picInfo;
        }

        public static class PicInfoBeanX {
            /**
             * ref : null
             * width : null
             * url : http://cms-bucket.nosdn.127.net/2018/09/11/7f5d1b60a5374b07a77fb9a0001a165b.jpeg
             * height : null
             */

            private Object ref;
            private Object width;
            private String url;
            private Object height;

            public Object getRef() {
                return ref;
            }

            public void setRef(Object ref) {
                this.ref = ref;
            }

            public Object getWidth() {
                return width;
            }

            public void setWidth(Object width) {
                this.width = width;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public Object getHeight() {
                return height;
            }

            public void setHeight(Object height) {
                this.height = height;
            }
        }
    }

    public static class MoneyBean {
        /**
         * liveInfo : null
         * tcount : 0
         * picInfo : [{"ref":null,"width":null,"url":"http://cms-bucket.nosdn.127.net/2018/09/11/f4242138842e43898527716ac18634fd.jpeg","height":null}]
         * docid : DRDOLQ44bjwangxiaowu
         * videoInfo : null
         * channel : money
         * link : https://3g.163.com/all/special/S1536582508220.html
         * source : 网易商业
         * title : 2018天津夏季达沃斯论坛将于9月18日开启
         * type : special
         * imgsrcFrom : null
         * imgsrc3gtype : 3
         * unlikeReason : 重复、旧闻/6,内容质量差/6
         * digest :
         * typeid : S1536582508220
         * addata : null
         * tag : 专题
         * category : 推荐
         * ptime : 2018-09-11 09:35:16
         */

        private Object liveInfo;
        private int tcount;
        private String docid;
        private Object videoInfo;
        private String channel;
        private String link;
        private String source;
        private String title;
        private String type;
        private Object imgsrcFrom;
        private int imgsrc3gtype;
        private String unlikeReason;
        private String digest;
        private String typeid;
        private Object addata;
        private String tag;
        private String category;
        private String ptime;
        private List<PicInfoBeanXX> picInfo;

        public Object getLiveInfo() {
            return liveInfo;
        }

        public void setLiveInfo(Object liveInfo) {
            this.liveInfo = liveInfo;
        }

        public int getTcount() {
            return tcount;
        }

        public void setTcount(int tcount) {
            this.tcount = tcount;
        }

        public String getDocid() {
            return docid;
        }

        public void setDocid(String docid) {
            this.docid = docid;
        }

        public Object getVideoInfo() {
            return videoInfo;
        }

        public void setVideoInfo(Object videoInfo) {
            this.videoInfo = videoInfo;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getImgsrcFrom() {
            return imgsrcFrom;
        }

        public void setImgsrcFrom(Object imgsrcFrom) {
            this.imgsrcFrom = imgsrcFrom;
        }

        public int getImgsrc3gtype() {
            return imgsrc3gtype;
        }

        public void setImgsrc3gtype(int imgsrc3gtype) {
            this.imgsrc3gtype = imgsrc3gtype;
        }

        public String getUnlikeReason() {
            return unlikeReason;
        }

        public void setUnlikeReason(String unlikeReason) {
            this.unlikeReason = unlikeReason;
        }

        public String getDigest() {
            return digest;
        }

        public void setDigest(String digest) {
            this.digest = digest;
        }

        public String getTypeid() {
            return typeid;
        }

        public void setTypeid(String typeid) {
            this.typeid = typeid;
        }

        public Object getAddata() {
            return addata;
        }

        public void setAddata(Object addata) {
            this.addata = addata;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getPtime() {
            return ptime;
        }

        public void setPtime(String ptime) {
            this.ptime = ptime;
        }

        public List<PicInfoBeanXX> getPicInfo() {
            return picInfo;
        }

        public void setPicInfo(List<PicInfoBeanXX> picInfo) {
            this.picInfo = picInfo;
        }

        public static class PicInfoBeanXX {
            /**
             * ref : null
             * width : null
             * url : http://cms-bucket.nosdn.127.net/2018/09/11/f4242138842e43898527716ac18634fd.jpeg
             * height : null
             */

            private Object ref;
            private Object width;
            private String url;
            private Object height;

            public Object getRef() {
                return ref;
            }

            public void setRef(Object ref) {
                this.ref = ref;
            }

            public Object getWidth() {
                return width;
            }

            public void setWidth(Object width) {
                this.width = width;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public Object getHeight() {
                return height;
            }

            public void setHeight(Object height) {
                this.height = height;
            }
        }
    }

    public static class SportsBean {
        /**
         * liveInfo : null
         * tcount : 1498
         * picInfo : [{"ref":null,"width":null,"url":"http://cms-bucket.nosdn.127.net/2018/09/11/07ea59125acf43cbb8e8cafed5332664.png","height":null},{"ref":null,"width":null,"url":"http://cms-bucket.nosdn.127.net/2018/09/11/719b8b30ee7d4ccab3e214602d9bdc28.png","height":null},{"ref":null,"width":null,"url":"http://cms-bucket.nosdn.127.net/2018/09/11/557d73d686604e4bb3eb0f20f56cd7d7.png","height":null}]
         * docid : DRDJ10L6bzheng
         * videoInfo : null
         * channel : sports
         * link : https://3g.163.com/all/photoview/0005/163683.html
         * source : 网易体育
         * title : 尽力了!武磊两中框 难胜"三流"巴林
         * type : photoset
         * imgsrcFrom : null
         * imgsrc3gtype : 2
         * unlikeReason : 重复、旧闻/6,内容质量差/6
         * digest :
         * typeid : 0B4C0005|163683
         * addata : null
         * tag : 图集
         * category : 推荐
         * ptime : 2018-09-11 07:56:32
         */

        private Object liveInfo;
        private int tcount;
        private String docid;
        private Object videoInfo;
        private String channel;
        private String link;
        private String source;
        private String title;
        private String type;
        private Object imgsrcFrom;
        private int imgsrc3gtype;
        private String unlikeReason;
        private String digest;
        private String typeid;
        private Object addata;
        private String tag;
        private String category;
        private String ptime;
        private List<PicInfoBeanXXX> picInfo;

        public Object getLiveInfo() {
            return liveInfo;
        }

        public void setLiveInfo(Object liveInfo) {
            this.liveInfo = liveInfo;
        }

        public int getTcount() {
            return tcount;
        }

        public void setTcount(int tcount) {
            this.tcount = tcount;
        }

        public String getDocid() {
            return docid;
        }

        public void setDocid(String docid) {
            this.docid = docid;
        }

        public Object getVideoInfo() {
            return videoInfo;
        }

        public void setVideoInfo(Object videoInfo) {
            this.videoInfo = videoInfo;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getImgsrcFrom() {
            return imgsrcFrom;
        }

        public void setImgsrcFrom(Object imgsrcFrom) {
            this.imgsrcFrom = imgsrcFrom;
        }

        public int getImgsrc3gtype() {
            return imgsrc3gtype;
        }

        public void setImgsrc3gtype(int imgsrc3gtype) {
            this.imgsrc3gtype = imgsrc3gtype;
        }

        public String getUnlikeReason() {
            return unlikeReason;
        }

        public void setUnlikeReason(String unlikeReason) {
            this.unlikeReason = unlikeReason;
        }

        public String getDigest() {
            return digest;
        }

        public void setDigest(String digest) {
            this.digest = digest;
        }

        public String getTypeid() {
            return typeid;
        }

        public void setTypeid(String typeid) {
            this.typeid = typeid;
        }

        public Object getAddata() {
            return addata;
        }

        public void setAddata(Object addata) {
            this.addata = addata;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getPtime() {
            return ptime;
        }

        public void setPtime(String ptime) {
            this.ptime = ptime;
        }

        public List<PicInfoBeanXXX> getPicInfo() {
            return picInfo;
        }

        public void setPicInfo(List<PicInfoBeanXXX> picInfo) {
            this.picInfo = picInfo;
        }

        public static class PicInfoBeanXXX {
            /**
             * ref : null
             * width : null
             * url : http://cms-bucket.nosdn.127.net/2018/09/11/07ea59125acf43cbb8e8cafed5332664.png
             * height : null
             */

            private Object ref;
            private Object width;
            private String url;
            private Object height;

            public Object getRef() {
                return ref;
            }

            public void setRef(Object ref) {
                this.ref = ref;
            }

            public Object getWidth() {
                return width;
            }

            public void setWidth(Object width) {
                this.width = width;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public Object getHeight() {
                return height;
            }

            public void setHeight(Object height) {
                this.height = height;
            }
        }
    }

    public static class DyBean {
        /**
         * liveInfo : null
         * tcount : 107
         * picInfo : [{"ref":null,"width":null,"url":"http://cms-bucket.nosdn.127.net/2018/09/10/8e9a6e75deed4263ae28e081ed94d0b4.png","height":null},{"ref":null,"width":null,"url":"http://cms-bucket.nosdn.127.net/2018/09/10/7fbe074f1b6d46ad8ba0a5ffd50bf918.png","height":null},{"ref":null,"width":null,"url":"http://cms-bucket.nosdn.127.net/2018/09/10/d23ab6b8c2b94740bb29f139923fe14f.png","height":null}]
         * docid : DRBN7BLR0514CKB6
         * videoInfo : null
         * channel : dy
         * link : https://3g.163.com/all/article/DRBN7BLR0514CKB6.html
         * source : 丁香医生
         * title : 脖子僵、肩膀痛怎么办？4 个动作就能缓解
         * type : doc
         * imgsrcFrom : null
         * imgsrc3gtype : 2
         * unlikeReason : 重复、旧闻/6,内容质量差/6
         * digest : 查看大图但族人们有个先天缺陷，因为稍微不留神坏姿势就集于一身
         * typeid :
         * addata : null
         * tag :
         * category : 推荐
         * ptime : 2018-09-10 14:32:11
         */

        private Object liveInfo;
        private int tcount;
        private String docid;
        private Object videoInfo;
        private String channel;
        private String link;
        private String source;
        private String title;
        private String type;
        private Object imgsrcFrom;
        private int imgsrc3gtype;
        private String unlikeReason;
        private String digest;
        private String typeid;
        private Object addata;
        private String tag;
        private String category;
        private String ptime;
        private List<PicInfoBeanXXXX> picInfo;

        public Object getLiveInfo() {
            return liveInfo;
        }

        public void setLiveInfo(Object liveInfo) {
            this.liveInfo = liveInfo;
        }

        public int getTcount() {
            return tcount;
        }

        public void setTcount(int tcount) {
            this.tcount = tcount;
        }

        public String getDocid() {
            return docid;
        }

        public void setDocid(String docid) {
            this.docid = docid;
        }

        public Object getVideoInfo() {
            return videoInfo;
        }

        public void setVideoInfo(Object videoInfo) {
            this.videoInfo = videoInfo;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getImgsrcFrom() {
            return imgsrcFrom;
        }

        public void setImgsrcFrom(Object imgsrcFrom) {
            this.imgsrcFrom = imgsrcFrom;
        }

        public int getImgsrc3gtype() {
            return imgsrc3gtype;
        }

        public void setImgsrc3gtype(int imgsrc3gtype) {
            this.imgsrc3gtype = imgsrc3gtype;
        }

        public String getUnlikeReason() {
            return unlikeReason;
        }

        public void setUnlikeReason(String unlikeReason) {
            this.unlikeReason = unlikeReason;
        }

        public String getDigest() {
            return digest;
        }

        public void setDigest(String digest) {
            this.digest = digest;
        }

        public String getTypeid() {
            return typeid;
        }

        public void setTypeid(String typeid) {
            this.typeid = typeid;
        }

        public Object getAddata() {
            return addata;
        }

        public void setAddata(Object addata) {
            this.addata = addata;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getPtime() {
            return ptime;
        }

        public void setPtime(String ptime) {
            this.ptime = ptime;
        }

        public List<PicInfoBeanXXXX> getPicInfo() {
            return picInfo;
        }

        public void setPicInfo(List<PicInfoBeanXXXX> picInfo) {
            this.picInfo = picInfo;
        }

        public static class PicInfoBeanXXXX {
            /**
             * ref : null
             * width : null
             * url : http://cms-bucket.nosdn.127.net/2018/09/10/8e9a6e75deed4263ae28e081ed94d0b4.png
             * height : null
             */

            private Object ref;
            private Object width;
            private String url;
            private Object height;

            public Object getRef() {
                return ref;
            }

            public void setRef(Object ref) {
                this.ref = ref;
            }

            public Object getWidth() {
                return width;
            }

            public void setWidth(Object width) {
                this.width = width;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public Object getHeight() {
                return height;
            }

            public void setHeight(Object height) {
                this.height = height;
            }
        }
    }

    public static class WarBean {
        /**
         * liveInfo : null
         * tcount : 0
         * picInfo : [{"ref":null,"width":null,"url":"http://cms-bucket.nosdn.127.net/2018/09/10/c63866bc3e72439e988dcae05637a2e3.jpeg","height":null}]
         * docid : 0001set2296239
         * videoInfo : null
         * channel : war
         * link : https://3g.163.com/all/photoview/0001/2296239.html
         * source : 环球网
         * title : 朝鲜国庆70周年阅兵未出现洲际导弹
         * type : photoset
         * imgsrcFrom : null
         * imgsrc3gtype : 1
         * unlikeReason : 重复、旧闻/6,内容质量差/6
         * digest :
         * typeid : 4T8E0001|2296239
         * addata : null
         * tag : 图集
         * category : 推荐
         * ptime : 2018-09-10 17:53:37
         */

        private Object liveInfo;
        private int tcount;
        private String docid;
        private Object videoInfo;
        private String channel;
        private String link;
        private String source;
        private String title;
        private String type;
        private Object imgsrcFrom;
        private int imgsrc3gtype;
        private String unlikeReason;
        private String digest;
        private String typeid;
        private Object addata;
        private String tag;
        private String category;
        private String ptime;
        private List<PicInfoBeanXXXXX> picInfo;

        public Object getLiveInfo() {
            return liveInfo;
        }

        public void setLiveInfo(Object liveInfo) {
            this.liveInfo = liveInfo;
        }

        public int getTcount() {
            return tcount;
        }

        public void setTcount(int tcount) {
            this.tcount = tcount;
        }

        public String getDocid() {
            return docid;
        }

        public void setDocid(String docid) {
            this.docid = docid;
        }

        public Object getVideoInfo() {
            return videoInfo;
        }

        public void setVideoInfo(Object videoInfo) {
            this.videoInfo = videoInfo;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getImgsrcFrom() {
            return imgsrcFrom;
        }

        public void setImgsrcFrom(Object imgsrcFrom) {
            this.imgsrcFrom = imgsrcFrom;
        }

        public int getImgsrc3gtype() {
            return imgsrc3gtype;
        }

        public void setImgsrc3gtype(int imgsrc3gtype) {
            this.imgsrc3gtype = imgsrc3gtype;
        }

        public String getUnlikeReason() {
            return unlikeReason;
        }

        public void setUnlikeReason(String unlikeReason) {
            this.unlikeReason = unlikeReason;
        }

        public String getDigest() {
            return digest;
        }

        public void setDigest(String digest) {
            this.digest = digest;
        }

        public String getTypeid() {
            return typeid;
        }

        public void setTypeid(String typeid) {
            this.typeid = typeid;
        }

        public Object getAddata() {
            return addata;
        }

        public void setAddata(Object addata) {
            this.addata = addata;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getPtime() {
            return ptime;
        }

        public void setPtime(String ptime) {
            this.ptime = ptime;
        }

        public List<PicInfoBeanXXXXX> getPicInfo() {
            return picInfo;
        }

        public void setPicInfo(List<PicInfoBeanXXXXX> picInfo) {
            this.picInfo = picInfo;
        }

        public static class PicInfoBeanXXXXX {
            /**
             * ref : null
             * width : null
             * url : http://cms-bucket.nosdn.127.net/2018/09/10/c63866bc3e72439e988dcae05637a2e3.jpeg
             * height : null
             */

            private Object ref;
            private Object width;
            private String url;
            private Object height;

            public Object getRef() {
                return ref;
            }

            public void setRef(Object ref) {
                this.ref = ref;
            }

            public Object getWidth() {
                return width;
            }

            public void setWidth(Object width) {
                this.width = width;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public Object getHeight() {
                return height;
            }

            public void setHeight(Object height) {
                this.height = height;
            }
        }
    }

    public static class EntBean {
        /**
         * liveInfo : null
         * tcount : 372
         * picInfo : [{"ref":null,"width":null,"url":"http://cms-bucket.nosdn.127.net/2018/09/11/2577e2b65ce54b2eac729c89a1436835.jpeg","height":null},{"ref":null,"width":null,"url":"http://cms-bucket.nosdn.127.net/2018/09/11/18190c02320045a584ae974c73ea18b8.jpeg","height":null},{"ref":null,"width":null,"url":"http://cms-bucket.nosdn.127.net/2018/09/11/3cc8d74f75be43d0ac17ed7b22ab81e2.jpeg","height":null}]
         * docid : 0003set658340
         * videoInfo : null
         * channel : ent
         * link : https://3g.163.com/all/photoview/0003/658340.html
         * source : 视觉中国
         * title : 杨幂赴纽约看秀 时尚女王功力不减
         * type : photoset
         * imgsrcFrom : null
         * imgsrc3gtype : 2
         * unlikeReason : 重复、旧闻/6,内容质量差/6
         * digest :
         * typeid : 00AJ0003|658340
         * addata : null
         * tag : 图集
         * category : 推荐
         * ptime : 2018-09-11 06:50:53
         */

        private Object liveInfo;
        private int tcount;
        private String docid;
        private Object videoInfo;
        private String channel;
        private String link;
        private String source;
        private String title;
        private String type;
        private Object imgsrcFrom;
        private int imgsrc3gtype;
        private String unlikeReason;
        private String digest;
        private String typeid;
        private Object addata;
        private String tag;
        private String category;
        private String ptime;
        private List<PicInfoBeanXXXXXX> picInfo;

        public Object getLiveInfo() {
            return liveInfo;
        }

        public void setLiveInfo(Object liveInfo) {
            this.liveInfo = liveInfo;
        }

        public int getTcount() {
            return tcount;
        }

        public void setTcount(int tcount) {
            this.tcount = tcount;
        }

        public String getDocid() {
            return docid;
        }

        public void setDocid(String docid) {
            this.docid = docid;
        }

        public Object getVideoInfo() {
            return videoInfo;
        }

        public void setVideoInfo(Object videoInfo) {
            this.videoInfo = videoInfo;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getImgsrcFrom() {
            return imgsrcFrom;
        }

        public void setImgsrcFrom(Object imgsrcFrom) {
            this.imgsrcFrom = imgsrcFrom;
        }

        public int getImgsrc3gtype() {
            return imgsrc3gtype;
        }

        public void setImgsrc3gtype(int imgsrc3gtype) {
            this.imgsrc3gtype = imgsrc3gtype;
        }

        public String getUnlikeReason() {
            return unlikeReason;
        }

        public void setUnlikeReason(String unlikeReason) {
            this.unlikeReason = unlikeReason;
        }

        public String getDigest() {
            return digest;
        }

        public void setDigest(String digest) {
            this.digest = digest;
        }

        public String getTypeid() {
            return typeid;
        }

        public void setTypeid(String typeid) {
            this.typeid = typeid;
        }

        public Object getAddata() {
            return addata;
        }

        public void setAddata(Object addata) {
            this.addata = addata;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getPtime() {
            return ptime;
        }

        public void setPtime(String ptime) {
            this.ptime = ptime;
        }

        public List<PicInfoBeanXXXXXX> getPicInfo() {
            return picInfo;
        }

        public void setPicInfo(List<PicInfoBeanXXXXXX> picInfo) {
            this.picInfo = picInfo;
        }

        public static class PicInfoBeanXXXXXX {
            /**
             * ref : null
             * width : null
             * url : http://cms-bucket.nosdn.127.net/2018/09/11/2577e2b65ce54b2eac729c89a1436835.jpeg
             * height : null
             */

            private Object ref;
            private Object width;
            private String url;
            private Object height;

            public Object getRef() {
                return ref;
            }

            public void setRef(Object ref) {
                this.ref = ref;
            }

            public Object getWidth() {
                return width;
            }

            public void setWidth(Object width) {
                this.width = width;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public Object getHeight() {
                return height;
            }

            public void setHeight(Object height) {
                this.height = height;
            }
        }
    }

    public static class ToutiaoBean {
        /**
         * liveInfo : null
         * tcount : 393
         * picInfo : []
         * docid : DRDL8STK000189FH
         * videoInfo : null
         * channel :
         * link : https://3g.163.com/all/article/DRDL8STK000189FH.html
         * source : 新华网
         * title : 教育的首要问题 习近平讲了这十大"金句"
         * type : doc
         * imgsrcFrom : doc
         * imgsrc3gtype : 0
         * unlikeReason : 重复、旧闻/6,内容质量差/6
         * digest : 教育的首要问题，习近平讲了这十大“金句”
         * typeid :
         * addata : null
         * tag :
         * category : 新闻
         * ptime : 2018-09-11 08:35:47
         */

        private Object liveInfo;
        private int tcount;
        private String docid;
        private Object videoInfo;
        private String channel;
        private String link;
        private String source;
        private String title;
        private String type;
        private String imgsrcFrom;
        private int imgsrc3gtype;
        private String unlikeReason;
        private String digest;
        private String typeid;
        private Object addata;
        private String tag;
        private String category;
        private String ptime;
        private List<?> picInfo;

        public Object getLiveInfo() {
            return liveInfo;
        }

        public void setLiveInfo(Object liveInfo) {
            this.liveInfo = liveInfo;
        }

        public int getTcount() {
            return tcount;
        }

        public void setTcount(int tcount) {
            this.tcount = tcount;
        }

        public String getDocid() {
            return docid;
        }

        public void setDocid(String docid) {
            this.docid = docid;
        }

        public Object getVideoInfo() {
            return videoInfo;
        }

        public void setVideoInfo(Object videoInfo) {
            this.videoInfo = videoInfo;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getImgsrcFrom() {
            return imgsrcFrom;
        }

        public void setImgsrcFrom(String imgsrcFrom) {
            this.imgsrcFrom = imgsrcFrom;
        }

        public int getImgsrc3gtype() {
            return imgsrc3gtype;
        }

        public void setImgsrc3gtype(int imgsrc3gtype) {
            this.imgsrc3gtype = imgsrc3gtype;
        }

        public String getUnlikeReason() {
            return unlikeReason;
        }

        public void setUnlikeReason(String unlikeReason) {
            this.unlikeReason = unlikeReason;
        }

        public String getDigest() {
            return digest;
        }

        public void setDigest(String digest) {
            this.digest = digest;
        }

        public String getTypeid() {
            return typeid;
        }

        public void setTypeid(String typeid) {
            this.typeid = typeid;
        }

        public Object getAddata() {
            return addata;
        }

        public void setAddata(Object addata) {
            this.addata = addata;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getPtime() {
            return ptime;
        }

        public void setPtime(String ptime) {
            this.ptime = ptime;
        }

        public List<?> getPicInfo() {
            return picInfo;
        }

        public void setPicInfo(List<?> picInfo) {
            this.picInfo = picInfo;
        }
    }
}
